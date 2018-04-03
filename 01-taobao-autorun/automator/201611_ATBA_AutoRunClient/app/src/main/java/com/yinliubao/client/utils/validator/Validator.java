package com.yinliubao.client.utils.validator;

import java.util.ArrayList;
import java.util.List;

public class Validator
{
	private static Validator instance;

	private final List<Rule> mRules;

	private Validator()
	{
		mRules = new ArrayList<Rule>();
	}

	public static Validator getInstance()
	{
		return new Validator();
	}

	public interface ValidatorListener
	{
		public void onFailed(Rule rule);

		public void onSuccess();
	}

	private ValidatorListener mValidatorListener;

	public ValidatorListener getValidatorListener()
	{
		return mValidatorListener;
	}

	public void setValidatorListener(ValidatorListener mValidatorListener)
	{
		this.mValidatorListener = mValidatorListener;
	}

	public void validate()
	{
		Rule failedRule = validateAllRules();
		if (failedRule == null)
		{
			mValidatorListener.onSuccess();
		} else
		{
			mValidatorListener.onFailed(failedRule);
		}
		// clear();
	}

	public void putRule(Rule rule)
	{
		mRules.add(rule);
	}

	public void clear()
	{
		mRules.clear();
	}

	private Rule validateAllRules()
	{
		Rule failedRule = null;
		for (Rule rule : mRules)
		{
			if (rule == null)
				continue;
			if (!rule.isValid())
			{
				failedRule = rule;
				break;
			}
		}
		return failedRule;
	}

}
