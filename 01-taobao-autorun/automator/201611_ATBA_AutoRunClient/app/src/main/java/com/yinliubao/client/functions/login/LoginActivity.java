package com.yinliubao.client.functions.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yinliubao.client.R;
import com.yinliubao.client.core.net.http.OkHttpClientManager;
import com.yinliubao.client.core.net.http.OkHttpClientManager.OkHttpCallBack;
import com.yinliubao.client.core.storage.SharedPreferHelper;
import com.yinliubao.client.functions.BaseActivity;
import com.yinliubao.client.functions.MainActivity;
import com.yinliubao.client.functions.login.bean.ReqLoginVo;
import com.yinliubao.client.functions.login.bean.RespLoginVo;
import com.yinliubao.client.functions.regist.RegistActivity;
import com.yinliubao.client.functions.reset.ResetActivity;
import com.yinliubao.client.functions_common.bean.CommonRequestVo;
import com.yinliubao.client.functions_common.http.RequestIds;
import com.yinliubao.client.utils.GsonHelper;
import com.yinliubao.client.utils.Md5Helper;
import com.yinliubao.client.utils.validator.Rule;
import com.yinliubao.client.utils.validator.Rules;
import com.yinliubao.client.utils.validator.Validator;
import com.yinliubao.client.views.WaitingDialog;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author gray.z
 * @Description: 登录页面
 * @date 2017年4月11日 下午8:22:15
 */
public class LoginActivity extends BaseActivity {
    /**
     * 用户账号
     */
    @InjectView(R.id.et_account)
    EditText et_account;

    /**
     * 用户密码
     */
    @InjectView(R.id.et_password)
    EditText et_password;

    private Validator mValidator;
    private int mReqId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mValidator = Validator.getInstance();
        mValidator.setValidatorListener(validatorListener);

        String session = SharedPreferHelper.getInstance(getApplicationContext()).getLoginSession();
        if (!session.isEmpty()) {
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(it);
            finish();
        }

    }

    /************************************************* OnClick Event Begin ******************************************************/

    /**
     * 用户登录
     */
    @OnClick(R.id.btn_login)
    public void doLogin(View view) {
        validate();
    }

    /**
     * 用户注册
     */
    @OnClick(R.id.btn_regist)
    public void doRegist(View view) {
        Intent it = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(it);
    }

    /**
     * 用户重置密码
     */
    @OnClick(R.id.tv_lost_pwd)
    public void doRestPwd(View view) {
        Intent it = new Intent(LoginActivity.this, ResetActivity.class);
        startActivity(it);
    }

    /************************************************* OnClick Event End ******************************************************/

    /**
     * 验证输入信息是否为空
     */
    private void validate() {
        mValidator.clear();
        mValidator.putRule(Rules.minLength(11, getString(R.string.please_input_phone_num), et_account));
        mValidator.putRule(Rules.minLength(1, getString(R.string.please_input_password), et_password));
        mValidator.validate();

    }

    /**
     * 验证监听功能类
     */
    Validator.ValidatorListener validatorListener = new Validator.ValidatorListener() {
        @Override
        public void onFailed(Rule rule) {
            showOnlyOneToast(rule.getFailureMessage());
            rule.getEditText().requestFocus();
            rule.getEditText().selectAll();
            InputMethodManager inputManager = (InputMethodManager) rule.getEditText().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(rule.getEditText(), InputMethodManager.SHOW_FORCED);
        }

        @Override
        public void onSuccess() {
            sendRequestLogin();
        }
    };

    private void sendRequestLogin() {
        mReqId = RequestIds.REQUEST_CLIENT_LOGIN;

        ReqLoginVo reqLoginVo = new ReqLoginVo();
        reqLoginVo.setTerminal_userid(et_account.getText().toString());
        reqLoginVo.setUser_pwd(Md5Helper.md5(et_password.getText().toString()));

        CommonRequestVo vo = new CommonRequestVo();
        vo.setRequest("flow.base.terminal.login");
        vo.setJsondata(reqLoginVo);

        WaitingDialog.getInstance(this).showWaitPrompt("正在登录中...");
        OkHttpClientManager.doAsynPostRequest(vo, null, mResponseCallBack);
    }

    /**
     * 网络请求返回
     */
    private OkHttpCallBack mResponseCallBack = new OkHttpCallBack() {
        @Override
        public void onFailure(final Request respBody, IOException e) {
            WaitingDialog.getWaitDialog().disMiss();
            showOnlyOneToast(respBody.body().toString());
        }

        @Override
        public void onResponse(String jsonBody, String session) {
            WaitingDialog.getWaitDialog().disMiss();

            if (mReqId == RequestIds.REQUEST_CLIENT_LOGIN) {
                RespLoginVo respLoginVo = GsonHelper.json2Obj(jsonBody, RespLoginVo.class);
                if (null == respLoginVo) {
                    Toast.makeText(getApplicationContext(), "JSON数据解析错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((OkHttpClientManager.RESP_OK).equals(respLoginVo.getRes())) {
                    SharedPreferHelper.getInstance(getApplicationContext()).saveLoginSesion(session);
                    SharedPreferHelper.getInstance(getApplicationContext()).saveUserName(et_account.getText().toString());

                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                } else {
                    showOnlyOneToast(respLoginVo.getMessage());
                }
            }

        }
    };

}
