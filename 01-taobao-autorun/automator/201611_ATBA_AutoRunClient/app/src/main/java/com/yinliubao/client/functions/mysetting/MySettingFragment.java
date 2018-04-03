package com.yinliubao.client.functions.mysetting;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yinliubao.client.R;
import com.yinliubao.client.core.net.http.OkHttpClientManager;
import com.yinliubao.client.core.net.http.OkHttpClientManager.OkHttpCallBack;
import com.yinliubao.client.core.storage.SharedPreferHelper;
import com.yinliubao.client.functions.login.LoginActivity;
import com.yinliubao.client.functions.mysetting.bean.RespAccountRestVo;
import com.yinliubao.client.functions.mysetting.functions.aboutus.AboutUsActivity;
import com.yinliubao.client.functions.mysetting.functions.progresscash.ProgressCashActivity;
import com.yinliubao.client.functions.mysetting.functions.pullcash.PullCashActivity;
import com.yinliubao.client.functions_common.bean.CommonRequestVo;
import com.yinliubao.client.functions_common.http.RequestIds;
import com.yinliubao.client.utils.GsonHelper;
import com.yinliubao.client.views.WaitingDialog;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MySettingFragment extends Fragment
{
	@InjectView(R.id.tv_user_name)
	TextView tv_user_name;

	@InjectView(R.id.tv_account_rest)
	TextView tv_account_rest;

	private int mReqId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_my_setting, null);
		ButterKnife.inject(this, view);
		initData();
		return view;
	}

	private void initData()
	{
		String userName = SharedPreferHelper.getInstance(getActivity()).getUserName();
		tv_user_name.setText(userName);

		sendRequsetTask();
	}

	/************************************************* OnClick Event Begin ******************************************************/

	@OnClick(R.id.btn_login_out)
	public void doLoginOut(View view)
	{
		SharedPreferHelper.getInstance(getActivity().getApplicationContext()).saveUserName("");
		SharedPreferHelper.getInstance(getActivity().getApplicationContext()).saveLoginSesion("");
		Intent it = new Intent(getActivity(), LoginActivity.class);
		startActivity(it);
		getActivity().finish();
	}

	@OnClick(R.id.tv_tixian)
	public void doTixian(View view)
	{
		Intent it = new Intent(getActivity(), PullCashActivity.class);
		startActivity(it);
	}

	@OnClick(R.id.tv_chaxun)
	public void doChaxun(View view)
	{
		Intent it = new Intent(getActivity(), ProgressCashActivity.class);
		startActivity(it);
	}

	@OnClick(R.id.tv_account_setting)
	public void doAccountSetting(View view)
	{
		Toast.makeText(getActivity(), "正在紧张开发中.....", Toast.LENGTH_SHORT).show();
	}

	@OnClick(R.id.tv_aboutus)
	public void doAboutUs(View view)
	{
		Intent it = new Intent(getActivity(), AboutUsActivity.class);
		startActivity(it);
	}

	/************************************************* OnClick Event End ******************************************************/

	private void sendRequsetTask()
	{
		mReqId = RequestIds.REQUEST_ACCOUNT_MONNEY;
		CommonRequestVo vo = new CommonRequestVo();
		vo.setRequest("flow.base.terminal.account");
		vo.setJsondata(null);
		String loginSession = SharedPreferHelper.getInstance(getActivity().getApplicationContext()).getLoginSession();
		WaitingDialog.getInstance(getActivity()).showWaitPrompt("正在请求中...");
		OkHttpClientManager.doAsynPostRequest(vo, loginSession, mResponseCallBack);
	}

	/** 网络请求返回 */
	private OkHttpCallBack mResponseCallBack = new OkHttpCallBack()
	{
		@Override
		public void onFailure(final Request respBody, IOException e)
		{
			WaitingDialog.getWaitDialog().disMiss();
			Toast.makeText(getActivity(), respBody.toString(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onResponse(String jsonBody, String session)
		{
			WaitingDialog.getWaitDialog().disMiss();

			try
			{
				if (mReqId == RequestIds.REQUEST_ACCOUNT_MONNEY)
				{
					RespAccountRestVo responseVo = GsonHelper.json2Obj(jsonBody, RespAccountRestVo.class);

					if (null == responseVo)
					{
						Toast.makeText(getActivity(), jsonBody, Toast.LENGTH_LONG).show();
						return;
					}

					if (OkHttpClientManager.RESP_OK.equals(responseVo.getRes()))
					{
						Toast.makeText(getActivity(), "查询余额成功！", Toast.LENGTH_LONG).show();
						String accountValue = responseVo.getOrders().getAccount_value();
						tv_account_rest.setText(accountValue);
					} else
					{
						Toast.makeText(getActivity(), responseVo.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}

			} catch (Exception e)
			{
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
	};
}
