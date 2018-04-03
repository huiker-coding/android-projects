package com.yinliubao.client.functions.regist.bean;

/***********************************************************************************************************
 {
   "terminal_userid","终端用户ID,手机号码",
   "user_pwd","登录密码,md5",
   "device_sn","设备序号",
   "account","支付宝账户",
   "idt_code","手机验证码",
  }
 ************************************************************************************************************/
public class ReqUserRegistVo
{
	/** 用户手机号码*/
	private String terminal_userid;

	/** 用户登录密码*/
	private String user_pwd;

	/** 支付宝账户*/
	private String account;
	
	/** 设备序号*/
	private String device_sn;
	
	/** 验证码*/
	private String idt_code;

	public String getTerminalUserid()
	{
		return terminal_userid;
	}

	public void setTerminalUserid(String terminal_userid)
	{
		this.terminal_userid = terminal_userid;
	}

	public String getUserPwd()
	{
		return user_pwd;
	}

	public void setUserPwd(String user_pwd)
	{
		this.user_pwd = user_pwd;
	}

	public String getDeviceSn()
	{
		return device_sn;
	}

	public void setDeviceSn(String device_sn)
	{
		this.device_sn = device_sn;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getIdtCode()
	{
		return idt_code;
	}

	public void setIdtCode(String idt_code)
	{
		this.idt_code = idt_code;
	}

}
