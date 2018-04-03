package com.example.controller.io;

public class TaskParameter
{
	private BaseSearchVo baseSearchVo;

	private ConditionFilterVo conditionFilterVo;

	private ExtraActionVo extraActionVo;
	
	private SysParameterVo sysParameterVo;

	public BaseSearchVo getBaseSearchVo()
	{
		return baseSearchVo;
	}

	public void setBaseSearchVo(BaseSearchVo baseSearchVo)
	{
		this.baseSearchVo = baseSearchVo;
	}

	public ConditionFilterVo getConditionFilterVo()
	{
		return conditionFilterVo;
	}

	public void setConditionFilterVo(ConditionFilterVo conditionFilterVo)
	{
		this.conditionFilterVo = conditionFilterVo;
	}

	public ExtraActionVo getExtraActionVo()
	{
		return extraActionVo;
	}

	public void setExtraActionVo(ExtraActionVo extraActionVo)
	{
		this.extraActionVo = extraActionVo;
	}

	public SysParameterVo getSysParameterVo()
	{
		return sysParameterVo;
	}

	public void setSysParameterVo(SysParameterVo sysParameterVo)
	{
		this.sysParameterVo = sysParameterVo;
	}

}
