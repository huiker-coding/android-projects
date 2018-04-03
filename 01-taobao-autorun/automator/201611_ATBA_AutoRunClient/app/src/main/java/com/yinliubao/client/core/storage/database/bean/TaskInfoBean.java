package com.yinliubao.client.core.storage.database.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tab_task")
public class TaskInfoBean
{
	@DatabaseField(generatedId=true)
	private int id;
	
	@DatabaseField(columnName="order_bn")
	private String orderBn;
	
	@DatabaseField(columnName="task_id")
	private String taskId;
	
	@DatabaseField(columnName="task_state")
	private String taskState;

	public TaskInfoBean(){
		
	}
	
	public TaskInfoBean(int id,String orderBn,String taskId,String taskState){
		this.id=id;
		this.orderBn=orderBn;
		this.taskId=taskId;
		this.taskState=taskState;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getOrderBn()
	{
		return orderBn;
	}

	public void setOrderBn(String orderBn)
	{
		this.orderBn = orderBn;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getTaskState()
	{
		return taskState;
	}

	public void setTaskState(String taskState)
	{
		this.taskState = taskState;
	}

	
}
