package com.yinliubao.client.core.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferHelper
{
	/** SP文件名*/
	private static final String SP_FILE = "ylb_prefs";
	
	/** 客户端登录session */
	private static final String LOGION_SESSION= "login_session";
	
	private static final String USER_NAME= "user_name";

	
	private static SharedPreferHelper ypSpManager;
	private Context appContext;


	private SharedPreferHelper(Context context){
		this.appContext=context;
	}
	
	public static SharedPreferHelper getInstance(Context context){
		if(null==ypSpManager){
			ypSpManager=new SharedPreferHelper(context);
		}
		return ypSpManager;
	}
	
	/** 
	 * 保存登录的session
	 * @param
	 * @return void 
	 **/
	public void saveLoginSesion(String loginSession)
	{
		SharedPreferences preferences = appContext.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(LOGION_SESSION, loginSession);
		editor.apply();
	}

	/** 
	 * 获取登录的session
	 * @param
	 * @return String 
	 **/
	public String getLoginSession()
	{
		SharedPreferences preferences = appContext.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		String loginSession = preferences.getString(LOGION_SESSION, "");
		return loginSession;
	}
	
	/** 
	 * 保存登录用户名
	 * @param
	 * @return void 
	 **/
	public void saveUserName(String userName)
	{
		SharedPreferences preferences = appContext.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(USER_NAME, userName);
		editor.apply();
	}

	/** 
	 * 获取登录用户名
	 * @param
	 * @return String 
	 **/
	public String getUserName()
	{
		SharedPreferences preferences = appContext.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		String userName = preferences.getString(USER_NAME, "");
		return userName;
	}
	
	
}
