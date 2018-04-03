package com.yinliubao.client.functions.taskhall.bean.task_receive;

public class ReqTaskReceiveVo
{
	/** 用户手机号码*/
	private String terminal_userid;
	
	/** 会话Session*/
	private String login_session;

	public String getTerminalUserid()
	{
		return terminal_userid;
	}

	public void setTerminalUserid(String terminal_userid)
	{
		this.terminal_userid = terminal_userid;
	}

	public String getLogin_session()
	{
		return login_session;
	}

	public void setLogin_session(String login_session)
	{
		this.login_session = login_session;
	}

}
