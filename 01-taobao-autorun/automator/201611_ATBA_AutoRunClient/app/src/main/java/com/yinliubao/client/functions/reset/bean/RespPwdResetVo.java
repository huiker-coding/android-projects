package com.yinliubao.client.functions.reset.bean;

import java.io.Serializable;

public class RespPwdResetVo implements Serializable
{
	/** 串行版本标识*/ 
	private static final long serialVersionUID = -8668473463789330617L;
	
	/** 返回结果标识*/
	private String body_res;

	public String getBodyRes()
	{
		return body_res;
	}

	public void setBodyRes(String body_res)
	{
		this.body_res = body_res;
	}

}
