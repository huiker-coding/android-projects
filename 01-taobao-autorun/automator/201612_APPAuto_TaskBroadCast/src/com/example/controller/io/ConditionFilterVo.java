package com.example.controller.io;

import java.math.BigDecimal;

public class ConditionFilterVo
{
	/** �Ƿ�������ɸѡ����*/
	private Boolean isSetFilter;
	
	/** ���̺��*/
	private Boolean isShopRed;
	
	/** ����*/
	private Boolean isFreeShip;
	
	/** ��è*/
	private Boolean isTmall;
	
	/** ���·���*/
	private Boolean isHuaBei;
	
	/** ȫ��*/
	private Boolean isGlobalBuy;
	
	/** �����߱���*/
	private Boolean isConsumerEnsure;
	
	/** ��è����*/
	private Boolean isTmallGlobal;
	
	/** �Խ�ҵ�Ǯ*/
	private Boolean isGoldCoin;
	
	/** 7+�����˻�*/
	private Boolean isSevenDayReturn;
	
	/** ��������*/
	private Boolean isCOD;
	
	/** ͨ������*/
	private Boolean isGeneralSort;

	/** ��ͼ�*/
	private BigDecimal minPrice;
	
	/** ��߼�*/
	private BigDecimal maxPrice;
	
	/** ������*/
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
