package com.yinliubao.client.functions.mysetting.functions.pullcash;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.squareup.okhttp.Request;
import com.yinliubao.client.R;
import com.yinliubao.client.core.net.http.OkHttpClientManager;
import com.yinliubao.client.core.net.http.OkHttpClientManager.OkHttpCallBack;
import com.yinliubao.client.core.storage.SharedPreferHelper;
import com.yinliubao.client.functions.mysetting.bean.RespAccountRestVo;
import com.yinliubao.client.functions.mysetting.bean.tixian.ReqAccountCashVo;
import com.yinliubao.client.functions_common.bean.CommonRequestVo;
import com.yinliubao.client.functions_common.http.RequestIds;
import com.yinliubao.client.utils.GsonHelper;
import com.yinliubao.client.views.WaitingDialog;

public class PullCashActivity extends Activity
{
	
	/** 返回按钮 */
	@InjectView(R.id.ll_left)
	LinearLayout ll_left;

	/** 页面标题 */
	@InjectView(R.id.tv_title)
	TextView tv_title;
	
	/** 提现额度*/
	@InjectView(R.id.et_cash_num)
	EditText et_cash_num;
	
	private int mReqId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pullcash);
		ButterKnife.inject(this);
		initData();
	}
	
	private void initData(){
		ll_left.setVisibility(View.VISIBLE);
		tv_title.setText("用户提现");
	}
	
	@OnClick(R.id.ll_left)
	public void doGoBack(View view)
	{
		finish();
	}
	
	@OnClick(R.id.btn_pullcash)
	public void doPullCash(View view){
		sendReqPullCash();
	}
	
	
	private void sendReqPullCash()
	{
		mReqId = RequestIds.REQUEST_ACCOUNT_CASHPULL;
		CommonRequestVo vo = new CommonRequestVo();
		
		ReqAccountCashVo cashVo=new ReqAccountCashVo();
		cashVo.setApply_value(et_cash_num.getText().toString());
		
		vo.setRequest("flow.base.terminal.apply.cash");
		vo.setJsondata(cashVo);

		String loginSession = SharedPreferHelper.getInstance(this).getLoginSession();

		WaitingDialog.getInstance(this).showWaitPrompt("正在请求中...");
		OkHttpClientManager.doAsynPostRequest(vo, loginSession, mResponseCallBack);
	}
	
	/** 网络请求返回 */
	private OkHttpCallBack mResponseCallBack = new OkHttpCallBack()
	{
		@Override
		public void onFailure(final Request respBody, IOException e)
		{
			WaitingDialog.getWaitDialog().disMiss();
			Toast.makeText(PullCashActivity.this, respBody.toString(),Toast.LENGTH_LONG).show();
		}

		@Override
		public void onResponse(String jsonBody, String session)
		{
			WaitingDialog.getWaitDialog().disMiss();

			try
			{
				if (mReqId == RequestIds.REQUEST_ACCOUNT_CASHPULL)
				{
					RespAccountRestVo responseVo = GsonHelper.json2Obj(jsonBody, RespAccountRestVo.class);

					if (null == responseVo)
					{
						Toast.makeText(PullCashActivity.this, jsonBody, Toast.LENGTH_LONG).show();
						return;
					}

					if (OkHttpClientManager.RESP_OK.equals(responseVo.getRes()))
					{
						Toast.makeText(PullCashActivity.this, "用户提现受理成功！", Toast.LENGTH_LONG).show();
						finish();

					} else
					{
						Toast.makeText(PullCashActivity.this, responseVo.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
				

			} catch (Exception e)
			{
				Toast.makeText(PullCashActivity.this, e.toString(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
	};
}
