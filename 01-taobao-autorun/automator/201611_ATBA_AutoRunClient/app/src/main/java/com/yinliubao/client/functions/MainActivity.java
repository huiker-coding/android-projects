package com.yinliubao.client.functions;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yinliubao.client.R;
import com.yinliubao.client.functions.incomehistory.IncomeHistoryFragment;
import com.yinliubao.client.functions.mysetting.MySettingFragment;
import com.yinliubao.client.functions.taskhall.TaskHallFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity
{

	/** 返回按钮 */
	@InjectView(R.id.ll_left)
	LinearLayout ll_left;

	/** 页面标题 */
	@InjectView(R.id.tv_title)
	TextView tv_title;

	/** 页面底下的Tab*/
	@InjectView(R.id.radio_group)
	RadioGroup radio_group;

	/** 任务大厅*/
	@InjectView(R.id.rb_task_hall)
	RadioButton rb_task_hall;

	/** 收益历史*/
	@InjectView(R.id.rb_income_history)
	RadioButton rb_income_history;

	/** 设置中心*/
	@InjectView(R.id.rb_setting)
	RadioButton rb_setting;

	private static int GET_CF = 0X00001;
	private static String Tag1 = "mTaskHallFragment";
	private static String Tag2 = "mIncomeHistoryFragment";
	private static String Tag3 = "mMySettingFragment";
	public Fragment currentFragment;
	private long exitTime = 0;

	private Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			if (msg.what == GET_CF)
			{
				// 通过Tag标记得到当前Fragment对象
				currentFragment = getFragmentManager().findFragmentByTag(msg.obj.toString());
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		initData();
		setListener();
	}

	private void initData()
	{
		getFragmentManager().beginTransaction().add(R.id.ll_container, new TaskHallFragment(), Tag1).commit();
		setCurrentFragmentByTag(Tag1);
		tv_title.setText("任务大厅");
	}


	private void setListener()
	{
		radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkId)
			{
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				switch (checkId)
				{
				case R.id.rb_task_hall:
					Fragment fragment1 = getFragmentManager().findFragmentByTag(Tag1);
					if (fragment1.isHidden())
					{
						getFragmentManager().beginTransaction().hide(currentFragment).show(fragment1).commit();
					}
					setCurrentFragmentByTag(Tag1);
					tv_title.setText("任务大厅");
					break;
				case R.id.rb_income_history:
					// 通过Tag得到当前Fragment的对象
					Fragment fragment2 = getFragmentManager().findFragmentByTag(Tag2);
					// 若没有得到表示当前Fragment还未创建过，创建之，并隐藏上一个Fragment
					if (fragment2 == null)
					{
						getFragmentManager().beginTransaction().add(R.id.ll_container, new IncomeHistoryFragment(), Tag2).hide(currentFragment).commit();

					} else if (fragment2.isHidden())
					{
						getFragmentManager().beginTransaction().hide(currentFragment).show(fragment2).commit();
					}
					// 最后将当前Fragment设置为当前Fragment操作对象
					setCurrentFragmentByTag(Tag2);
					tv_title.setText("收益历史");
					break;
				case R.id.rb_setting:
					// 通过Tag得到当前Fragment的对象
					Fragment fragment3 = getFragmentManager().findFragmentByTag(Tag3);
					// 若没有得到表示当前Fragment还未创建过，创建之，并隐藏上一个Fragment
					if (fragment3 == null)
					{
						getFragmentManager().beginTransaction().add(R.id.ll_container, new MySettingFragment(), Tag3).hide(currentFragment).commit();

					} else if (fragment3.isHidden())
					{
						getFragmentManager().beginTransaction().hide(currentFragment).show(fragment3).commit();
					}
					// 最后将当前Fragment设置为当前Fragment操作对象
					setCurrentFragmentByTag(Tag3);
					tv_title.setText("设置中心");
					break;
				}
				setTabState();// 设置状态
				transaction.commit();
			}
		});
	}

	private void setCurrentFragmentByTag(String tag)
	{
		Message msg = new Message();
		msg.what = GET_CF;
		msg.obj = tag;
		handler.sendMessage(msg);
	}

	private void setTabState()
	{
		setTaskHallState();
		setIncomeHistoryState();
		setMySettingState();

	}

	private void setTaskHallState()
	{
		if (rb_task_hall.isChecked())
		{
			rb_task_hall.setTextColor(getResources().getColor(R.color.green));
		} else
		{
			rb_task_hall.setTextColor(getResources().getColor(R.color.black));
		}
	}

	private void setIncomeHistoryState()
	{
		if (rb_income_history.isChecked())
		{
			rb_income_history.setTextColor(getResources().getColor(R.color.green));
		} else
		{
			rb_income_history.setTextColor(getResources().getColor(R.color.black));
		}
	}

	private void setMySettingState()
	{
		if (rb_setting.isChecked())
		{
			rb_setting.setTextColor(getResources().getColor(R.color.green));
		} else
		{
			rb_setting.setTextColor(getResources().getColor(R.color.black));
		}
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else
			{
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
