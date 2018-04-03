package com.example.controller.io;

public class ExtraActionVo
{
	/** 收藏宝贝*/
	private Boolean isFavorite;
	
	/** 关注商铺*/
	private Boolean isAtShops;
	
	/** 查看产品参数*/
	private Boolean isScanPrdPrameter;
	
	/** 进店看看*/
	private Boolean isEnterShop;
	
	/** 查看更多评论*/
	private Boolean isScanComment;
	
	/** 模拟用户咨询*/
	private String userCounselStr;

	public Boolean getIsFavorite()
	{
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite)
	{
		this.isFavorite = isFavorite;
	}

	public Boolean getIsAtShops()
	{
		return isAtShops;
	}

	public void setIsAtShops(Boolean isAtShops)
	{
		this.isAtShops = isAtShops;
	}

	public Boolean getIsScanPrdPrameter()
	{
		return isScanPrdPrameter;
	}

	public void setIsScanPrdPrameter(Boolean isScanPrdPrameter)
	{
		this.isScanPrdPrameter = isScanPrdPrameter;
	}

	public Boolean getIsEnterShop()
	{
		return isEnterShop;
	}

	public void setIsEnterShop(Boolean isEnterShop)
	{
		this.isEnterShop = isEnterShop;
	}

	public Boolean getIsScanComment()
	{
		return isScanComment;
	}

	public void setIsScanComment(Boolean isScanComment)
	{
		this.isScanComment = isScanComment;
	}

	public String getUserCounselStr()
	{
		return userCounselStr;
	}

	public void setUserCounselStr(String userCounselStr)
	{
		this.userCounselStr = userCounselStr;
	}

}
