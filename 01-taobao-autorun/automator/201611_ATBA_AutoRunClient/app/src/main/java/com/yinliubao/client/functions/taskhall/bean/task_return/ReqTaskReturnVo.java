package com.yinliubao.client.functions.taskhall.bean.task_return;


/********************************************************************************************************
 {
	"task_id":"任务号,必填",
	"search_result":"搜索结果,[0=失败,1=成功],必填",
	"task_status":"任务状态,[finish=完成,fail=失败],必填",
	"search_result_time":"搜索结果时间点,2017-04-28 10:00:00",
	"visit_time":"浏览开始时间",
	"add_cart_time":"加入购物车时间",
	"collect_shop_time":"收藏店铺时间",
	"collect_good_time":"收藏宝贝时间",
	"share_time":"分享宝贝时间",
	"evaluate_time":"评价时间",
	"compare_time":"货比三家时间",S
	"visit_use_time":"浏览时长",
	"leave_type":"离店方式,[1=推荐跳离,2=直接跳离]",
	"leave_time":"离店时间",
	"finish_time":"任务完成时间"
 }
**********************************************************************************************************/
public class ReqTaskReturnVo
{
	private String order_id;
	
	/** 任务号*/
	private String task_id;

	/** 搜索结果*/
	private int search_result;

	/** 任务执行状态*/
	private boolean task_status;

	/** 搜索得到目标结果时间*/
	private String search_result_time;

	/** 浏览开始时间*/
	private String visit_time;

	/** 加入购物车时间*/
	private String add_cart_time;

	/** 收藏店铺时间*/
	private String collect_shop_time;

	/** 收藏宝贝时间*/
	private String collect_good_time;

	/** 宝贝分享时间*/
	private String share_time;

	/** 浏览评价时间*/
	private String evaluate_time;

	/** 货比三家时间*/
	private String compare_time;

	/** 浏览总耗时*/
	private int visit_use_time;

	/** 离店方式*/
	private int leave_type;

	/** 离店时间*/
	private String leave_time;

	/** 完成时间*/
	private String finish_time;

	
	public String getOrder_id()
	{
		return order_id;
	}

	public void setOrder_id(String order_id)
	{
		this.order_id = order_id;
	}

	public String getTask_id()
	{
		return task_id;
	}

	public void setTask_id(String task_id)
	{
		this.task_id = task_id;
	}

	public int getSearch_result()
	{
		return search_result;
	}

	public void setSearch_result(int search_result)
	{
		this.search_result = search_result;
	}

	public boolean isTask_status()
	{
		return task_status;
	}

	public void setTask_status(boolean task_status)
	{
		this.task_status = task_status;
	}

	
	public int getVisit_use_time()
	{
		return visit_use_time;
	}

	public void setVisit_use_time(int visit_use_time)
	{
		this.visit_use_time = visit_use_time;
	}

	public int getLeave_type()
	{
		return leave_type;
	}

	public void setLeave_type(int leave_type)
	{
		this.leave_type = leave_type;
	}

	public String getSearch_result_time()
	{
		return search_result_time;
	}

	public void setSearch_result_time(String search_result_time)
	{
		this.search_result_time = search_result_time;
	}

	public String getVisit_time()
	{
		return visit_time;
	}

	public void setVisit_time(String visit_time)
	{
		this.visit_time = visit_time;
	}

	public String getAdd_cart_time()
	{
		return add_cart_time;
	}

	public void setAdd_cart_time(String add_cart_time)
	{
		this.add_cart_time = add_cart_time;
	}

	public String getCollect_shop_time()
	{
		return collect_shop_time;
	}

	public void setCollect_shop_time(String collect_shop_time)
	{
		this.collect_shop_time = collect_shop_time;
	}

	public String getCollect_good_time()
	{
		return collect_good_time;
	}

	public void setCollect_good_time(String collect_good_time)
	{
		this.collect_good_time = collect_good_time;
	}

	public String getShare_time()
	{
		return share_time;
	}

	public void setShare_time(String share_time)
	{
		this.share_time = share_time;
	}

	public String getEvaluate_time()
	{
		return evaluate_time;
	}

	public void setEvaluate_time(String evaluate_time)
	{
		this.evaluate_time = evaluate_time;
	}

	public String getCompare_time()
	{
		return compare_time;
	}

	public void setCompare_time(String compare_time)
	{
		this.compare_time = compare_time;
	}

	public String getLeave_time()
	{
		return leave_time;
	}

	public void setLeave_time(String leave_time)
	{
		this.leave_time = leave_time;
	}

	public String getFinish_time()
	{
		return finish_time;
	}

	public void setFinish_time(String finish_time)
	{
		this.finish_time = finish_time;
	}

}
