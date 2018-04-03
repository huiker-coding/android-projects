package com.yinliubao.client.functions.mysetting.functions.aboutus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.yinliubao.client.R;

public class AboutUsActivity extends Activity
{

	/** 返回按钮 */
	@InjectView(R.id.ll_left)
	LinearLayout ll_left;

	/** 页面标题 */
	@InjectView(R.id.tv_title)
	TextView tv_title;

	private int mReqId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		ButterKnife.inject(this);
		initData();
	}

	private void initData()
	{
		ll_left.setVisibility(View.VISIBLE);
		tv_title.setText("关于我们");

	}

	@OnClick(R.id.ll_left)
	public void doGoBack(View view)
	{
		finish();
	}

}
