package com.example.controller.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Helper
{
	/**
	 * BASE64�ַ���ת��Ϊ���������ݱ���
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(String str)
	{
		return new String(Base64.encodeBase64(str.getBytes()));
	}

	/**
	 * ���������ݱ���ΪBASE64�ַ���
	 * 
	 * @param bytes
	 * @return
	 */
	public static String decode(String str)
	{
		return new String(Base64.decodeBase64(str.getBytes()));
	}

}