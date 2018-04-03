package com.yinliubao.client.functions.regist;

import java.io.IOException;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yinliubao.client.R;
import com.yinliubao.client.core.net.http.OkHttpClientManager;
import com.yinliubao.client.core.net.http.OkHttpClientManager.OkHttpCallBack;
import com.yinliubao.client.functions.BaseActivity;
import com.yinliubao.client.functions.regist.RegistActivity.MyCountTimer;
import com.yinliubao.client.functions.regist.bean.ReqUserRegistVo;
import com.yinliubao.client.functions_common.bean.CommonRequestVo;
import com.yinliubao.client.functions_common.bean.CommonResponseVo;
import com.yinliubao.client.functions_common.bean.idt.ReqIdtCodeVo;
import com.yinliubao.client.functions_common.http.RequestIds;
import com.yinliubao.client.utils.GsonHelper;
import com.yinliubao.client.utils.Md5Helper;
import com.yinliubao.client.utils.validator.Rule;
import com.yinliubao.client.utils.validator.Rules;
import com.yinliubao.client.utils.validator.Validator;
import com.yinliubao.client.views.WaitingDialog;

/**
 * @Description:
 * @author gray.z
 * @date 2017年4月11日 下午8:23:38
 */
public class RegistActivity extends BaseActivity
{
	/** 返回按钮 */
	@InjectView(R.id.ll_left)
	LinearLayout ll_left;

	/** 页面标题 */
	@InjectView(R.id.tv_title)
	TextView tv_title;

	/** 用户账号 */
	@InjectView(R.id.et_account)
	EditText et_account;

	/** 用户支付宝账号 */
	@InjectView(R.id.et_zf_account)
	EditText et_zf_account;

	/** 用户密码 */
	@InjectView(R.id.et_password)
	EditText et_password;

	/** 用户密码again */
	@InjectView(R.id.et_pwd_again)
	EditText et_pwd_again;

	/** 验证码 */
	@InjectView(R.id.et_verify_code)
	EditText et_verify_code;

	@InjectView(R.id.btn_send_verify_code)
	Button btn_send_verify_code;

	private MyCountTimer timer;
	private Validator mValidator;
	private int mReqId;

	private String session;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		ButterKnife.inject(this);
		initData();
	}

	private void initData()
	{
		ll_left.setVisibility(View.VISIBLE);
		tv_title.setText("用户注册");

		mValidator = Validator.getInstance();
		mValidator.setValidatorListener(validatorListener);
	}

	/************************************************* OnClick Event Begin ******************************************************/

	@OnClick(R.id.ll_left)
	public void doGoBack(View view)
	{
		finish();
	}

	@OnClick(R.id.btn_send_verify_code)
	public void doSendVerifyCode(View view)
	{
		String phoneNum = et_account.getText().toString();
		if (phoneNum.length() < 11)
		{
			showOnlyOneToast("请输入手机号码");
			return;
		}
		timer = new MyCountTimer(60000, 1000);
		timer.start();
		btn_send_verify_code.setEnabled(false);
		sendRequestVerifyCode();
	}

	@OnClick(R.id.btn_register)
	public void doConfirmRegist(View view)
	{
		validate();
	}

	/************************************************* OnClick Event End ******************************************************/

	/**
	 * 验证输入信息是否为空
	 */
	private void validate()
	{
		mValidator.clear();
		mValidator.putRule(Rules.minLength(11, getString(R.string.please_input_phone_num), et_account));
		mValidator.putRule(Rules.minLength(1, getString(R.string.please_input_zfb_account), et_zf_account));
		mValidator.putRule(Rules.minLength(1, getString(R.string.please_input_password), et_password));
		mValidator.putRule(Rules.minLength(1, getString(R.string.please_input_password_again), et_pwd_again));
		mValidator.putRule(Rules.minLength(6, getString(R.string.please_input_verify_code), et_verify_code));
		mValidator.validate();

	}

	/**
	 * 验证监听功能类
	 */
	Validator.ValidatorListener validatorListener = new Validator.ValidatorListener()
	{
		@Override
		public void onFailed(Rule rule)
		{
			showOnlyOneToast(rule.getFailureMessage());
			rule.getEditText().requestFocus();
			rule.getEditText().selectAll();
			InputMethodManager inputManager = (InputMethodManager) rule.getEditText().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.showSoftInput(rule.getEditText(), InputMethodManager.SHOW_FORCED);
		}

		@Override
		public void onSuccess()
		{
			sendRequestRegist();
		}
	};

	/** 发送获取验证码的请求 */
	private void sendRequestVerifyCode()
	{
		mReqId = RequestIds.REQUEST_SEND_VERIFY_CODE;
		String url = "http://10.20.24.69/api.php";

		ReqIdtCodeVo reqIdtCodeVo = new ReqIdtCodeVo(et_account.getText().toString(), "register");

		CommonRequestVo vo = new CommonRequestVo();
		vo.setRequest("flow.base.terminal.mobile.validate");
		vo.setJsondata(reqIdtCodeVo);

		WaitingDialog.getInstance(this).showWaitPrompt("正在请求中...");
		OkHttpClientManager.doAsynPostRequest(vo, null, mResponseCallBack);
	}

	/** 发送用户注册请求 */
	private void sendRequestRegist()
	{
		mReqId = RequestIds.REQUEST_CLIENT_REGIST;
		String url = "http://10.20.24.69/api.php";

		ReqUserRegistVo reqRegisterVo = new ReqUserRegistVo();
		reqRegisterVo.setAccount(et_zf_account.getText().toString());
		reqRegisterVo.setTerminalUserid(et_account.getText().toString());
		reqRegisterVo.setUserPwd(Md5Helper.md5(et_password.getText().toString()));
		reqRegisterVo.setDeviceSn("1234");
		reqRegisterVo.setIdtCode(et_verify_code.getText().toString());

		CommonRequestVo vo = new CommonRequestVo();
		vo.setRequest("flow.base.terminal.register");
		vo.setJsondata(reqRegisterVo);

		showOnlyOneToast("注册的sessionId为：" + session);
		WaitingDialog.getInstance(this).showWaitPrompt("正在请求中...");
		OkHttpClientManager.doAsynPostRequest(vo, session, mResponseCallBack);
	}

	/** 网络请求返回 */
	private OkHttpCallBack mResponseCallBack = new OkHttpCallBack()
	{
		@Override
		public void onFailure(final Request respBody, IOException e)
		{
			WaitingDialog.getWaitDialog().disMiss();
			showOnlyOneToast(respBody.body().toString());
		}

		@Override
		public void onResponse(String body, String session)
		{
			WaitingDialog.getWaitDialog().disMiss();

			try
			{
//				// respBody.body().string();
//
//				if (mReqId == RequestIds.REQUEST_CLIENT_REGIST)
//				{
//					CommonResponseVo respLoginVo = GsonHelper.json2Obj(respBody.body().string(), CommonResponseVo.class);
//
//					if ("0".equals(respLoginVo.getRes()))
//					{
//						showOnlyOneToast("注册成功");
//						finish();
//					} else
//					{
//						showOnlyOneToast(respLoginVo.getMessage());
//					}
//				}
//
//				if (mReqId == RequestIds.REQUEST_SEND_VERIFY_CODE)
//				{
//					CommonResponseVo respLoginVo = GsonHelper.json2Obj(respBody.body().string(), CommonResponseVo.class);
//					
//					if ("0".equals(respLoginVo.getRes()))
//					{
//						session = respBody.headers().values("set-cookie").get(0);
//						showOnlyOneToast("验证码发送成功");
//					} else
//					{
//						showOnlyOneToast(respLoginVo.getMessage());
//					}
//					
//				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	};

	class MyCountTimer extends CountDownTimer
	{

		public MyCountTimer(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			btn_send_verify_code.setText((millisUntilFinished / 1000) + "秒后重发");
		}

		@Override
		public void onFinish()
		{
			btn_send_verify_code.setEnabled(true);
			btn_send_verify_code.setText("重新发送验证码");
			timer.cancel();
		}
	}

}
