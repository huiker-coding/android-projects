package com.yinliubao.client.views;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.yinliubao.client.R;

public class WaitingDialog
{
	private static final int DEFAULT_WAIT_TIME_OUT = 12888;
	private static final String COMMON_PROMPT = "正在卖力加载中";

	private Context context;
	private ProgressDialog progressDialog;
	private final Timer timer = new Timer();
	private TimerTask timerTask;
	private static WaitingDialog waitDialog;

	public WaitingDialog(Context context)
	{
		this.context = context;
	}

	public static WaitingDialog getInstance(Context context)
	{
		waitDialog = new WaitingDialog(context);
		return waitDialog;
	}

	public static WaitingDialog getWaitDialog()
	{
		return waitDialog;
	}

	public void showWaitPrompt()
	{
		showWaitPrompt(COMMON_PROMPT);
	}

	public void showWaitPrompt(String prompt)
	{
		showWaitPrompt(prompt, false);
	}

	public void showWaitPrompt(String prompt, Boolean cancelAble)
	{
		showWaitPrompt(prompt, cancelAble, DEFAULT_WAIT_TIME_OUT);
	}

	public void showWaitPrompt(String prompt, Boolean cancelAble, int waitTime)
	{
		progressDialog = new ProgressDialog(context, R.style.progress_dialog);
		progressDialog.show();
		progressDialog.setContentView(R.layout.dialog_waiting);
		progressDialog.setCancelable(cancelAble);
		progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
		msg.setText(prompt);

		timerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				disMiss();
			}
		};
		timer.schedule(timerTask, waitTime);
	}

	public boolean isShowing()
	{
		if (null == progressDialog)
		{
			return false;
		}
		return progressDialog.isShowing();
	}

	public void disMiss()
	{
		if (null == progressDialog)
		{
			return;
		}

		if (progressDialog.isShowing())
		{
			progressDialog.dismiss();
			timer.cancel();
			progressDialog = null;
		}
	}

}
