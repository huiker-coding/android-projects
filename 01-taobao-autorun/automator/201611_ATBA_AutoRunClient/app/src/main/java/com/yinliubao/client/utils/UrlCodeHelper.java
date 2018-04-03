package com.yinliubao.client.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description: WS的Url地址需要Encode2次，奇怪的问题
 * @author gray.z
 * @date 2017年2月16日 下午2:50:37
 */
public class UrlCodeHelper
{
	/** 若为Http请求参数则要进行1次UrlDecode*/
	public static String httpUrlEncode(String paramStr)
	{
		String encodeUrl = null;
		try
		{
			encodeUrl = URLEncoder.encode(paramStr, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return encodeUrl;
	}

}
