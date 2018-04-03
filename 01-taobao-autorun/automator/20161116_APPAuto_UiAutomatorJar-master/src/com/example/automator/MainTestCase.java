package com.example.automator;

import java.io.IOException;

import android.os.Bundle;
import android.util.Base64;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.example.automator.base.ExceptionCatch;
import com.example.automator.base.YundaTestCase;
import com.example.automator.io.BaseSearchVo;
import com.example.automator.io.ConditionFilterVo;
import com.example.automator.io.ExtraActionVo;
import com.example.automator.io.SysParameterVo;
import com.example.automator.io.TaskParameter;
import com.example.automator.utils.GsonHelper;

/**
 * @desc 自动化主程式
 * @author gray.z
 * @date
 */
public class MainTestCase extends YundaTestCase
{
	private UiDevice device;
	private UiScrollable scrollHandler;

	private SysParameterVo sysParameterVo;
	private BaseSearchVo baseSearchVo;
	private ConditionFilterVo conditionFilterVo;
	private ExtraActionVo extraActionVo;

	/** Function 自动化主入口 */
	public void testDemo() throws Exception
	{
		initData();

		// 装载自动化用例，并且捕获异常
		ExceptionCatch catchExceptionRunTest = new ExceptionCatch(sysParameterVo.getInputMethodStr())
		{
			@Override
			public void caseLoad() throws Exception
			{
				TestCaseBuilder();
			}
		};
		catchExceptionRunTest.runCase();
	}

	/** Function：初始化自动化基础参数 */
	private void initData() throws IOException
	{
		// LOG_PATH =Environment.getExternalStorageDirectory().getPath()
		// +Config.RELATIVE_LOG_PATH;
		// deleteUiAutoLog();

		device = getUiDevice();
		scrollHandler = new UiScrollable(new UiSelector().scrollable(true));
		initSearchData();
	}

	/** Function：此处接收由客户端发过来的请求数据 */
	private void initSearchData()
	{
		Bundle bundle = getParams();
		String str = bundle.getString("task");

		String jsonStr = new String(Base64.decode(str, 0));
		TaskParameter taskParameter = GsonHelper.jsonToObject(jsonStr, TaskParameter.class);

		sysParameterVo = taskParameter.getSysParameterVo();
		baseSearchVo = taskParameter.getBaseSearchVo();
		conditionFilterVo = taskParameter.getConditionFilterVo();
		extraActionVo = taskParameter.getExtraActionVo();

		System.out.println("TAOBAO_AUTO_LOG——" + jsonStr);
	}

	/** Function： 此处构建自动化用例流程 */
	private void TestCaseBuilder() throws Exception
	{
		// 自动化初始化过程
		autoRunInit();

		// Step1: 点击搜索框进入关键字录入界面
		gotoSearchPage(device);

		// Step2: 根据搜索关键字进行搜索操作
		searchByCondition(conditionFilterVo.getIsTmall(), baseSearchVo.getSearchKey());

		// Step3: 根据筛选条件进行筛选操作
		scannedFilter(scrollHandler, conditionFilterVo);

		// Step4: 在找到目标商铺之前先随机浏览1-3家商铺
		scanBefGoods(device, scrollHandler, baseSearchVo.getGoodsTitle());

		// Step5: 根据宝贝标题进入目标商铺，并进行指定操作
		searchForGoods(device, scrollHandler, baseSearchVo.getGoodsTitle(), extraActionVo);

		// Step6: 目标商铺浏览完成后再随机浏览1-3家商铺
		scanOtherGoods(device, scrollHandler);

		// 自动化执行完毕，资源释放
		autoRunFinish();
	}

	private void autoRunInit() throws Exception
	{
		device.wakeUp(); // 设备唤醒
		sleepShort();
		doExec("am start -c android.intent.category.LAUNCHER -a android.intent.action.MAIN -n com.taobao.taobao/com.taobao.tao.welcome.Welcome -f 0x10200000"); // 启动目标APP
		sleepShort();
		doExec("ime set com.yunda.input/.ImeService"); // 启动自动化内置输入法
	}

	private void autoRunFinish() throws IOException
	{
		goBackToPage(device, selBuildByResId("com.taobao.taobao:id/home_searchedit", null, null), "com.taobao.taobao"); // 回到主页面
		sleepShort();
		doExec("am force-stop com.taobao.taobao"); // 关闭淘宝APP
		sleepShort();
		doExec("ime set " + sysParameterVo.getInputMethodStr()); // 还原输入法
	}

}
