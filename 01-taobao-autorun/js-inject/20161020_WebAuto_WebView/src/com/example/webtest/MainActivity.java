package com.example.webtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.webtest.io.WA_Parameters;

public class MainActivity extends Activity
{
	private static final String[] TMALL_FLAG ={ "��", "��" };

	private EditText keywordEt;
	private EditText titleEt;
	private Spinner storeTypeSp;

	private String keywordStr;
	private String titleStr;
	private boolean isTmall = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		initData();
		setListener();
	}

	private void findViews()
	{
		keywordEt = (EditText) findViewById(R.id.et_keyword);
		titleEt = (EditText) findViewById(R.id.et_title);
		storeTypeSp = (Spinner) findViewById(R.id.sp_store_type);
	}

	private void initData()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, TMALL_FLAG);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		storeTypeSp.setAdapter(adapter);
	}

	private void setListener()
	{
		storeTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				switch (position)
				{
				case 0:
					isTmall = false;
					break;
				case 1:
					isTmall = true;
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
	}

	public void clickMe(View view)
	{
		//ƴװĿ����������
		keywordStr = keywordEt.getText().toString();
		titleStr = titleEt.getText().toString();
		
		WA_Parameters parameter = new WA_Parameters();
		parameter.setKeywordStr(keywordStr);
		parameter.setTitleStr(titleStr);
		parameter.setIsTMall(isTmall);

		//����ִ���Զ�����Fragmentҳ��
		WA_MainFragment.start(MainActivity.this,R.id.container, parameter);
	}

}
