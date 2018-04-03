package com.example.controller.io;

public class BaseSearchVo
{
	/** 搜索关键字 */
	private String searchKey;

	/** 宝贝标题 */
	private String goodsTitle;

	/** 店面停留秒数 */
	private Integer staySecond;

	/** 货比三家 */
	private Integer compareNum;
	
	
	public String getSearchKey()
	{
		return searchKey;
	}

	public void setSearchKey(String searchKey)
	{
		this.searchKey = searchKey;
	}

	public String getGoodsTitle()
	{
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle)
	{
		this.goodsTitle = goodsTitle;
	}

	public Integer getStaySecond()
	{
		return staySecond;
	}

	public void setStaySecond(Integer staySecond)
	{
		this.staySecond = staySecond;
	}

	public Integer getCompareNum()
	{
		return compareNum;
	}

	public void setCompareNum(Integer compareNum)
	{
		this.compareNum = compareNum;
	}

}
