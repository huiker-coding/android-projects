package com.yinliubao.client.functions.reset.bean;

/***********************************************************************************************
  {  
    "terminal_userid","终端用户ID",
	"user_reset_pwd","登录密码,md5加密",
    "idt_code","手机验证码"
  }
 ***********************************************************************************************/
public class ReqPwdResetVo
{
	/** 用户手机号码*/
	private String terminal_userid;

	/** 用户重置后的密码*/
	private String user_reset_pwd;

	/** 手机验证码*/
	private String idt_code;

	public String getTerminal_userid()
	{
		return terminal_userid;
	}

	public void setTerminal_userid(String terminal_userid)
	{
		this.terminal_userid = terminal_userid;
	}

	public String getUser_reset_pwd()
	{
		return user_reset_pwd;
	}

	public void setUser_reset_pwd(String user_reset_pwd)
	{
		this.user_reset_pwd = user_reset_pwd;
	}

	public String getIdt_code()
	{
		return idt_code;
	}

	public void setIdt_code(String idt_code)
	{
		this.idt_code = idt_code;
	}

}
