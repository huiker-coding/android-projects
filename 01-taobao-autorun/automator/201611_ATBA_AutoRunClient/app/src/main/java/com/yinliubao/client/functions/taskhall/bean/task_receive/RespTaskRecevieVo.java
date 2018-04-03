package com.yinliubao.client.functions.taskhall.bean.task_receive;

import java.io.Serializable;
import java.util.List;

import com.yinliubao.client.functions.taskhall.bean.task_receive.RespTaskRecevieVo.TaskReceiveBody;
import com.yinliubao.client.functions_common.bean.CommonResponseVo;

/*********************************
 * 任务获取返回结果 ******************************************* { "body_res": "0",
 * "order_bn": "订单号", "task_id": "任务号", "goods_search_key": "搜索关键词",
 * "goods_title": "短标题", "goods_name": "宝贝名称", "shop_name": "店铺名称",
 * "shop_seller": "店铺旺旺", "shop_type": "店铺类型", "flow_type": "流量类型", "tao_token":
 * "淘宝口令", "scan_time": "浏览时长", "scan_depth": "浏览深度,30%", "deliver_placels":
 * "发货地,重庆", "extra_filterls": *"额外条件,全球购", "price_max": "最高价,23", "price_min":
 * "最低价,12", "sort_type": *"排序类型", "extra_actionls": "附加动作,[加入购物车]", "obligate":
 * "预留字段,json=[{},{}]" }
 *************************************************************************************************/
public class RespTaskRecevieVo extends CommonResponseVo<TaskReceiveBody> implements Serializable
{

	/**  */ 
	private static final long serialVersionUID = -1129551645106447191L;

	public static class TaskReceiveBody
	{
		/** 订单ID */
		private String order_id;

		/** 订单号 */
		private String order_bn;

		/** 任务号(一个订单下可能有多个任务号) */
		private String task_id;

		/** 搜索关键字 */
		private String goods_search_key;

		/** 短标题 */
		private String goods_title;

		/** 宝贝名称 */
		private String goods_name;

		/** 店铺名称 */
		private String shop_name;

		/** 店铺旺旺 */
		private String shop_seller;

		/** 店铺类型 */
		private String shop_type;

		/** 流量类型 */
		private String flow_type;

		/** 淘口令 */
		private String tao_token;

		/** 浏览时长 */
		private String scan_time;

		/** 浏览深度 */
		private String scan_depth;

		/** 发货地 */
		private String deliver_placels;

		/** 额外条件 */
		private List<String> extra_filterls;

		/** 最高价 */
		private String price_max;

		/** 最低价 */
		private String price_min;

		/** 排序类型 */
		private String sort_type;

		/** 附加浏览动作 */
		private List<Integer> extra_actionls;

		/** 预留字段 */
		private String obligate;

		/** 用户原输入法 */
		private String input_method;

		public String getOrder_bn()
		{
			return order_bn;
		}

		public void setOrder_bn(String order_bn)
		{
			this.order_bn = order_bn;
		}

		public String getTask_id()
		{
			return task_id;
		}

		public void setTask_id(String task_id)
		{
			this.task_id = task_id;
		}

		public String getGoods_search_key()
		{
			return goods_search_key;
		}

		public void setGoods_search_key(String goods_search_key)
		{
			this.goods_search_key = goods_search_key;
		}

		public String getGoods_title()
		{
			return goods_title;
		}

		public void setGoods_title(String goods_title)
		{
			this.goods_title = goods_title;
		}

		public String getGoods_name()
		{
			return goods_name;
		}

		public void setGoods_name(String goods_name)
		{
			this.goods_name = goods_name;
		}

		public String getShop_name()
		{
			return shop_name;
		}

		public void setShop_name(String shop_name)
		{
			this.shop_name = shop_name;
		}

		public String getShop_seller()
		{
			return shop_seller;
		}

		public void setShop_seller(String shop_seller)
		{
			this.shop_seller = shop_seller;
		}

		public String getShop_type()
		{
			return shop_type;
		}

		public void setShop_type(String shop_type)
		{
			this.shop_type = shop_type;
		}

		public String getFlow_type()
		{
			return flow_type;
		}

		public void setFlow_type(String flow_type)
		{
			this.flow_type = flow_type;
		}

		public String getTao_token()
		{
			return tao_token;
		}

		public void setTao_token(String tao_token)
		{
			this.tao_token = tao_token;
		}

		public String getScan_time()
		{
			return scan_time;
		}

		public void setScan_time(String scan_time)
		{
			this.scan_time = scan_time;
		}

		public String getScan_depth()
		{
			return scan_depth;
		}

		public void setScan_depth(String scan_depth)
		{
			this.scan_depth = scan_depth;
		}

		public String getDeliver_placels()
		{
			return deliver_placels;
		}

		public void setDeliver_placels(String deliver_placels)
		{
			this.deliver_placels = deliver_placels;
		}

		public List<String> getExtra_filterls()
		{
			return extra_filterls;
		}

		public void setExtra_filterls(List<String> extra_filterls)
		{
			this.extra_filterls = extra_filterls;
		}

		public String getPrice_max()
		{
			return price_max;
		}

		public void setPrice_max(String price_max)
		{
			this.price_max = price_max;
		}

		public String getPrice_min()
		{
			return price_min;
		}

		public void setPrice_min(String price_min)
		{
			this.price_min = price_min;
		}

		public String getSort_type()
		{
			return sort_type;
		}

		public void setSort_type(String sort_type)
		{
			this.sort_type = sort_type;
		}

		public List<Integer> getExtra_actionls()
		{
			return extra_actionls;
		}

		public void setExtra_actionls(List<Integer> extra_actionls)
		{
			this.extra_actionls = extra_actionls;
		}

		public String getObligate()
		{
			return obligate;
		}

		public void setObligate(String obligate)
		{
			this.obligate = obligate;
		}

		public String getInput_method()
		{
			return input_method;
		}

		public void setInput_method(String input_method)
		{
			this.input_method = input_method;
		}

		public String getOrder_id()
		{
			return order_id;
		}

		public void setOrder_id(String order_id)
		{
			this.order_id = order_id;
		}
	}

}
