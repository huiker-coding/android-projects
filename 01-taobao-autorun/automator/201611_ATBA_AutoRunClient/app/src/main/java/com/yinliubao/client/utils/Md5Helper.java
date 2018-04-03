package com.yinliubao.client.utils;

import java.security.MessageDigest;

public class Md5Helper
{
	
	/** 
	 * 将字符串进行MD5加密
	 * @param str 原始字符串
	 * @return String 
	 **/
	public static String md5(String str)
	{
		MessageDigest messageDigest = null;
		StringBuffer md5StrBuff = new StringBuffer();
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			byte[] byteArray = messageDigest.digest();
			for (int i = 0; i < byteArray.length; i++)
			{
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return md5StrBuff.toString();
	}
}
