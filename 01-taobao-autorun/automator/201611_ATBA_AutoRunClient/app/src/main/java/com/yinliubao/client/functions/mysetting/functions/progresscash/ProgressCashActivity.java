package com.yinliubao.client.functions.mysetting.functions.progresscash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.yinliubao.client.functions.mysetting.functions.progresscash.bean.DataVo;
import com.yinliubao.client.functions_common.bean.CommonRequestVo;
import com.yinliubao.client.functions_common.http.RequestIds;
import com.yinliubao.client.utils.GsonHelper;
import com.yinliubao.client.views.WaitingDialog;

public class ProgressCashActivity extends Activity
{

	/** 返回按钮 */
	@InjectView(R.id.ll_left)
	LinearLayout ll_left;

	/** 页面标题 */
	@InjectView(R.id.tv_title)
	TextView tv_title;

	@InjectView(R.id.lv_progress)
	ListView lv_progress;

	private int mReqId;
	private List<DataVo> mDataLs;
	private mAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progresscash);
		ButterKnife.inject(this);
		initData();
	}

	private void initData()
	{
		ll_left.setVisibility(View.VISIBLE);
		tv_title.setText("提现结果查询");

		mDataLs = new ArrayList<>();

		mDataLs.add(new DataVo("2017/04/01", "提交提现申请"));
		mDataLs.add(new DataVo("2017/04/05", "提现受理中"));
		mDataLs.add(new DataVo("2017/04/10", "提现成功"));

		adapter = new mAdapter(this);
		lv_progress.setAdapter(adapter);

	}

	@OnClick(R.id.ll_left)
	public void doGoBack(View view)
	{
		finish();
	}

	private void sendReqPullCash()
	{
		mReqId = RequestIds.REQUEST_ACCOUNT_SEARCH;
		CommonRequestVo vo = new CommonRequestVo();

		vo.setRequest("flow.base.terminal.apply.cash");
		vo.setJsondata(null);

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
			Toast.makeText(ProgressCashActivity.this, respBody.toString(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onResponse(String jsonBody, String session)
		{
			WaitingDialog.getWaitDialog().disMiss();

			try
			{
				if (mReqId == RequestIds.REQUEST_ACCOUNT_SEARCH)
				{
					RespAccountRestVo responseVo = GsonHelper.json2Obj(jsonBody, RespAccountRestVo.class);

					if (null == responseVo)
					{
						Toast.makeText(ProgressCashActivity.this, jsonBody, Toast.LENGTH_LONG).show();
						return;
					}

					if (OkHttpClientManager.RESP_OK.equals(responseVo.getRes()))
					{
						Toast.makeText(ProgressCashActivity.this, "用户提现受理成功！", Toast.LENGTH_LONG).show();
						finish();

					} else
					{
						Toast.makeText(ProgressCashActivity.this, responseVo.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}

			} catch (Exception e)
			{
				Toast.makeText(ProgressCashActivity.this, e.toString(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
	};

	class mAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater;

		public mAdapter(Context context)
		{
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			// TODO 自动生成的方法存根
			return mDataLs.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO 自动生成的方法存根
			return mDataLs.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO 自动生成的方法存根
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (view == null)
			{
				holder = new ViewHolder();
				view = mInflater.inflate(R.layout.lv_progress_item, null);
//				holder.iv_image_title = (ImageView) view.findViewById(R.id.iv_image_title);
//				holder.iv_image_line = (ImageView) view.findViewById(R.id.iv_image_line);
				holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
				holder.tv_state = (TextView) view.findViewById(R.id.tv_state);
				view.setTag(holder);
			} else
			{
				holder = (ViewHolder) view.getTag();
			}

			holder.tv_date.setText(mDataLs.get(position).getDate());
			holder.tv_state.setText(mDataLs.get(position).getState());
			return view;
		}

		class ViewHolder
		{
//			public ImageView iv_image_title;
//			public ImageView iv_image_line;
			public TextView tv_date;
			public TextView tv_state;
		}

	}

}
