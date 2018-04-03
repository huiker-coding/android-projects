package com.example.controller.io;

import java.math.BigDecimal;

public class ConditionFilterVo
{
	/** 是否设置了筛选条件*/
	private Boolean isSetFilter;
	
	/** 店铺红包*/
	private Boolean isShopRed;
	
	/** 包邮*/
	private Boolean isFreeShip;
	
	/** 天猫*/
	private Boolean isTmall;
	
	/** 花呗分期*/
	private Boolean isHuaBei;
	
	/** 全球购*/
	private Boolean isGlobalBuy;
	
	/** 消费者保障*/
	private Boolean isConsumerEnsure;
	
	/** 天猫国际*/
	private Boolean isTmallGlobal;
	
	/** 淘金币抵钱*/
	private Boolean isGoldCoin;
	
	/** 7+天内退货*/
	private Boolean isSevenDayReturn;
	
	/** 货到付款*/
	private Boolean isCOD;
	
	/** 通用排序*/
	private Boolean isGeneralSort;

	/** 最低价*/
	private BigDecimal minPrice;
	
	/** 最高价*/
	private BigDecimal maxPrice;
	
	/** 发货地*/
	private String deliverPlace;

	

	public Boolean getIsSetFilter()
	{
		return isSetFilter;
	}

	public void setIsSetFilter(Boolean isSetFilter)
	{
		this.isSetFilter = isSetFilter;
	}

	public Boolean getIsShopRed()
	{
		return isShopRed;
	}

	public void setIsShopRed(Boolean isShopRed)
	{
		this.isShopRed = isShopRed;
	}

	public Boolean getIsFreeShip()
	{
		return isFreeShip;
	}

	public void setIsFreeShip(Boolean isFreeShip)
	{
		this.isFreeShip = isFreeShip;
	}

	public Boolean getIsTmall()
	{
		return isTmall;
	}

	public void setIsTmall(Boolean isTmall)
	{
		this.isTmall = isTmall;
	}

	public Boolean getIsHuaBei()
	{
		return isHuaBei;
	}

	public void setIsHuaBei(Boolean isHuaBei)
	{
		this.isHuaBei = isHuaBei;
	}

	public Boolean getIsGlobalBuy()
	{
		return isGlobalBuy;
	}

	public void setIsGlobalBuy(Boolean isGlobalBuy)
	{
		this.isGlobalBuy = isGlobalBuy;
	}

	public Boolean getIsConsumerEnsure()
	{
		return isConsumerEnsure;
	}

	public void setIsConsumerEnsure(Boolean isConsumerEnsure)
	{
		this.isConsumerEnsure = isConsumerEnsure;
	}

	public Boolean getIsTmallGlobal()
	{
		return isTmallGlobal;
	}

	public void setIsTmallGlobal(Boolean isTmallGlobal)
	{
		this.isTmallGlobal = isTmallGlobal;
	}

	public Boolean getIsGoldCoin()
	{
		return isGoldCoin;
	}

	public void setIsGoldCoin(Boolean isGoldCoin)
	{
		this.isGoldCoin = isGoldCoin;
	}

	public Boolean getIsSevenDayReturn()
	{
		return isSevenDayReturn;
	}

	public void setIsSevenDayReturn(Boolean isSevenDayReturn)
	{
		this.isSevenDayReturn = isSevenDayReturn;
	}

	public Boolean getIsCOD()
	{
		return isCOD;
	}

	public void setIsCOD(Boolean isCOD)
	{
		this.isCOD = isCOD;
	}

	public Boolean getIsGeneralSort()
	{
		return isGeneralSort;
	}

	public void setIsGeneralSort(Boolean isGeneralSort)
	{
		this.isGeneralSort = isGeneralSort;
	}

	public BigDecimal getMinPrice()
	{
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice)
	{
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice()
	{
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice)
	{
		this.maxPrice = maxPrice;
	}

	public String getDeliverPlace()
	{
		return deliverPlace;
	}

	public void setDeliverPlace(String deliverPlace)
	{
		this.deliverPlace = deliverPlace;
	}

}
