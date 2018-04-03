package com.yinliubao.client.core.storage.database.dao;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.yinliubao.client.core.storage.database.DatabaseHelper;
import com.yinliubao.client.core.storage.database.bean.TaskInfoBean;

public class TaskInfoDao
{
	private Dao<TaskInfoBean, Integer> taskInfoDaoOpe;
	private DatabaseHelper helper;
	private TaskInfoDao taskInfoDao;
	
	 @SuppressWarnings("unchecked")
	public TaskInfoDao(Context context){
		try
		{
			helper=DatabaseHelper.getHelper(context);
			taskInfoDaoOpe=helper.getDao(TaskInfoBean.class);
		} catch ( SQLException e)
		{
			e.printStackTrace();
		}
	}
	 
	// 增
	public int add(TaskInfoBean dishInfo)
	{
		try
		{
			return taskInfoDaoOpe.create(dishInfo);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

}
