package com.example.webtest.base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Fragment;
import android.os.Environment;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * @author z.h
 * @des commmon tools
 */
public class WA_BaseFragment extends Fragment
{
	private static final String LOG_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/web_auto.log";

	/** ע�����Զ�ִ�е�JS���� */
	protected String doAutoTest(String code)
	{
		return "function doAutoTest() { " + code + "}";
	}

	/** ��װ����JS���� */
	protected String buildTest(String logicStr)
	{
		String js = "var newscript = document.createElement(\"script\");" + "newscript.text = window.onload=doAutoTest();" + logicStr + "document.body.appendChild(newscript);";
		return js;
	}

	/** Load JS���룬Ȼ����Զ�ִ��doAutoTest()������� */
	protected void loadUrl(WebView webView, String logicStr)
	{
		String js = buildTest(logicStr);
		webView.loadUrl("javascript:" + js);
	}

	/** ע�뱾���ļ��е�JS���� */
	protected String getJsFromFile(Activity mContext, String jsPath)
	{
		InputStream in = null;
		try
		{
			in = mContext.getBaseContext().getAssets().open(jsPath);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		byte buff[] = new byte[1024];
		ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
		do
		{
			int numRead = 0;
			try
			{
				numRead = in.read(buff);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			if (numRead <= 0)
			{
				break;
			}
			fromFile.write(buff, 0, numRead);
		} while (true);

		return fromFile.toString();
	}

	/** ����������־�ļ� */
	protected void createLog(String infoStr)
	{

		FileWriter fw = null;
		try
		{
			fw = new FileWriter(LOG_FILE_PATH, true);
			fw.write("\r\n" + infoStr + "\r\n");
			fw.flush();

		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				fw.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	/** ɾ��������־�ļ� */
	protected void deleteLog()
	{
		File file = new File(LOG_FILE_PATH);
		if (file.exists())
		{
			file.delete();
		} else
		{
			Toast.makeText(getActivity(), "This file is not exist!", Toast.LENGTH_SHORT).show();
		}
	}

	/** �߳���ʱ */
	protected void doSleep(int time)
	{
		try
		{
			Thread.sleep(time * 1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/** �߳���ֹ */
	protected void doInterreput()
	{
		Thread.interrupted();
	}

}
