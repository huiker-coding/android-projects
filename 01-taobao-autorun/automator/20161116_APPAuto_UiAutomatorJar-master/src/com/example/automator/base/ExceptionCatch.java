package com.example.automator.base;

import java.io.IOException;

public abstract class ExceptionCatch extends YundaTestCase
{
	private String inputMethodStr;
	
	public ExceptionCatch(String inputMethodStr)
	{
		this.inputMethodStr= inputMethodStr;
	}
	
	public abstract void caseLoad() throws  Exception;
	
	/** Function：捕获异常，若执行失败则还原输入法  */
	public void runCase() throws IOException
	{
		try
		{
			caseLoad();
		} catch (Exception e)
		{
			doExec("ime set "+inputMethodStr);
		}
	}

}
