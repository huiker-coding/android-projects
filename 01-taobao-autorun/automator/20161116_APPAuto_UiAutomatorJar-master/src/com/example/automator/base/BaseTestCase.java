package com.example.automator.base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import android.annotation.SuppressLint;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.example.automator.settings.Config;
import com.example.automator.utils.Utf7ImeHelper;

/**
 * This case is used for defining basic utils
 * 
 * @author gray.z
 */
public class BaseTestCase extends UiAutomatorTestCase
{

	// 拼接得到日志文件的绝对路径
	// protected static String LOG_PATH = "/sdcard/DCIM/UiAutomatorLog.txt";

	/**
	 * Function：根据resourceId、包含字符串 查找控件——
	 * 若isFullMatch为null则表示只用resId匹配控件，isFullMatch为false表示包含匹配
	 * ，isFullMatch为true表示全字符匹配
	 * 
	 * @param
	 * @return UiSelector
	 * @date 2016年9月30日下午1:58:50
	 */
	protected UiSelector selBuildByResId(String resId, String text, Boolean isFullMatch)
	{

		if (null == isFullMatch)
		{
			return new UiSelector().resourceId(resId);
		} else
		{
			if (isFullMatch)
			{
				return new UiSelector().resourceId(resId).text(text);
			} else
			{
				return new UiSelector().resourceId(resId).textContains(text);
			}
		}

	}

	/**
	 * Function：根据className和描述字符串 查找控件
	 * 
	 * @param
	 * @return UiSelector
	 * @date 2016年9月30日下午1:58:50
	 */
	protected UiSelector selBuildByDesc(String className, String descStr)
	{
		return new UiSelector().className(className).description(descStr);

	}

	/**
	 * Function：根据className、包含字符串 查找控件——
	 * 若isFullMatch为null则表示只用resId匹配控件，isFullMatch为false表示包含匹配
	 * ，isFullMatch为true表示全字符匹配
	 * 
	 * @param
	 * @return UiSelector
	 * @date 2016年9月30日下午1:58:50
	 */
	protected UiSelector selBuildByClassName(String className, String parentResId, String text, Boolean isFullMatch) throws UiObjectNotFoundException
	{
		if (null == parentResId)
		{
			if (null == isFullMatch)
			{
				return new UiSelector().className(className);
			} else
			{
				if (isFullMatch)
				{
					return new UiSelector().className(className).text(text);
				} else
				{
					return new UiSelector().className(className).textContains(text);
				}
			}
		} else
		{
			UiObject parentUO = getWidget(selBuildByResId(parentResId, null, null));

			if (null == isFullMatch)
			{
				return new UiSelector().className(className);
			} else
			{
				if (isFullMatch)
				{
					return parentUO.getChild(new UiSelector().className(className).text(text)).getSelector();

				} else
				{
					return parentUO.getChild(new UiSelector().className(className).textContains(text)).getSelector();
				}
			}
		}
	}

	/** 获得上下滑屏随机步长 */
	protected int getScrollSteps()
	{
		return getRandomNum(Config.MIN_SCROLL_STEPS, Config.MAX_SCROLL_STEPS);
	}

	/** 获得左右滑屏随机步长 */
	protected int getSwipeSteps()
	{
		return getRandomNum(Config.MIN_SWIPE_STEPS, Config.MAX_SWIPE_STEPS);
	}

	/** 获得向下滑屏的随机次数 */
	protected int getScrollTimes()
	{
		return getRandomNum(Config.MIN_SCROLL_TIMES, Config.MAX_SCROLL_TIMES);
	}

	/** 产生0-100的随机数 */
	protected int getRandomRate()
	{
		return getRandomNum(1, 100);
	}

	/**
	 * Function：用于APP全局返回到指定界面(通过resourceId标识的控件)
	 * 
	 * @param
	 * @return void
	 * @date 2016年9月30日下午2:00:05
	 */
	protected void goBackToPage(UiDevice device, UiSelector uiSelector, String packageName)
	{
		UiObject uiWidget = getWidget(uiSelector);

		// 如果在当前APP内并且控件不存在则继续回退
		if (device.getCurrentPackageName().equals(packageName) && !uiWidget.exists())
		{
			device.pressBack();
			sleepShort();
			goBackToPage(device, uiSelector, packageName);
		}
	}

	/**
	 * Function 用于滚屏查找指定控件
	 * 
	 * @param
	 * @return UiObject
	 * @date 2016年9月30日下午1:56:47
	 */
	protected UiObject scrollForWidget(UiScrollable scrollHandler, UiSelector uiSelector) throws UiObjectNotFoundException
	{
		int scrollCount = 0;
		UiObject uiWidget = getWidget(uiSelector);

		if (uiWidget.exists())
		{
			return uiWidget;
		} else
		{
			scrollHandler.scrollToBeginning(Config.MAX_SEARCH_SWIPES);
			if (uiWidget.exists())
			{
				return uiWidget;
			}
			for (int x = 0; x < Config.MAX_SEARCH_SWIPES; x++)
			{
				sleepLong();
				int randomNum = getRandomNum(0, 100);
				if (randomNum < 5)
				{
					sleepLong();
				} else if (randomNum > 95)
				{
					scrollHandler.scrollBackward();
				} else
				{
					boolean scrolled = scrollHandler.scrollForward(getScrollSteps());
					sleepLong();
					if (uiWidget.exists())
					{
						return uiWidget;
					}

					if (!scrolled)
					{
						scrollCount++;
						if (scrollCount == 5)
						{
							return null;
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
		}
		return null;
	}

	/**
	 * Function 用于滚屏滑动到页面最底部
	 * 
	 * @param
	 * @return UiObject
	 * @date 2016年9月30日下午1:56:47
	 */
	protected void scrollToBottom(UiScrollable scrollHandler, int MaxStep) throws UiObjectNotFoundException
	{
		int scrollCount = 0;
		for (int x = 0; x < MaxStep; x++)
		{
			sleepLong();
			int randomNum = getRandomNum(0, 100);
			if (randomNum < 5)
			{
				sleepLong();
			} else if (randomNum > 90)
			{
				scrollHandler.scrollBackward(getScrollSteps());
			} else
			{
				boolean scrolled = scrollHandler.scrollForward();
				sleepLong();
				if (!scrolled)
				{
					scrollCount++;
					if (scrollCount == 5)
					{
						return;
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
	}

	/**
	 * Function 用于滚屏滑动到页面最顶部
	 * 
	 * @param
	 * @return UiObject
	 * @date 2016年9月30日下午1:56:47
	 */
	protected boolean scrollToTop(UiScrollable scrollHandler) throws UiObjectNotFoundException
	{
		int scrollCount = 0;
		for (int x = 0; x < Config.MAX_SEARCH_SWIPES; x++)
		{
			boolean scrolled = scrollHandler.scrollBackward();

			if (!scrolled)
			{
				scrollCount++;
				if (scrollCount == 5)
				{
					return true;
				} else
				{
					continue;
				}
			} else
			{
				scrollCount = 0;
			}
		}
		return false;
	}

	/**
	 * Function：根据UiSelector得到控件对象
	 * 
	 * @param
	 * @return UiObject
	 * @date 2016年9月30日下午1:58:50
	 */
	protected UiObject getWidget(UiSelector uiSelector)
	{
		return new UiObject(uiSelector);
	}

	/**
	 * Function：通过子控件得到父控件对象
	 * 
	 * @param
	 * @return UiObject
	 * @date 2016年9月30日下午1:58:50
	 */
	protected UiObject getParentWidget(UiSelector parentUiSelector, UiSelector childUiSelector)
	{
		return new UiObject(parentUiSelector.childSelector(childUiSelector));
	}

	/**
	 * Function：将字符串解析为自定义输入法能够识别的字符
	 * 
	 * @param
	 * @return void
	 * @date 2016年12月02日下午3:13:51
	 */
	protected String getUtfImeStr(String condition)
	{
		char[] c = condition.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < c.length; i++)
		{
			sb.append(Utf7ImeHelper.e(String.valueOf(c[i])));
		}
		return sb.toString();
	}

	/**
	 * Function：用于将日志文件存储到本地文件中
	 * 
	 * @param
	 * @return void
	 * @date 2016年9月30日下午4:04:21
	 */
	protected void createUiAutoLog(String infoStr)
	{
		uiAutoLogWriter(infoStr, true);
	}

	/**
	 * Function： 用于重置本地日志文件
	 * 
	 * @param
	 * @return boolean
	 * @date 2016年9月30日下午4:09:11
	 */
	protected void deleteUiAutoLog() throws IOException
	{
		uiAutoLogWriter("", false);

	}

	/** Function： 随机短延时 */
	protected void sleepShort()
	{
		int sleepTime = new Random().nextInt(1000) + 1000;
		sleep(sleepTime);
	}

	/** Function： 随机长延时 */
	protected void sleepLong()
	{
		int sleepTime = new Random().nextInt(2000) + 2000;
		sleep(sleepTime);
	}

	/** Function： 指定区间的随机延时 */
	protected void sleepRandom(int preTime, int befTime)
	{
		int sleepTime = new Random().nextInt(befTime - preTime + 1) + preTime;
		sleep(1000 * sleepTime);
	}

	/** Function： 指定区间的随机延时 */
	protected int getRandomNum(int preNum, int befNum)
	{
		return new Random().nextInt(befNum - preNum + 1) + preNum;

	}

	/**
	 * Function： 执行ADB命令
	 * 
	 * @throws IOException
	 */
	protected void doExec(String orderStr) throws IOException
	{
		Runtime.getRuntime().exec(orderStr);
	}

	private boolean isFolderExists(String strFolder)
	{
		File file = new File(strFolder);
		if (!file.exists())
		{
			if (file.mkdirs())
			{
				return true;
			} else
			{
				return false;

			}
		}
		return true;

	}

	@SuppressLint("SdCardPath")
	private void uiAutoLogWriter(String infoStr, boolean isAppend)
	{

		String mLogFilePath = "";
		if (isFolderExists("/sdcard/UIAutomatorLog"))
		{
			mLogFilePath = "/sdcard/UIAutomatorLog" + Config.RELATIVE_LOG_PATH;
		}

		FileWriter fw = null;
		try
		{
			fw = new FileWriter(mLogFilePath, isAppend);
			fw.write(infoStr + "\r\n");
			fw.flush();

		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				fw.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
