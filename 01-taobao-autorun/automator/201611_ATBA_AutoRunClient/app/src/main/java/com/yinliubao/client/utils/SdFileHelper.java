package com.yinliubao.client.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import android.os.Environment;

public class SdFileHelper
{
	private final static String FILE_YLB_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.ylb/task_history.log";
	private final static String FILE_YLB_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.ylb";

	
	private static boolean createFolder(){
		File file = new File(FILE_YLB_FOLDER);
		if(file.exists()){
			return true;
		}else{
			return file.mkdir();
		}
	}
	
	
	
	/**####################################### 暴露的public方法begin #######################################*/
	
	/** 
	 * 向指定目录文件下写入字符串内容
	 * @param content 字符串内容
	 * @return void 
	 **/
	public static void writeLogFile(String content)
	{
		try
		{
			if(!createFolder()){
				return;
			}
			
			File file = new File(FILE_YLB_FILE);
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/** 
	 * 读取指定目录文件下面的字符串内容
	 * @param
	 * @return String 
	 **/
	public static String readLogFile()
	{
		BufferedReader reader = null;
		try
		{
			File file = new File(FILE_YLB_FILE);
			reader = new BufferedReader(new FileReader(file));
			reader.close();
			return reader.readLine();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**####################################### 暴露的public方法end #######################################*/

}

