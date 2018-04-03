/*
 *    Copyright 2013 TOYAMA Sumio <jun.nama@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunda.input;

import java.nio.charset.Charset;
import java.util.Random;

import android.inputmethodservice.InputMethodService;
import android.text.method.MetaKeyKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.beetstra.jutf7.CharsetProvider;

/**
 * <p>
 * Utf7ImeService enables users to input any Unicode character by using only the
 * hardware keyboard. The selection of word candidates is not necessary. <br />
 * Using automated testing tools such as Uiautomator, it is impossible to input
 * non-ASCII characters directly. Utf7ImeService helps you to input any
 * characters by using Uiautomator.
 * </p>
 * <p>
 * String that is input from the keyboard, must be encoded in Modified UTF-7
 * (see RFC 3501).
 * </p>
 * 
 * @author TOYAMA Sumio
 * 
 */
public class ImeService extends InputMethodService
{

	private static final String TAG = "Utf7ImeService";

	/** Expected encoding for hardware key input. */
	private static final String CHARSET_MODIFIED_UTF7 = "X-MODIFIED-UTF-7";

	/** Special character to shift to Modified BASE64 in modified UTF-7. */
	private static final char MODIFIED_UTF7_SHIFT = '&';

	/** Indicates if current UTF-7 state is Modified BASE64 or not. */
	private long mMetaState;

	private Charset mModifiedUtf7Charset;

	private int charCount = 0;
	private StringBuffer mComposing;
	private boolean mIsShifted;

	@Override
	public void onStartInput(EditorInfo attribute, boolean restarting)
	{
		super.onStartInput(attribute, restarting);

		if (!restarting)
		{
			mMetaState = 0;
			mIsShifted = false;
			mModifiedUtf7Charset = new CharsetProvider().charsetForName(CHARSET_MODIFIED_UTF7);
		}
		mComposing = null;

	}

	@Override
	public void onFinishInput()
	{
		super.onFinishInput();
		mModifiedUtf7Charset = null;
		mComposing = null;
	}

	@Override
	public boolean onEvaluateFullscreenMode()
	{
		return false;
	}

	@Override
	public boolean onEvaluateInputViewShown()
	{
		return false;
	}

	/**
	 * Translates key events encoded in modified UTF-7 into Unicode text.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		int c = getUnicodeChar(keyCode, event);

		if (c == 0)
		{
			return super.onKeyDown(keyCode, event);
		}

		if (!mIsShifted)
		{
			mIsShifted = true;
			mComposing = new StringBuffer();
		}

		if (isAsciiPrintable(c))
		{
			appendComposing(c);
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		mMetaState = MetaKeyKeyListener.handleKeyUp(mMetaState, keyCode, event);
		return super.onKeyUp(keyCode, event);
	}

	private int getUnicodeChar(int keyCode, KeyEvent event)
	{
		mMetaState = MetaKeyKeyListener.handleKeyDown(mMetaState, keyCode, event);
		int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(mMetaState));
		mMetaState = MetaKeyKeyListener.adjustMetaAfterKeypress(mMetaState);
		return c;
	}

	private void appendComposing(int c)
	{
		charCount++;
		if (charCount == 1 && c != MODIFIED_UTF7_SHIFT)
		{
			String decoded = decodeUtf7(String.valueOf((char) c));
			InputConnection ic = getCurrentInputConnection();
			ic.commitText(decoded, 1);
			charCount = 0;
			return;
		}

		mComposing.append((char) c);
		if (charCount == 5)
		{
			Log.d(TAG, "startDecode----" + mComposing.toString());
			String decoded = decodeUtf7(mComposing.toString());
			Log.d(TAG, decoded);
			InputConnection ic = getCurrentInputConnection();
			ic.commitText(decoded, 1);
			mComposing = new StringBuffer();
			charCount = 0;

			sleepRandom();
		}
	}

	private String decodeUtf7(String encStr)
	{
		byte[] encoded = encStr.getBytes(Charset.forName("US-ASCII"));
		return new String(encoded, mModifiedUtf7Charset);
	}

	private boolean isAsciiPrintable(int c)
	{
		return c >= 0x20 && c <= 0x7E;
	}

	private void sleepRandom()
	{
		int randomTime = new Random().nextInt(2000) + 1000;
		try
		{
			Thread.sleep(randomTime);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
