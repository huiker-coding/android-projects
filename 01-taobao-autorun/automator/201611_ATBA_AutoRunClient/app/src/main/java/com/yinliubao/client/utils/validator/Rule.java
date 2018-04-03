package com.yinliubao.client.utils.validator;

import android.widget.EditText;
import android.widget.Spinner;

public class Rule
{
	private String failureMessage;
	private boolean isValid;
	private EditText mEditText;
	private Spinner mSpinner;

	public String getFailureMessage()
	{
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage)
	{
		this.failureMessage = failureMessage;
	}

	public boolean isValid()
	{
		return isValid;
	}

	public void setValid(boolean isValid)
	{
		this.isValid = isValid;
	}

	public EditText getEditText()
	{
		return mEditText;
	}

	public void setEditText(EditText editText)
	{
		this.mEditText = editText;
	}

	public Spinner getSpinner()
	{
		return mSpinner;
	}

	public void setSpinner(Spinner mSpinner)
	{
		this.mSpinner = mSpinner;
	}

}
