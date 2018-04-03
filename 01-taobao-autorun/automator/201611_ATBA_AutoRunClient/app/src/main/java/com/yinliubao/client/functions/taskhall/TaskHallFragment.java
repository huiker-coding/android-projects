package com.yinliubao.client.functions.taskhall;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yinliubao.client.R;
import com.yinliubao.client.core.net.http.OkHttpClientManager;
import com.yinliubao.client.core.net.http.OkHttpClientManager.OkHttpCallBack;
import com.yinliubao.client.core.storage.SharedPreferHelper;
import com.yinliubao.client.core.storage.database.bean.TaskInfoBean;
import com.yinliubao.client.core.storage.database.dao.TaskInfoDao;
import com.yinliubao.client.functions.taskhall.bean.task_receive.RespTaskRecevieVo;
import com.yinliubao.client.functions.taskhall.bean.task_receive.RespTaskRecevieVo.TaskReceiveBody;
import com.yinliubao.client.functions.taskhall.bean.task_return.ReqTaskReturnVo;
import com.yinliubao.client.functions.taskhall.functions.GetRootActivity;
import com.yinliubao.client.functions_common.bean.CommonRequestVo;
import com.yinliubao.client.functions_common.bean.CommonResponseVo;
import com.yinliubao.client.functions_common.http.RequestIds;
import com.yinliubao.client.utils.Base64Helper;
import com.yinliubao.client.utils.GsonHelper;
import com.yinliubao.client.utils.SdFileHelper;
import com.yinliubao.client.views.WaitingDialog;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TaskHallFragment extends Fragment {

    @InjectView(R.id.tv_android_ver)
    TextView tv_android_ver;

    @InjectView(R.id.tv_taobao_ver)
    TextView tv_taobao_ver;

    @InjectView(R.id.tv_root_state)
    TextView tv_root_state;

    @InjectView(R.id.btn_solve_root)
    Button btn_solve_root;

    @InjectView(R.id.btn_solve_taobaover)
    Button btn_solve_taobaover;

    @InjectView(R.id.btn_task_handler)
    Button btn_task_handler;

    private int mReqId;
    private int count;
    private MyCountTimer timer;

    private boolean blTaobaoVer = true;
    private boolean blRootState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_hall, null);
        ButterKnife.inject(this, view);
        initData();
        return view;
    }

    private void initData() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TaskInfoDao taskInfoDao = new TaskInfoDao(getActivity());
        TaskInfoBean task = new TaskInfoBean(1, "242314", "4321421", "30");
        taskInfoDao.add(task);

        testRunningEvr();
    }


    private void testRunningEvr() {

        tv_android_ver.setText(android.os.Build.VERSION.RELEASE);

        PackageManager manager;
        PackageInfo info = null;
        manager = getActivity().getPackageManager();
        try {
            info = manager.getPackageInfo("com.taobao.taobao", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (null != info) {
            tv_taobao_ver.setText(info.versionName);
            btn_solve_taobaover.setVisibility(View.GONE);
            tv_root_state.setTextColor(getResources().getColor(R.color.green));
            blTaobaoVer = true;
        } else {
            tv_taobao_ver.setText("检测本机未安装手淘客户端");
            btn_solve_taobaover.setVisibility(View.VISIBLE);
            tv_root_state.setTextColor(getResources().getColor(R.color.colorRed));
            blTaobaoVer = false;
        }


        // 判断当前设备是否ROOT过，根据是否root进入相应页面
        int ret = execRootCmdSilent("echo test");
        if (ret != 1) {
            btn_solve_root.setVisibility(View.GONE);
            tv_root_state.setText("是");
            tv_root_state.setTextColor(getResources().getColor(R.color.green));
            blRootState = true;
            pushAutoJar();
            installInputMethod();
        } else {
            btn_solve_root.setVisibility(View.VISIBLE);
            tv_root_state.setText("否");
            blRootState = false;
            tv_root_state.setTextColor(getResources().getColor(R.color.colorRed));
        }

        if (blRootState && blTaobaoVer) {
            btn_task_handler.setEnabled(true);
        } else {
            btn_task_handler.setEnabled(false);
        }
    }

    @Override
    public void onResume() {

        if (blRootState && blTaobaoVer) {
            btn_task_handler.setEnabled(true);
        } else {
            btn_task_handler.setEnabled(false);
        }

        super.onResume();
    }

    class MyCountTimer extends CountDownTimer {
        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            if (count >= 7) {
                String parameterStr = GsonHelper.obj2Json(getTaskParaData());
                String taskStr = Base64Helper.encode(parameterStr);
                onTaskHandler(taskStr);
            } else {
                sendRequsetTask();
            }
            timer.cancel();
            timer = null;
        }
    }

    /*************************************************
     * OnClick Event Begin
     ******************************************************/

    @OnClick(R.id.btn_solve_root)
    public void doSolveRoot(View view) {
        Intent it = new Intent(getActivity(), GetRootActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.btn_refresh_state)
    public void doRefreshState(View view) {
        testRunningEvr();
    }

    @OnClick(R.id.btn_task_handler)
    public void doHandlerTask(View view) {
        if (timer == null) {
            timer = new MyCountTimer(5000, 1000);
            timer.start();
        }
        count++;
    }

    @OnClick(R.id.btn_task_callback)
    public void doCallbackTask(View view) {
        sendRequestTaskCallback();
    }

    /**************************************************
     * OnClick Event End
     ******************************************************/

    private void sendRequsetTask() {
        mReqId = RequestIds.REQUEST_GET_TASK;
        CommonRequestVo vo = new CommonRequestVo();
        vo.setRequest("flow.order.task.get");
        vo.setJsondata(null);

        String loginSession = SharedPreferHelper.getInstance(getActivity().getApplicationContext()).getLoginSession();

        WaitingDialog.getInstance(getActivity()).showWaitPrompt("正在请求中...");
        OkHttpClientManager.doAsynPostRequest(vo, loginSession, mResponseCallBack);
    }

    private void sendRequestTaskCallback() {
        mReqId = RequestIds.REQUEST_TASK_RETURN;
        String jsonData = SdFileHelper.readLogFile();
        ReqTaskReturnVo reqTaskReturnVo = GsonHelper.json2Obj(jsonData, ReqTaskReturnVo.class);
        CommonRequestVo vo = new CommonRequestVo();
        vo.setRequest("flow.order");
        vo.setJsondata(reqTaskReturnVo);

        String loginSession = SharedPreferHelper.getInstance(getActivity().getApplicationContext()).getLoginSession();

        WaitingDialog.getInstance(getActivity()).showWaitPrompt("正在请求中...");
        OkHttpClientManager.doAsynPostRequest(vo, loginSession, mResponseCallBack);
    }

    /**
     * 网络请求返回
     */
    private OkHttpCallBack mResponseCallBack = new OkHttpCallBack() {
        @Override
        public void onFailure(final Request respBody, IOException e) {
            WaitingDialog.getWaitDialog().disMiss();
            Toast.makeText(getActivity(), respBody.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String bodyStr, String session) {
            WaitingDialog.getWaitDialog().disMiss();

            try {

                if (mReqId == RequestIds.REQUEST_GET_TASK) {
                    RespTaskRecevieVo responseVo = GsonHelper.json2Obj(bodyStr, RespTaskRecevieVo.class);

                    if (null == responseVo) {
                        Toast.makeText(getActivity(), bodyStr, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if ("0".equals(responseVo.getRes())) {
                        TaskReceiveBody taskReceiveVo = responseVo.getOrders();
                        taskReceiveVo.setInput_method(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD));

                        String parameterStr = GsonHelper.obj2Json(taskReceiveVo);
                        String taskStr = Base64Helper.encode(parameterStr);
                        onTaskHandler(taskStr);
                    } else {
                        Toast.makeText(getActivity(), responseVo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (mReqId == RequestIds.REQUEST_TASK_RETURN) {
                    CommonResponseVo responseVo = GsonHelper.json2Obj(bodyStr, CommonResponseVo.class);
                    if (null == responseVo) {
                        Toast.makeText(getActivity(), bodyStr, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if ("0".equals(responseVo.getRes())) {
                        Toast.makeText(getActivity(), "任务状态回传成功", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), responseVo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    };

    // 开始任务执行
    private void onTaskHandler(String taskStr) {

        String[] mcommand = new String[]
                {"su", "-c", "uiautomator runtest mRunner.jar -e task " + taskStr + " -c com.example.automator.MainTestCase"};
        try {
            Runtime.getRuntime().exec(mcommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼接用户填写的任务发布参数
     */
    private TaskReceiveBody getTaskParaData() {
        TaskReceiveBody taskVo = new TaskReceiveBody();

        taskVo.setOrder_id("CE-ZH-20170425");
        taskVo.setOrder_bn("JP1723680066716795622");
        taskVo.setTask_id("JP1723680066716795622-001");
        taskVo.setGoods_search_key("阿迪达斯男鞋");
        taskVo.setGoods_title("阿迪达斯男鞋2017夏季低帮透气 黑白透气运动休闲鞋板鞋BB 9727");
        taskVo.setGoods_name("阿迪达斯男鞋2017夏季低帮透气 黑白透气运动休闲鞋板鞋BB 9727");
        taskVo.setShop_name("风驰运动专营店");
        taskVo.setShop_seller("爷傲奈我何");
        taskVo.setShop_type("天猫");
        taskVo.setFlow_type("精品");
        taskVo.setTao_token("token001");
        taskVo.setScan_time("60");
        taskVo.setScan_depth("30");
        taskVo.setDeliver_placels("江苏");
        taskVo.setPrice_min("200.000");
        taskVo.setPrice_max("300.000");
        taskVo.setSort_type("销量优先");

        List<String> extraFilterLs = new ArrayList<String>();
        extraFilterLs.add("包邮");
        extraFilterLs.add("天猫");
        extraFilterLs.add("消费者保障");
        taskVo.setExtra_filterls(extraFilterLs);

        // 附加服务
        List<Integer> extraActionLs = new ArrayList<Integer>();
        extraActionLs.add(1);
        extraActionLs.add(2);
        extraActionLs.add(3);
        extraActionLs.add(4);
        extraActionLs.add(5);
        extraActionLs.add(6);
        taskVo.setExtra_actionls(extraActionLs);

        taskVo.setInput_method(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD));
        return taskVo;
    }


    private void pushAutoJar() {
        createFile("mRunner.jar"); // 进行资源的转移 将assets下的文件转移到可读写文件目录下
        String[] mcommand = new String[]
                {"su", "-c", "mv " + Environment.getExternalStorageDirectory().getPath() + "/mRunner.jar " + "/data/local/tmp"};
        try {
            Runtime.getRuntime().exec(mcommand);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    private int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public void createFile(String fileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = getActivity().getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + fileName);
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    private void installInputMethod() {
        PackageInfo packageInfo;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo("com.yunda.input", 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();

        }
        if (packageInfo == null) {
            // 启用安装新线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("hao", "未安装进行安装");
                    slientInstall(); // 未安装进行安装
                }
            }).start();
        } else {
            Log.e("hao", "已经安装");
        }
    }

    /**
     * 静默安装
     *
     * @param
     * @return
     */
    public boolean slientInstall() {
        createFile("yd_ime.apk"); // 进行资源的转移 将assets下的文件转移到可读写文件目录下
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/yd_ime.apk");

        boolean result = false;
        Process process = null;
        OutputStream out = null;
        System.out.println(file.getPath());
        if (file.exists()) {
            System.out.println(file.getPath() + "==");
            try {
                process = Runtime.getRuntime().exec("su");
                out = process.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(out);
                dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n"); // 获取文件所有权限
                dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + file.getPath()); // 进行静默安装命令
                // 提交命令
                dataOutputStream.flush();
                // 关闭流操作
                dataOutputStream.close();
                out.close();
                int value = process.waitFor();

                // 代表成功
                if (value == 0) {
                    Log.e("hao", "安装成功！");
                    result = true;
                } else if (value == 1) { // 失败
                    Log.e("hao", "安装失败！");
                    result = false;
                } else { // 未知情况
                    Log.e("hao", "未知情况！");
                    result = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!result) {
                Log.e("hao", "root权限获取失败，将进行普通安装");
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
                result = true;
            }
        }

        return result;
    }


}
