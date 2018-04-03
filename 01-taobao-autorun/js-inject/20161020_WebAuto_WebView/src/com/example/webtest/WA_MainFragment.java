package com.example.webtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.webtest.base.WA_YundaFragment;
import com.example.webtest.io.WA_Parameters;

/**
 * @desc 自动化Fragment主调页面
 * @author z.h
 */
public class WA_MainFragment extends WA_YundaFragment
{
	private static final String ARG_CODE = "WebAutoFragment";
	private static final String BASIC_JS_PATH = "basic_inject.js";
	private static final String LOGIC_JS_PATH = "logic_inject.js";

	private Button startBtn;
	private LocalMethod mLocalMethod;
	private WA_Parameters parameter;
	private String injectJS;

	/**  通过静态方法实例化自动化Fragment*/
	public static void start(Activity mContext, int containerRsID, WA_Parameters parameter)
	{
		WA_MainFragment mCurrentFragment = WA_MainFragment.getInstence(parameter);
		mContext.getFragmentManager().beginTransaction().replace(containerRsID, mCurrentFragment).commit();
	}

	private static WA_MainFragment getInstence(WA_Parameters parameter)
	{
		WA_MainFragment webAutoFragment = new WA_MainFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(ARG_CODE, parameter);
		webAutoFragment.setArguments(bundle);
		return webAutoFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (null != bundle)
		{
			parameter = (WA_Parameters) bundle.getSerializable(ARG_CODE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.wa_fragment_main, container, false);
		findViews(view);
		initData();
		setListener(view);
		return view;
	}

	private void findViews(View view)
	{
		listWeb = (WebView) view.findViewById(R.id.wa_webview_list);
		detailWeb = (WebView) view.findViewById(R.id.wa_webview_detail);
		startBtn = (Button) view.findViewById(R.id.wa_btn_start);
	}

	/** 初始化两个不同功用的WebView */
	private void initData()
	{
		initListWeb();
		initDetailWeb();
		//deleteLog();
		injectJS = getJsFromFile(getActivity(), BASIC_JS_PATH) + getJsFromFile(getActivity(), LOGIC_JS_PATH);
	}

	private void initListWeb()
	{
		WebSettings webSetting = listWeb.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setDefaultTextEncodingName("utf-8");
		webSetting.setLoadWithOverviewMode(true);
		webSetting.setAllowFileAccess(true);

		listWeb.loadUrl("https://m.taobao.com");
		listWeb.setWebViewClient(new MyListWebViewClient());
		mLocalMethod = new WA_YundaFragment.LocalMethod(getActivity(), parameter);
		listWeb.addJavascriptInterface(mLocalMethod, "localMethod");
	}

	private void initDetailWeb()
	{
		WebSettings webSetting = detailWeb.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setDefaultTextEncodingName("utf-8");
		webSetting.setLoadWithOverviewMode(true);
		webSetting.setAllowFileAccess(true);

		detailWeb.setWebViewClient(new MyDetailWebViewClient());
	}

	private void setListener(View view)
	{
		startBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new Thread()
				{
					@Override
					public void run()
					{
						// step1：点击搜索框进行商品搜索##########################（Page1-首页）
						doEnterSearchPage();
						doSleep(6);

						// step2：选择店铺类型####################################（Page2-搜索页面）
						doSelectStoreType(parameter);
						doSleep(6);

						// step3：输入搜索关键字并点击搜索按钮#####################（Page2-搜索页面）
						doSearch(parameter);
						doSleep(6);

						// step4：根据筛选条件和销量优先顺序排序###################（Page2-搜索页面）
						doOrderBySellAmount();
						doSleep(3);

						// step5：浏览选择指定商品#################################（Page2-搜索页面——>Page3-详情页面）
						doScan(parameter);
					}
				}.start();
			}
		});
	}

	/** ListWebView加载完注入基本JS函数 */
	private class MyListWebViewClient extends WebViewClient
	{
		@Override
		public void onPageFinished(WebView view, String url)
		{
			view.loadUrl("javascript:" + injectJS);
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			super.onPageStarted(view, url, favicon);
		}
	}

	/** DetailWebView加载完注入基本JS函数并且关闭对话框 */
	private class MyDetailWebViewClient extends WebViewClient
	{
		@Override
		public void onPageFinished(WebView view, String url)
		{
			view.loadUrl("javascript:" + injectJS);
			doAlertHide();
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			super.onPageStarted(view, url, favicon);
		}
	}

}
