package com.yinliubao.client.functions.taskhall.bean.task_return;

import java.io.Serializable;

public class RespTaskReturnVo implements Serializable
{
	/** 串行版本标识*/ 
	private static final long serialVersionUID = 5575655118101630965L;

	/** 结果返回标识*/
	private String body_res;
	
	/** 任务号*/
	private String task_id;
	
	/** 收入金额*/
	private String income_sum;

	public String getBody_res()
	{
		return body_res;
	}

	public void setBody_res(String body_res)
	{
		this.body_res = body_res;
	}

	public String getTask_id()
	{
		return task_id;
	}

	public void setTask_id(String task_id)
	{
		this.task_id = task_id;
	}

	public String getIncome_sum()
	{
		return income_sum;
	}

	public void setIncome_sum(String income_sum)
	{
		this.income_sum = income_sum;
	}
	
	
}
