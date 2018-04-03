package com.yinliubao.client.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.Spinner;

class ValidatorRules
{

	public static boolean checkRegex(String input, String regex)
	{
		boolean matchFlag;
		try
		{
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(input);
			matchFlag = matcher.matches();
		} catch (Exception e)
		{
			matchFlag = false;
		}
		return matchFlag;
	}

	public static boolean checkMaxLength(String input, int maxLength)
	{
		return input.length() <= maxLength;
	}

	public static boolean checkMinLength(String input, int minLength)
	{
		return input.length() >= minLength;
	}

	public static boolean isSpinnerHasValue(int mSpinnerPos)
	{
		return mSpinnerPos >= 0;

	}

	public static boolean checkMaxValue(Comparable input, Comparable maxValue)
	{
		return input.compareTo(maxValue) <= 0;
	}

	public static boolean checkMinValue(Comparable input, Comparable minValue)
	{
		return input.compareTo(minValue) >= 0;
	}

	public static boolean checkEqual(String input, String equalValue)
	{
		return input.equals(equalValue);
	}
}
