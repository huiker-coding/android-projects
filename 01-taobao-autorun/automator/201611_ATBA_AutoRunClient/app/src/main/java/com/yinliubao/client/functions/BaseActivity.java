package com.yinliubao.client.functions;

import android.app.Activity;
import android.widget.Toast;

public class BaseActivity extends Activity
{
	private Toast lastToast;
	private Toast currentToast;
	
	protected void showOnlyOneToast(String content)
	{
		currentToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
		if (lastToast != null)
		{
			lastToast.cancel();
		}
		lastToast = currentToast;
		currentToast.show();
	}

}
