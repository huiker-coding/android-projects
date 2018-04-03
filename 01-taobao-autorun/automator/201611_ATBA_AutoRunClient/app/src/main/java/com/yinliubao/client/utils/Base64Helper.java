package com.yinliubao.client.utils;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class Base64Helper
{
	/**
	 * BASE64字符串转化为二进制数据编码
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 ***/
	public static String encode(String str)
	{
		try
		{
			return new String(Base64.encodeBase64(str.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 二进制数据编码为BASE64字符串
	 * 
	 * @param bytes
	 * @return
	 ***/
	public static String decode(String str)
	{
		try
		{
			return new String(Base64.decodeBase64(str.getBytes()),"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return "";
	}

}