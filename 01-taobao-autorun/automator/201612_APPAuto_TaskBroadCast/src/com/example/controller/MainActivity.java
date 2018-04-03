package com.example.controller;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.example.controller.io.BaseSearchVo;
import com.example.controller.io.ConditionFilterVo;
import com.example.controller.io.ExtraActionVo;
import com.example.controller.io.SysParameterVo;
import com.example.controller.io.TaskParameter;
import com.example.controller.settings.Configs;
import com.example.controller.util.Base64Helper;
import com.example.controller.util.GsonHelper;

public class MainActivity extends Activity
{
	private static final String[] TMALL_FLAG =
	{ "否", "是" };

	@InjectView(R.id.et_search_key)
	EditText et_search_key;

	@InjectView(R.id.et_goods_tilte)
	EditText et_goods_tilte;

	@InjectView(R.id.sp_is_tmall)
	Spinner sp_is_tmall;

	@InjectView(R.id.cb_shop_red)
	CheckBox cb_shop_red;

	@InjectView(R.id.cb_free_ship)
	CheckBox cb_free_ship;

	@InjectView(R.id.cb_huabei)
	CheckBox cb_huabei;

	@InjectView(R.id.cb_global_buy)
	CheckBox cb_global_buy;

	@InjectView(R.id.cb_consumer_ensure)
	CheckBox cb_consumer_ensure;

	@InjectView(R.id.cb_tmall_global)
	CheckBox cb_tmall_global;

	@InjectView(R.id.cb_gold_coin)
	CheckBox cb_gold_coin;

	@InjectView(R.id.cb_sevenday_return)
	CheckBox cb_sevenday_return;

	@InjectView(R.id.cb_cod)
	CheckBox cb_cod;

	@InjectView(R.id.cb_general_sort)
	CheckBox cb_general_sort;

	@InjectView(R.id.et_min_price)
	EditText et_min_price;

	@InjectView(R.id.et_max_price)
	EditText et_max_price;

	@InjectView(R.id.et_deliver_place)
	EditText et_deliver_place;

	@InjectView(R.id.cb_favorite)
	CheckBox cb_favorite;

	@InjectView(R.id.cb_at_shops)
	CheckBox cb_at_shops;

	@InjectView(R.id.cb_scan_prd_prameter)
	CheckBox cb_scan_prd_prameter;

	@InjectView(R.id.cb_enter_shop)
	CheckBox cb_enter_shop;

	@InjectView(R.id.cb_scan_comment)
	CheckBox cb_scan_comment;

	@InjectView(R.id.et_user_counsel)
	EditText et_user_counsel;

	@InjectView(R.id.ll_not_tmall)
	LinearLayout ll_not_tmall;

	@InjectView(R.id.ll_is_tmall)
	LinearLayout ll_is_tmall;

	private TaskParameter taskParameter;
	private String taskStr;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		initData();
		setListener();
	}

	private void initData()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, TMALL_FLAG);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_is_tmall.setAdapter(adapter);
	}

	private void setListener()
	{
		sp_is_tmall.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				switch (position)
				{
				case 0:
					ll_not_tmall.setVisibility(View.VISIBLE);
					ll_is_tmall.setVisibility(View.GONE);
					break;
				case 1:
					ll_not_tmall.setVisibility(View.GONE);
					ll_is_tmall.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO 自动生成的方法存根

			}
		});
	}

	/** 点击发布任务将任务参数推送至自动化设备中 */
	public void doConfirm(View view) throws Exception
	{

		if (!validationHandler())
		{
			return;
		}
		taskParameter = getTaskParaData();

		String parameterStr = GsonHelper.objectToJson(taskParameter);
		taskStr = Base64Helper.encode(parameterStr);

		// 推动过程必须在子线程中执行
		new Thread()
		{
			@Override
			public void run()
			{
				PushKeyPair pair = new PushKeyPair(Configs.API_KEY, Configs.SECRET_KEY);
				BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);
				pushClient.setChannelLogHandler(new YunLogHandler()
				{
					public void onHandle(YunLogEvent event)
					{
						System.out.println(event.getMessage());
					}
				});

				try
				{
					PushMsgToAllRequest request = new PushMsgToAllRequest().addMsgExpires(new Integer(3600)).addMessageType(0).addMessage(taskStr).addDeviceType(3);
					PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
					Toast.makeText(MainActivity.this, "任务推送成功！", Toast.LENGTH_LONG).show();
					System.out.println("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime() + ",timerId: " + response.getTimerId());// Http请求结果解析打印
				} catch (PushClientException e)
				{
					if (BaiduPushConstants.ERROROPTTYPE)
					{
						Toast.makeText(MainActivity.this, "任务推送失败！", Toast.LENGTH_SHORT).show();
					} else
					{
						e.printStackTrace();
					}
				} catch (PushServerException e)
				{
					if (BaiduPushConstants.ERROROPTTYPE)
					{
						Toast.makeText(MainActivity.this, "任务推送失败！", Toast.LENGTH_SHORT).show();
					} else
					{
						System.out.println(String.format("requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
					}
				}

				super.run();
			}
		}.start();

	}

	/** 拼接用户填写的任务发布参数 */
	private TaskParameter getTaskParaData()
	{
		TaskParameter parameter = new TaskParameter();

		// 设置基础搜索参数
		BaseSearchVo baseSearchVo = new BaseSearchVo();
		baseSearchVo.setSearchKey(et_search_key.getText().toString());
		baseSearchVo.setGoodsTitle(et_goods_tilte.getText().toString());

		parameter.setBaseSearchVo(baseSearchVo);

		// 设置筛选条件
		ConditionFilterVo conditionFilterVo = new ConditionFilterVo();
		conditionFilterVo.setIsTmall(1 == sp_is_tmall.getSelectedItemPosition());
		conditionFilterVo.setIsShopRed(cb_shop_red.isChecked());
		conditionFilterVo.setIsFreeShip(cb_free_ship.isChecked());
		conditionFilterVo.setIsHuaBei(cb_huabei.isChecked());
		conditionFilterVo.setIsGlobalBuy(cb_global_buy.isChecked());
		conditionFilterVo.setIsConsumerEnsure(cb_consumer_ensure.isChecked());
		conditionFilterVo.setIsTmallGlobal(cb_tmall_global.isChecked());
		conditionFilterVo.setIsGoldCoin(cb_gold_coin.isChecked());
		conditionFilterVo.setIsSevenDayReturn(cb_sevenday_return.isChecked());
		conditionFilterVo.setIsCOD(cb_cod.isChecked());
		conditionFilterVo.setIsGeneralSort(cb_general_sort.isChecked());
		conditionFilterVo.setMinPrice(!"".equals(et_min_price.getText().toString()) ? new BigDecimal(et_min_price.getText().toString()) : null);
		conditionFilterVo.setMaxPrice(!"".equals(et_max_price.getText().toString()) ? new BigDecimal(et_max_price.getText().toString()) : null);
		conditionFilterVo.setDeliverPlace(!"".equals(et_deliver_place.getText().toString()) ? et_deliver_place.getText().toString() : null);
		conditionFilterVo.setIsSetFilter(isSetFiler());
		parameter.setConditionFilterVo(conditionFilterVo);

		// 店铺附加操作
		ExtraActionVo extraActionVo = new ExtraActionVo();
		extraActionVo.setIsFavorite(cb_favorite.isChecked());
		extraActionVo.setIsAtShops(cb_at_shops.isChecked());
		extraActionVo.setIsScanPrdPrameter(cb_scan_prd_prameter.isChecked());
		extraActionVo.setIsEnterShop(cb_enter_shop.isChecked());
		extraActionVo.setIsScanComment(cb_scan_comment.isChecked());
		extraActionVo.setUserCounselStr(!"".equals(et_user_counsel.getText().toString()) ? et_user_counsel.getText().toString() : null);
		parameter.setExtraActionVo(extraActionVo);

		// 基本参数(当前输入法)
		SysParameterVo sysParameterVo = new SysParameterVo();
		String inputMethodStr = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
		sysParameterVo.setInputMethodStr(inputMethodStr);
		parameter.setSysParameterVo(sysParameterVo);

		return parameter;

	}

	/** 判断是否有商品筛选条件 */
	private boolean isSetFiler()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(cb_shop_red.isChecked());
		sb.append(",");
		sb.append(cb_free_ship.isChecked());
		sb.append(",");
		sb.append(cb_huabei.isChecked());
		sb.append(",");
		sb.append(cb_global_buy.isChecked());
		sb.append(",");
		sb.append(cb_consumer_ensure.isChecked());
		sb.append(",");
		sb.append(cb_tmall_global.isChecked());
		sb.append(",");
		sb.append(cb_gold_coin.isChecked());
		sb.append(",");
		sb.append(cb_sevenday_return.isChecked());
		sb.append(",");
		sb.append(cb_cod.isChecked());
		sb.append(",");
		sb.append(cb_general_sort.isChecked());
		sb.append(",");
		sb.append(et_min_price.getText().toString());
		sb.append(et_max_price.getText().toString());
		sb.append(et_deliver_place.getText().toString());

		if (sb.toString().equals("false,false,false,false,false,false,false,false,false,false,"))
		{
			return false;
		}
		return true;
	}

	/** 验证必输项是否为空 */
	private boolean validationHandler()
	{
		if ("".equals(et_search_key.getText().toString()))
		{
			et_search_key.requestFocus();
			Toast.makeText(MainActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
			return false;
		}
		if ("".equals(et_goods_tilte.getText().toString()))
		{
			et_goods_tilte.requestFocus();
			Toast.makeText(MainActivity.this, "请输入宝贝标题", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
