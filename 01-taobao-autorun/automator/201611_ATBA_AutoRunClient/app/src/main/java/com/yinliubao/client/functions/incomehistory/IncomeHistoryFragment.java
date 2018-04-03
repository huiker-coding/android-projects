package com.yinliubao.client.functions.incomehistory;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.yinliubao.client.R;
import com.yinliubao.client.functions.mysetting.MySettingFragment;
import com.yinliubao.client.functions.taskhall.TaskHallFragment;
import com.yinliubao.client.views.HistogramView;

public class IncomeHistoryFragment extends Fragment
{
	private HistogramView green;
	private RadioGroup radio_group;
	private RadioButton rb_sevenday_income;
	private RadioButton rb_history_income;
	private LinearLayout ll_senvenday_history;
	private LinearLayout ll_income_history;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_income_history, null);
		findViews(view);
		setListener();
		initData();
		return view;
	}

	private void findViews(View view)
	{
		green = (HistogramView) view.findViewById(R.id.green);
		radio_group = (RadioGroup) view.findViewById(R.id.radio_group);
		rb_sevenday_income = (RadioButton) view.findViewById(R.id.rb_sevenday_income);
		rb_history_income = (RadioButton) view.findViewById(R.id.rb_history_income);
		ll_senvenday_history=(LinearLayout)view.findViewById(R.id.ll_senvenday_history);
		ll_income_history=(LinearLayout)view.findViewById(R.id.ll_income_history);
	}

	private void setListener(){
		radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkId)
			{
				switch (checkId)
				{
				case R.id.rb_sevenday_income:
					ll_senvenday_history.setVisibility(View.VISIBLE);
					ll_income_history.setVisibility(View.GONE);
					break;
				case R.id.rb_history_income:
					ll_income_history.setVisibility(View.VISIBLE);
					ll_senvenday_history.setVisibility(View.GONE);
					break;
				}
			}
		});
	}

	private void initData()
	{
		ll_senvenday_history.setVisibility(View.VISIBLE);
		green.start(2);
	}

}
