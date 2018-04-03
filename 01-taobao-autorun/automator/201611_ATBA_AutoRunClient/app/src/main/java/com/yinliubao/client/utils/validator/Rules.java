package com.yinliubao.client.utils.validator;

import java.math.BigDecimal;

import android.widget.EditText;
import android.widget.Spinner;

public class Rules
{
	private final static String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	private final static String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private final static String REGEX_USERNAME = "^[a-zA-Z_]\\w{2,19}$";
	private final static String REGEX_PASSWORD = "[0-9a-zA-Z_]+";
	private final static String REGEX_MAILNO = "^[0-9]{13}$";

	public static Rule maxValue(Comparable maxValue, String failureMessage, Comparable value, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);

		boolean isValid = ValidatorRules.checkMaxValue(value, maxValue);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule maxLength(int maxLength, String failureMessage, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);
		boolean isValid = ValidatorRules.checkMaxLength(editText.getText().toString(), maxLength);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule minLength(int minLength, String failureMessage, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);
		boolean isValid = ValidatorRules.checkMinLength(editText.getText().toString(), minLength);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule minLengthTwoEditText(int minLength, String failureMessage, EditText editText1, EditText editText2)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		boolean isValid = ValidatorRules.checkMinLength(editText1.getText().toString() + editText2.getText().toString() , minLength);
		resultRule.setValid(isValid);
		return resultRule;
	}
	
	public static Rule minLengthFourEditText(int minLength, String failureMessage, EditText editText1, EditText editText2, EditText editText3, EditText editText4)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		boolean isValid = ValidatorRules.checkMinLength(editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString()+ editText4.getText().toString(), minLength);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule minLengthSpinner(String failureMessage, Spinner spinner)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setSpinner(spinner);
		boolean isValid = ValidatorRules.isSpinnerHasValue(spinner.getSelectedItemPosition());
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule checkMobile(String failureMessage, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);
		boolean isValid = ValidatorRules.checkRegex(editText.getText().toString(), REGEX_MOBILE);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule checkEmail(String failureMessage, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);
		boolean isValid = ValidatorRules.checkRegex(editText.getText().toString(), REGEX_EMAIL);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule checkUserName(String failureMessage, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);
		boolean isValid = ValidatorRules.checkRegex(editText.getText().toString(), REGEX_USERNAME);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule checkPassword(String failureMessage, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);
		boolean isValid = ValidatorRules.checkRegex(editText.getText().toString(), REGEX_PASSWORD);
		resultRule.setValid(isValid);
		return resultRule;
	}

	public static Rule checkMailnoWithSpace(String failureMessage, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);
		boolean isValid = ValidatorRules.checkRegex(editText.getText().toString().trim().replace(" ", ""), REGEX_MAILNO);
		resultRule.setValid(isValid);
		return resultRule;
	}
	
	public static Rule minValue(Comparable minValue, String failureMessage, Comparable value, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);

		boolean isValid = ValidatorRules.checkMinValue(value, minValue);
		resultRule.setValid(isValid);
		return resultRule;
	}
	public static Rule notEqual(String equalValue, String failureMessage, String value, EditText editText)
	{
		Rule resultRule = new Rule();
		resultRule.setFailureMessage(failureMessage);
		resultRule.setEditText(editText);

		boolean isValid = ValidatorRules.checkEqual(value, equalValue);
		resultRule.setValid(!isValid);
		return resultRule;
	}
}
