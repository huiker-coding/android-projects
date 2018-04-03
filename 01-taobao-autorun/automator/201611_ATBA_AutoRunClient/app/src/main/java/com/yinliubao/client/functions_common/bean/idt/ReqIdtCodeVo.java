package com.yinliubao.client.functions_common.bean.idt;


/********************************************************************************************************
  {
	"terminal_userid","终端用户ID",
    "idt_type","验证码类型,register,reset_pwd"
  } 
 ********************************************************************************************************/
public class ReqIdtCodeVo
{
	/** 用户手机号码*/
	private String terminal_userid;
	
	/** 验证码类型*/
	private String idt_type;

	public ReqIdtCodeVo(){
		
	}
	
	public ReqIdtCodeVo(String terminal_userid,String idt_type){
		this.terminal_userid=terminal_userid;
		this.idt_type=idt_type;
	}
	
	public String getTerminal_userid()
	{
		return terminal_userid;
	}

	public void setTerminal_userid(String terminal_userid)
	{
		this.terminal_userid = terminal_userid;
	}

	public String getIdt_type()
	{
		return idt_type;
	}

	public void setIdt_type(String idt_type)
	{
		this.idt_type = idt_type;
	}

}
