package com.example.automator.base;

import java.util.List;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.example.automator.io.ConditionFilterVo;
import com.example.automator.io.ExtraActionVo;

/**
 * This case is used for defining basic logic TestCase which is usually used.
 * 
 * @author gray.z
 */
public class YundaTestCase extends BaseTestCase
{
	// 用于存储商品标题信息，防止搜索重复
	protected List<String> matTitleLs;

	/**
	 * Function：用于标识根据哪种方式查找 GOODS_NAME 为根据商品名称方式查找； TIAN_MAO
	 * 为在天猫商城上查找；STORE_NAME为查找商铺
	 */
	protected enum SearchFlag
	{
		GOODS_NAME, TIAN_MAO, STORE_NAME
	}

	/** Function：四种筛选方式：综合排序、上市时间、价格递减、价格递增 */
	protected enum SortType
	{
		COMPLEX_ORDER, ONSALE_DATE, PRICE_DEC, PRICE_INC
	}

	/** Function：在APP主页面点击搜索框进入商品搜索界面 */
	protected void gotoSearchPage(UiDevice device) throws UiObjectNotFoundException
	{
		sleepLong();
		goBackToPage(device, selBuildByResId("com.taobao.taobao:id/home_searchedit", null, null), "com.taobao.taobao"); // 回到主页面

		sleepShort();
		UiObject searchEdt = getWidget(selBuildByResId("com.taobao.taobao:id/home_searchedit", null, null));
		searchEdt.clickAndWaitForNewWindow();
	}

	/**
	 * Function： 1.搜索界面点击筛选条件(宝贝、天猫、商铺); 2.录入搜索条件; 3.点击搜索按钮
	 * 
	 * @param
	 * @return void
	 * @date 2016年10月8日下午5:35:56
	 */
	protected void searchByCondition(boolean isTmall, String condition) throws UiObjectNotFoundException
	{
		sleepShort();
		UiObject conditionBtn = getWidget(selBuildByResId("com.taobao.taobao:id/searchchoice_text", null, null));

		// 判断天猫或宝贝筛选操作是否在在本界面进行
		if (getRandomRate() > 80)
		{
			if (isTmall)
			{
				if (!conditionBtn.getText().equals("天猫"))
				{
					conditionBtn.click();
					UiObject tmall_btn = getWidget(selBuildByResId("com.taobao.taobao:id/tmall_btn", null, null));
					tmall_btn.click();
					sleepShort();
				}
			} else
			{
				if (!conditionBtn.getText().equals("宝贝"))
				{
					conditionBtn.click();
					UiObject goods_btn = getWidget(selBuildByResId("com.taobao.taobao:id/goods_btn", null, null));
					goods_btn.click();
					sleepShort();
				}
			}
		}

		// 清空搜索文本
		UiObject deleteBtn = getWidget(selBuildByResId("com.taobao.taobao:id/edit_del_btn", null, null));
		if (deleteBtn.exists())
		{
			sleepShort();
			deleteBtn.click();
		}

		// 录入搜索关键字
		UiObject searchEdt = getWidget(selBuildByResId("com.taobao.taobao:id/searchEdit", null, null));
		searchEdt.setText(getUtfImeStr(condition));
		sleepShort();

		// 点击确定按钮进行搜索
		UiObject confirmBtn = getWidget(selBuildByResId("com.taobao.taobao:id/searchbtn", "搜索", true));
		confirmBtn.clickAndWaitForNewWindow();
	}

	/**
	 * Function： 进行商品信息筛选
	 * 
	 * @param
	 * @return void
	 * @date 2016年10月8日下午5:42:04
	 */
	protected void scannedFilter(UiScrollable scrollHandler, ConditionFilterVo vo) throws UiObjectNotFoundException
	{
		sleepLong();

		// 设置浏览方式(列表模式OR大图模式)
		UiObject styleBtn = getWidget(selBuildByResId("com.taobao.taobao:id/styleBtn", null, null));
		if ("列表模式".equals(styleBtn.getContentDescription()) && getRandomRate() > 60)
		{
			styleBtn.clickAndWaitForNewWindow();
			sleepLong();
		}

		// 若没有设置筛选条件则跳过筛选环节
		if (!vo.getIsSetFilter())
		{
			return;
		}

		// 判断是否为天猫商铺
		if (vo.getIsTmall())
		{
			UiObject desBtn = getParentWidget(selBuildByClassName("android.support.v7.app.ActionBar$a", null, null, null), selBuildByResId("com.taobao.taobao:id/tab_text", "天猫", true));
			if (!desBtn.isSelected())
			{
				desBtn.click();
				sleepLong();
			}
		}

		// 打开筛选界面
		UiObject filterBtn = getWidget(selBuildByResId("com.taobao.taobao:id/filterBtn", null, null));
		filterBtn.click();
		sleepLong();

		// 设置筛选条件
		if (vo.getIsShopRed())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "店铺红包", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}

		if (vo.getIsFreeShip())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "包邮", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}

		if (vo.getIsHuaBei())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "花呗分期", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}
		if (vo.getIsGlobalBuy())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "全球购", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}
		if (vo.getIsConsumerEnsure())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "消费者保障", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}
		if (vo.getIsTmallGlobal())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "天猫国际", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}
		if (vo.getIsGoldCoin())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "淘金币抵钱", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}
		if (vo.getIsSevenDayReturn())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "7+天内退货", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}
		if (vo.getIsCOD())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "货到付款", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}
		if (vo.getIsGeneralSort())
		{
			UiObject desBtn = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", "通用排序", true));
			if (desBtn.exists())
			{
				desBtn.click();
				sleepShort();
			}
		}

		if (null != vo.getMinPrice())
		{
			UiObject desEdt = getWidget(selBuildByResId("com.taobao.taobao:id/tbsearch_filter_edit_min_price", "最低价", true));
			desEdt.setText(getUtfImeStr((vo.getMinPrice().toString())));
			sleepShort();
		}
		if (null != vo.getMaxPrice())
		{
			UiObject desEdt = getWidget(selBuildByResId("com.taobao.taobao:id/tbsearch_filter_edit_max_price", "最高价", true));
			desEdt.setText(getUtfImeStr(vo.getMaxPrice().toString()));
			sleepShort();
		}

		if (null != vo.getDeliverPlace())
		{
			UiObject detailBtn1 = scrollForWidget(scrollHandler, selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", vo.getDeliverPlace(), true));
			if (detailBtn1.exists())
			{
				detailBtn1.click();
				sleepShort();
			} else
			{

				UiObject desBtn = scrollForWidget(scrollHandler, selBuildByResId("com.taobao.taobao:id/tbsearch_filter_location_title_container", null, null));
				desBtn.click();
				sleepShort();

				UiObject detailBtn2 = scrollForWidget(scrollHandler, selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/filter_list_container", vo.getDeliverPlace(), true));
				detailBtn2.click();
				sleepShort();
			}
		}

		// 设置完筛选条件后点击完成
		UiObject confirmBtn = getWidget(selBuildByResId("com.taobao.taobao:id/filter_finish", "完成", true));
		confirmBtn.click();
	}

	/**
	 * Function： 商品按销量优先的顺序排序
	 * 
	 * @param
	 * @return void
	 * @date 2016年10月8日下午5:42:04
	 */
	protected void orderBySale() throws UiObjectNotFoundException
	{
		UiSelector sortTvSelector = selBuildByResId("com.taobao.taobao:id/sortShowText", "销量优先", true);
		UiObject sortBtn = getParentWidget(selBuildByClassName("android.widget.LinearLayout", null, null, null), sortTvSelector);
		sortBtn.click();
		sleepShort();
	}

	/**
	 * Function： 商品按：综合排序、上市时间、价格由高到低、价格由低到高 排序 (由于该下拉列表无控件标识，所以下面采用位置触控的方式模拟点击)
	 * 
	 * @param
	 * @return void
	 * @date 2016年10月8日下午5:42:04
	 */
	protected void orderByMixed(UiDevice device, SortType sortType) throws UiObjectNotFoundException
	{
		UiSelector sortTvSelector = selBuildByResId("com.taobao.taobao:id/sortShowText", "综合排序", true);
		UiObject sortBtn = getParentWidget(selBuildByClassName("android.widget.LinearLayout", null, null, null), sortTvSelector);
		sortBtn.click();
		sleepShort();

		switch (sortType)
		{
		case COMPLEX_ORDER: // 依据综合排序条件排序
			device.click(device.getDisplayWidth() / 2, device.getDisplayHeight() / 3 - 100);
			break;

		case ONSALE_DATE: // 依据上市时间排序
			device.click(device.getDisplayWidth() / 2, device.getDisplayHeight() / 3);
			break;
		case PRICE_DEC: // 依据商品价格降序排序
			device.click(device.getDisplayWidth() / 2, device.getDisplayHeight() / 3 + 100);
			break;
		case PRICE_INC: // 依据商品价格升序排序
			device.click(device.getDisplayWidth() / 2, device.getDisplayHeight() / 3 + 200);
			break;
		default:
			break;
		}

		sleepShort();
	}

	/**
	 * Function： 判断当前商铺中的商品是否已下架
	 * 
	 * @param
	 * @return boolean
	 * @date 2016年10月9日下午1:51:03
	 */
	protected boolean isGoodsExist() throws UiObjectNotFoundException
	{

		UiObject isExistTv = getWidget(selBuildByClassName("android.widget.TextView", "com.taobao.taobao:id/action_bar", "范德萨", true));
		if (isExistTv.exists())
		{
			return false;
		}
		return true;

	}

	/**
	 * Function： 在找到目标商铺之前先随机浏览1-3家商铺
	 * 
	 * @param
	 * @return boolean
	 * @throws UiObjectNotFoundException
	 * @date 2016年12月6日下午1:51:03
	 */
	protected void scanBefGoods(UiDevice device, UiScrollable scrollHandler, String goodsTitle) throws UiObjectNotFoundException
	{
		for (int i = 0; i < 3; i++)
		{
			sleepLong();
			// 若在向下翻页的过程中存在目标商铺则结束此预浏览进入寻找目标商铺流程
			if (isTargetGoodsExist(scrollHandler, getRandomNum(1, 4), goodsTitle))
			{
				return;
			}

			// 设置浏览随机概率
			if (getRandomRate() > 20)
			{
				UiObject desBtn = getWidget(selBuildByResId("com.taobao.taobao:id/auction_layout", null, null).instance(getRandomNum(1, 4)));
				desBtn.clickAndWaitForNewWindow();
				sleepLong();

				scanOtherGoodsDetail(device, scrollHandler);
				device.pressBack();
				sleepLong();
			}
		}

	}

	/** 判断在向下翻页的过程中是否存在当前搜索的商品 */
	private boolean isTargetGoodsExist(UiScrollable scrollHandler, int maxStep, String goodsTitle) throws UiObjectNotFoundException
	{
		int scrollCount = 0;
		for (int x = 0; x < maxStep; x++)
		{
			// 若在向下翻页的过程中存在目标商铺则结束此预浏览
			UiObject desTv = getWidget(selBuildByResId("com.taobao.taobao:id/title", goodsTitle, false));
			if (desTv.exists())
			{
				return true;
			}
			sleepLong();
			int randomNum = getRandomRate();
			if (randomNum < 5)
			{
				sleepLong();
			} else if (randomNum > 95)
			{
				scrollHandler.scrollBackward(getScrollSteps());

			} else
			{
				boolean scrolled = scrollHandler.scrollForward();
				sleepLong();
				if (!scrolled)
				{
					scrollCount++;
					if (scrollCount == 3)
					{
						return false;
					} else
					{
						continue;
					}
				} else
				{
					scrollCount = 0;
				}
			}
		}
		return false;
	}

	/**
	 * Function： 进入目标商铺进行详细浏览操作
	 * 
	 * @param
	 * @return boolean
	 * @date 2016年12月6日下午1:51:03
	 */
	protected void searchForGoods(UiDevice device, UiScrollable scrollHandler, String goodsTitle, ExtraActionVo vo) throws UiObjectNotFoundException
	{
		sleepLong();
		boolean isParameterScaned = false;
		UiObject desTv = scrollForWidget(scrollHandler, selBuildByResId("com.taobao.taobao:id/title", goodsTitle, false)); // 滑动屏幕查找目标商铺
		UiObject desBtn = getParentWidget(selBuildByResId("com.taobao.taobao:id/auction_layout", null, null), desTv.getSelector());
		desBtn.clickAndWaitForNewWindow();
		sleepLong();

		// 滑动浏览图片
		if (getRandomRate() > 40)
		{
			UiObject scrollPic = getWidget(selBuildByResId("com.taobao.taobao:id/img_gallery", null, null));
			int scanRandomNum = getRandomNum(3, 5); // 设置图片滑动为3-5次
			for (int i = 0; i < scanRandomNum; i++)
			{
				if (!scrollPic.exists())
				{
					break;
				}
				scrollPic.swipeLeft(getSwipeSteps());
				sleepShort();
				if (getRandomRate() > 90)
				{
					scrollPic.swipeRight(getSwipeSteps());
					sleepShort();
				}
			}

			if (!scrollPic.exists())
			{
				ScanParameter(scrollHandler, vo.getIsScanPrdPrameter(), getScrollTimes());
				device.pressBack();
				sleepLong();
				isParameterScaned = true;
			}
		}

		// 查看买家评论
		if (vo.getIsScanComment())
		{
			UiObject commentDetailBtn = scrollForWidget(scrollHandler, selBuildByResId("com.taobao.taobao:id/detail_main_sys_button", "查看全部评价", true));
			commentDetailBtn.clickAndWaitForNewWindow();
			sleepShort();
			scrollToBottom(scrollHandler, getRandomNum(6, 10));
			device.pressBack();
			sleepLong();
		}

		if (!isParameterScaned)
		{
			ScanParameter(scrollHandler, vo.getIsScanPrdPrameter(), getScrollTimes());
			UiObject goTopBtn = getWidget(selBuildByResId("com.taobao.taobao:id/btnGoTop", null, null));
			goTopBtn.click();
			sleepLong();
		}

		// 将该宝贝加入收藏
		if (vo.getIsFavorite())
		{
			UiObject isFavoriteTv = getWidget(selBuildByResId("com.taobao.taobao:id/fav_title", "收藏", true));
			if (isFavoriteTv.exists())
			{
				UiObject isFavoriteBtn = getParentWidget(selBuildByResId("com.taobao.taobao:id/fav_container", null, null), isFavoriteTv.getSelector());
				isFavoriteBtn.click();
				sleepShort();
				device.click(400, 800);
				sleepLong();
			}
		}

		// 进入商铺并进行关注商铺操作
		if (vo.getIsEnterShop())
		{
			UiObject shopTv = getWidget(selBuildByResId("com.taobao.taobao:id/tv_title", "店铺", true));
			UiObject shopBtn = getParentWidget(selBuildByResId("com.taobao.taobao:id/ll_icon", null, null), shopTv.getSelector());
			shopBtn.clickAndWaitForNewWindow();
			sleepShort();
			scrollToBottom(scrollHandler, getRandomNum(5, 10));

			if (vo.getIsAtShops())
			{
				scrollToTop(scrollHandler);
				UiObject atShopBtn = getWidget(selBuildByResId("com.taobao.taobao:id/fl_follow", null, null));
				atShopBtn.click();
				sleepLong();
				scrollToBottom(scrollHandler, getRandomNum(2, 6));
			}
			device.pressBack();
			sleepLong();

		}

		// 模拟跟客服聊天
		if (null != vo.getUserCounselStr())
		{
			UiObject selerTv = getWidget(selBuildByResId("com.taobao.taobao:id/tv_title", "客服", true));
			UiObject selerBtn = getParentWidget(selBuildByResId("com.taobao.taobao:id/ll_icon", null, null), selerTv.getSelector());
			selerBtn.clickAndWaitForNewWindow();
			sleepShort();

			UiObject userCounselBtn = scrollForWidget(scrollHandler, selBuildByResId("com.taobao.taobao:id/send_item", "发送宝贝链接", true));
			userCounselBtn.click();
			sleepShort();

			UiObject talkEt = getWidget(selBuildByResId("com.taobao.taobao:id/et_sendmessage", null, null));
			talkEt.setText(getUtfImeStr(vo.getUserCounselStr()));

			// 设置添加emoji表情的概率
			if (getRandomRate() > 60)
			{
				sleepShort();
				UiObject emojiBtn = getWidget(selBuildByResId("com.taobao.taobao:id/chatting_biaoqing_btn", null, null));
				emojiBtn.click();
				sleepShort();

				String emojiStr[] =
				{ "微笑", "害羞", "吐舌头", "爱慕", "加油", "亲亲", "握手" };
				UiObject selectEmojiBtn = getWidget(selBuildByDesc("android.widget.TextView", emojiStr[getRandomNum(0, 6)]));
				selectEmojiBtn.click();
				sleepShort();
				device.pressBack();
			}
			sleepLong();

			UiObject sendBtn = getWidget(selBuildByResId("com.taobao.taobao:id/btn_send", "发送", true));
			sendBtn.click();
			sleepLong();

			device.pressBack();
		}

	}

	// 查看图文参数
	private void ScanParameter(UiScrollable scrollHandler, boolean isScanPrdPrameter, int scrollStep) throws UiObjectNotFoundException
	{
		if (isScanPrdPrameter)
		{
			scrollToBottom(scrollHandler, scrollStep);
			UiObject parameterBtn = getWidget(selBuildByClassName("android.widget.TextView", null, "产品参数", true));
			if (parameterBtn.exists())
			{
				parameterBtn.click();
			} else
			{
				UiObject parameterBtn2 = getWidget(selBuildByResId("com.taobao.taobao:id/detail_fulldesc_btn_spec", "产品参数", true));
				parameterBtn2.click();
			}
			sleepShort();
		}
	}

	/**
	 * Function： 进入其它商铺进行浏览操作
	 * 
	 * @param
	 * @return void
	 * @date 2016年12月6日下午1:51:03
	 */
	protected void scanOtherGoods(UiDevice device, UiScrollable scrollHandler) throws UiObjectNotFoundException
	{
		goBackToPage(device, selBuildByResId("com.taobao.taobao:id/auction_layout", null, null), "com.taobao.taobao");

		for (int i = 0; i < 3; i++)
		{
			sleepLong();
			scrollToBottom(scrollHandler, getRandomNum(1, 4));
			if (getRandomRate() > 20)
			{
				UiObject desBtn = getWidget(selBuildByResId("com.taobao.taobao:id/auction_layout", null, null).instance(getRandomNum(1, 4)));
				desBtn.clickAndWaitForNewWindow();
				sleepLong();

				scanOtherGoodsDetail(device, scrollHandler);
				device.pressBack();
				sleepLong();
			}
		}
	}

	
	protected void scanOtherGoodsDetail(UiDevice device, UiScrollable scrollHandler) throws UiObjectNotFoundException
	{
		boolean isParameterScaned = false;

		if (getRandomRate() > 80)
		{
			UiObject scrollPic = getWidget(selBuildByResId("com.taobao.taobao:id/img_gallery", null, null));
			int scanRandomNum = getRandomNum(3, 5);
			for (int i = 0; i < scanRandomNum; i++)
			{
				if (!scrollPic.exists())
				{
					break;
				}
				scrollPic.swipeLeft(getSwipeSteps());
				sleepShort();
				if (getRandomRate() > 90)
				{
					scrollPic.swipeRight(getSwipeSteps());
					sleepShort();
				}
			}

			if (!scrollPic.exists())
			{
				ScanParameter(scrollHandler, true, getRandomNum(8, 20));
				device.pressBack();
				sleepLong();
				isParameterScaned = true;
			}
		}

		// 查看买家评论
		if (getRandomRate() > 60)
		{
			UiObject commentDetailBtn = scrollForWidget(scrollHandler, selBuildByResId("com.taobao.taobao:id/detail_main_sys_button", "查看全部评价", true));
			commentDetailBtn.clickAndWaitForNewWindow();
			sleepShort();
			scrollToBottom(scrollHandler, getRandomNum(3, 6));
			device.pressBack();
			sleepLong();
		}

		if (getRandomRate() > 80 && !isParameterScaned)
		{
			ScanParameter(scrollHandler, true, getRandomNum(8, 16));
			UiObject goTopBtn = getWidget(selBuildByResId("com.taobao.taobao:id/btnGoTop", null, null));
			goTopBtn.click();
			sleepLong();
		}

		// 将该宝贝加入收藏
		if (getRandomRate() > 90)
		{
			UiObject isFavoriteTv = getWidget(selBuildByResId("com.taobao.taobao:id/fav_title", "收藏", true));
			if (isFavoriteTv.exists())
			{
				UiObject isFavoriteBtn = getParentWidget(selBuildByResId("com.taobao.taobao:id/fav_container", null, null), isFavoriteTv.getSelector());
				isFavoriteBtn.click();
				sleepShort();
				device.click(400, 800);
				sleepLong();
			}
		}

		// 进入商铺并进行关注商铺操作
		if (getRandomRate() > 80)
		{
			UiObject shopTv = getWidget(selBuildByResId("com.taobao.taobao:id/tv_title", "店铺", true));
			UiObject shopBtn = getParentWidget(selBuildByResId("com.taobao.taobao:id/ll_icon", null, null), shopTv.getSelector());
			shopBtn.clickAndWaitForNewWindow();
			sleepShort();
			scrollToBottom(scrollHandler, getRandomNum(5, 10));

			if (getRandomRate() > 90)
			{
				UiObject atShopBtn = scrollForWidget(scrollHandler, selBuildByResId("com.taobao.taobao:id/fl_follow", null, null));
				atShopBtn.click();
				sleepLong();
				scrollToBottom(scrollHandler, getRandomNum(2, 5));
			}
			device.pressBack();
			sleepLong();

		}

	}

	/**
	 * 
	 * @param
	 * @return void
	 */
	// protected void loopForInfo(UiDevice device, UiScrollable scrollHandler,
	// List<SkuDetailVo> choiceLs) throws UiObjectNotFoundException
	// {
	// loopforItem(scrollHandler);
	// if (isGoodsExist())
	// {
	// getBasicInfo(scrollHandler, choiceLs);
	// }
	// goBackToPage(device,
	// selBuildByResId("com.taobao.taobao:id/search_listview", null, null),
	// "com.taobao.taobao");
	// sleepShort();
	// scanNum++;
	//
	// if (scanNum <= Config.MAX_SCAN_NUM)
	// {
	// loopForInfo(device, scrollHandler, choiceLs);
	// } else
	// {
	// return;
	// }
	// }

	/**
	 * Function: 用于处理只点击未浏览过的商铺
	 * 
	 * @param
	 * @return void
	 * @date 2016年10月7日下午3:05:29
	 */
	// protected void loopforItem(UiScrollable scrollHandler) throws
	// UiObjectNotFoundException
	// {
	//
	// UiObject itemLayout =
	// getWidget(selBuildByResId("com.taobao.taobao:id/auction_layout", null,
	// null).instance(position));
	// UiObject itemTitle = itemLayout.getChild(new
	// UiSelector().resourceId("com.taobao.taobao:id/title"));
	// if (!itemTitle.exists())
	// {
	// scrollHandler.scrollForward(Config.SCROLL_STEPS);
	// sleepShort();
	// position = 1;
	// loopforItem(scrollHandler);
	// } else
	// {
	// if (!matTitleLs.contains(itemTitle.getText()))
	// {
	// matTitleLs.add(itemTitle.getText());
	// itemLayout.clickAndWaitForNewWindow();
	// position++;
	// } else
	// {
	// position++;
	// loopforItem(scrollHandler);
	// }
	// }
	// }

}
