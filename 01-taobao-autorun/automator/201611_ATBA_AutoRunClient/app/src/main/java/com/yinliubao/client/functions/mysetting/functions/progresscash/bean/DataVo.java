package com.yinliubao.client.functions.mysetting.functions.progresscash.bean;

public class DataVo
{
	private String date;
	private String state;

	public DataVo(String date,String state)
	{
		this.date = date;
		this.state = state;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
