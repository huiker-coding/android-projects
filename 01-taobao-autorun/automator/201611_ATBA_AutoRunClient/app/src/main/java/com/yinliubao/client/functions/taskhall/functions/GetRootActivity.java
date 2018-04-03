package com.yinliubao.client.functions.taskhall.functions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinliubao.client.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GetRootActivity extends Activity
{
	/** 返回按钮 */
	@InjectView(R.id.ll_left)
	LinearLayout ll_left;

	/** 页面标题 */
	@InjectView(R.id.tv_title)
	TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_root);
		ButterKnife.inject(this);
		initData();
	}



	private void initData()
	{
		ll_left.setVisibility(View.VISIBLE);
		tv_title.setText("获取Root权限");
	}

	// 跳转到360超级root软件下载页面
	@OnClick(R.id.btn_app_qihu)
	public void getQihuApp(View view)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW); // 内置Action
		intent.setData(Uri.parse("http://p.sogou.com/su/vfZs6"));// 跳转到页面
		startActivity(intent);
	}

	// 跳转到Root大师软件下载页面
	@OnClick(R.id.btn_app_dashi)
	public void getDashiApp(View view)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW); // 内置Action
		intent.setData(Uri.parse("http://p.sogou.com/su/Y4ZFo"));// 跳转到页面
		startActivity(intent);
	}

	// 跳转到KingRoot软件下载页面
	@OnClick(R.id.btn_app_kingroot)
	public void getKingApp(View view)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW); // 内置Action
		intent.setData(Uri.parse("http://p.sogou.com/su/FvZb1"));// 跳转到页面
		startActivity(intent);
	}


	@OnClick(R.id.ll_left)
	public void doGoBack(View view)
	{
		finish();
	}




}
