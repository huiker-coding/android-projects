package com.yinliubao.client.utils;

import com.google.gson.Gson;

public class GsonHelper
{
	private static final Gson gson;

	static
	{
		gson = new Gson();
	}

	/**************************************************** public暴露方法begin******************************************/
	
	/** 
	 * 将对象转成json字符串
	 * @param  obj 对象
	 * @return String 
	 **/
	public static String obj2Json(Object obj)
	{
		return gson.toJson(obj);
	}

	/** 
	 * 将json字符串转成JavaBean对象
	 * @param  javaBean
	 * @return T 
	 **/
	public static <T> T json2Obj(String jsonStr, Class<T> javaBean)
	{
		try
		{
			return gson.fromJson(jsonStr, javaBean);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**************************************************** public暴露方法end******************************************/
	
}
