package com.yinliubao.client.functions.mysetting.bean;

import com.yinliubao.client.functions.mysetting.bean.RespAccountRestVo.AccountRestVo;
import com.yinliubao.client.functions_common.bean.CommonResponseVo;

/*******************************************************************************
 * { "res": "0", "message":"" "orders":[ { "account_status":"账户状态",
 * "account_value":"余额" }] }
 *******************************************************************************/
public class RespAccountRestVo extends CommonResponseVo<AccountRestVo>
{
	public static class AccountRestVo
	{
		private String account_value;
		private String account_status;

		public String getAccount_value()
		{
			return account_value;
		}

		public void setAccount_value(String account_value)
		{
			this.account_value = account_value;
		}

		public String getAccount_status()
		{
			return account_status;
		}

		public void setAccount_status(String account_status)
		{
			this.account_status = account_status;
		}

	}

}
