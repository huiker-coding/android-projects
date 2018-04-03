package com.yinliubao.client.functions_common.bean;

import java.io.Serializable;

public class CommonResponseVo<T> implements Serializable
{
	private String res;

	private String message;

	private T orders;

	public String getRes()
	{
		return res;
	}

	public void setRes(String res)
	{
		this.res = res;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public T getOrders()
	{
		return orders;
	}

	public void setOrders(T orders)
	{
		this.orders = orders;
	}

}
