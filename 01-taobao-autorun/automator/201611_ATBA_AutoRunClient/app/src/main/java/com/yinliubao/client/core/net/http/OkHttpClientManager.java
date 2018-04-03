package com.yinliubao.client.core.net.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.Set;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.yinliubao.client.core.storage.SharedPreferHelper;
import com.yinliubao.client.functions_common.bean.CommonRequestVo;
import com.yinliubao.client.functions_common.settings.ProConfigs;
import com.yinliubao.client.utils.Base64Helper;
import com.yinliubao.client.utils.GsonHelper;
import com.yinliubao.client.utils.Md5Helper;
import com.yinliubao.client.utils.UrlCodeHelper;
import com.yinliubao.client.views.WaitingDialog;

@SuppressWarnings("rawtypes")
public class OkHttpClientManager
{
	public static final String RESP_OK="0";
	
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final MediaType URLENCODE = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
	private static OkHttpClientManager mInstance;
	private OkHttpClient mOkHttpClient;
	private Handler mDelivery;
	private Gson mGson;

	private static final String TAG = "OkHttpClientManager";

	private OkHttpClientManager()
	{
		mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
		mDelivery = new Handler(Looper.getMainLooper());
	}

	public static OkHttpClientManager getInstance()
	{
//		if (mInstance == null)
//		{
//			synchronized (OkHttpClientManager.class)
//			{
//				if (mInstance == null)
//				{
					mInstance = new OkHttpClientManager();
//				}
//			}
//		}
		return mInstance;
	}

	/*************************************** 对外公布的方法begin ****************************************************************/

	public static void doAsynGetRequest(String url, Callback callback)
	{
		getInstance()._getAsyn(url, callback);
	}

	public static void doAsynPostRequest(CommonRequestVo requestVo,String session, OkHttpCallBack callback)
	{
		getInstance()._postAsyn(ProConfigs.YLB_BASE_URL, requestVo,session, callback);
	}


	public static void doAsynPostRequest(String url, Map<String, String> params, final ResultCallback callback)
	{
		getInstance()._postAsyn(url, params, callback);
	}

	public static void downloadAsyn(String url, String destDir, ResultCallback callback)
	{
		getInstance()._downloadAsyn(url, destDir, callback);
	}

	/*************************************** 对外公布的方法end ****************************************************************/

	/**
	 * 异步的get请求
	 * 
	 * @param url
	 * @param callback
	 */
	private void _getAsyn(String url, final Callback callback)
	{
		final Request request = new Request.Builder().url(url).build();
		Call call = mOkHttpClient.newCall(request);
		// 请求加入调度
		call.enqueue(callback);
	}

	/**
	 * 异步的post请求
	 * 
	 * @param url
	 * @param callback
	 * @param params
	 */
	private void _postAsyn(String url, Object requestVo,String session, final OkHttpCallBack callback)
	{
		RequestBody requestBody = RequestBody.create(JSON, GsonHelper.obj2Json(requestVo));
		Request request = null;
		if (session != null){
			request = new Request.Builder().url(url).post(requestBody).header("cookie", session).build();
		} else
		{
			request = new Request.Builder().url(url).post(requestBody).build();
		}
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback()
		{
			@Override
			public void onFailure(final Request request, final IOException e)
			{
				mDelivery.post(new Runnable()
				{
					@Override
					public void run()
					{
						if (callback != null)
							callback.onFailure(request, e);
					}
				});
			}

			@Override
			public void onResponse(final Response respBody) throws IOException
			{
				mDelivery.post(new Runnable()
				{
					@Override
					public void run()
					{
						if (callback != null){
							String session=null;
							String body=null;
							
							//获取返回数据中的session
							if (null != respBody.headers().values("set-cookie") && respBody.headers().values("set-cookie").size() > 0)
							{
								session = respBody.headers().values("set-cookie").get(0);
							}
							
							//解决对接PHP接口中遇到的坑
							try
							{
								body = respBody.body().string();
								
								if(body.contains("[]")){
									body=body.replace("[]", "{}");
								}
								
								if(body.contains("null")){
									body=body.replace("null", "\"\"");
								}
							} catch (IOException e)
							{
								e.printStackTrace();
							}
							callback.onResponse(body,session);
						}
							
					}
				});
			}

		});

		Log.d(TAG, url);
		Log.d(TAG, GsonHelper.obj2Json(requestVo));
	}

//	private void _postAsyn2(String url, RequestVo requestVo, String session, final OkHttpCallBack callback)
//	{
//		// String
//		// requestCode=UrlCodeHelper.httpUrlEncode(requestVo.getRequest());
//		// String version="1.0";
//		// String
//		// partnerid=UrlCodeHelper.httpUrlEncode(requestVo.getPartnerid());
//		// String
//		// jsondata=UrlCodeHelper.httpUrlEncode(Base64Helper.encode(GsonHelper.objectToJson(requestVo.getJsondata())));
//		// String
//		// validation=Md5Coder.md5(Base64Helper.encode(GsonHelper.objectToJson(requestVo.getJsondata()))+requestVo.getPartnerid()+requestVo.getPassword());
//		//
//		String requestCode = UrlCodeHelper.httpUrlEncode(requestVo.getRequest());
//		String version = UrlCodeHelper.httpUrlEncode("1.0");
//		String partnerid = UrlCodeHelper.httpUrlEncode(requestVo.getPartnerid());
//		String jsondata = UrlCodeHelper.httpUrlEncode(requestVo.getJsondata());
//		String validation = UrlCodeHelper.httpUrlEncode((Md5Coder.md5(requestVo.getJsondata() + requestVo.getPartnerid() + requestVo.getPassword())));
//
//		String requestData = "request=" + requestCode + "&version=" + version + "&partnerid=" + partnerid + "&validation=" + validation + "&jsondata=" + jsondata;
//
//		RequestBody requestBody = RequestBody.create(URLENCODE, requestData);
//
//		Request request = null;
//		if (session != null){
//			request = new Request.Builder().url(url).post(requestBody).header("set-cookie", session).build();
//		} else
//		{
//			request = new Request.Builder().url(url).post(requestBody).build();
//		}
//		Call call = mOkHttpClient.newCall(request);
//		call.enqueue(new Callback()
//		{
//			@Override
//			public void onFailure(final Request request, final IOException e)
//			{
//				mDelivery.post(new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						if (callback != null)
//							callback.onFailure(request, e);
//					}
//				});
//			}
//
//			@Override
//			public void onResponse(final Response respBody) throws IOException
//			{
//				mDelivery.post(new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						if (callback != null)
//							callback.onResponse(respBody);
//					}
//				});
//			}
//
//		});
//
//		Log.d(TAG, url);
//		Log.d(TAG, GsonHelper.objectToJson(requestVo));
//	}

	/**
	 * 异步的post请求
	 * 
	 * @param url
	 * @param callback
	 * @param params
	 */
	private void _postAsyn(String url, Map<String, String> params, final ResultCallback callback)
	{
		Param[] paramsArr = map2Params(params);
		Request request = buildPostRequest(url, paramsArr);
		deliveryResult(callback, request);
	}

	/**
	 * 异步下载文件
	 * 
	 * @param url
	 * @param destFileDir
	 *            本地文件存储的文件夹
	 * @param callback
	 */
	private void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback)
	{
		final Request request = new Request.Builder().url(url).build();
		final Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback()
		{
			@Override
			public void onFailure(final Request request, final IOException e)
			{
				sendFailedStringCallback(request, e, callback);
			}

			@Override
			public void onResponse(Response response)
			{
				InputStream is = null;
				byte[] buf = new byte[2048];
				int len = 0;
				FileOutputStream fos = null;
				try
				{
					is = response.body().byteStream();
					File file = new File(destFileDir, getFileName(url));
					fos = new FileOutputStream(file);
					while ((len = is.read(buf)) != -1)
					{
						fos.write(buf, 0, len);
					}
					fos.flush();
					// 如果下载文件成功，第一个参数为文件的绝对路径
					sendSuccessResultCallback(file.getAbsolutePath(), callback);
				} catch (IOException e)
				{
					sendFailedStringCallback(response.request(), e, callback);
				} finally
				{
					try
					{
						if (is != null)
							is.close();
					} catch (IOException e)
					{
					}
					try
					{
						if (fos != null)
							fos.close();
					} catch (IOException e)
					{
					}
				}

			}
		});
	}

	private String getFileName(String path)
	{
		int separatorIndex = path.lastIndexOf("/");
		return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
	}

	private Param[] map2Params(Map<String, String> params)
	{
		if (params == null)
			return new Param[0];
		int size = params.size();
		Param[] res = new Param[size];
		Set<Map.Entry<String, String>> entries = params.entrySet();
		int i = 0;
		for (Map.Entry<String, String> entry : entries)
		{
			res[i++] = new Param(entry.getKey(), entry.getValue());
		}
		return res;
	}

	private void deliveryResult(final ResultCallback callback, Request request)
	{
		mOkHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(final Request request, final IOException e)
			{
				sendFailedStringCallback(request, e, callback);
			}

			@Override
			public void onResponse(final Response response)
			{
				try
				{
					final String string = response.body().string();
					if (callback.mType == String.class)
					{
						sendSuccessResultCallback(string, callback);
					} else
					{
						Object o = mGson.fromJson(string, callback.mType);
						sendSuccessResultCallback(o, callback);
					}

				} catch (IOException e)
				{
					sendFailedStringCallback(response.request(), e, callback);
				} catch (com.google.gson.JsonParseException e)// Json解析的错误
				{
					sendFailedStringCallback(response.request(), e, callback);
				}

			}
		});
	}

	public interface OkHttpCallBack 
	{
		public void onFailure(Request respBody, IOException e);

		public void onResponse(String body,String session);
	}

	private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback)
	{
		mDelivery.post(new Runnable()
		{
			@Override
			public void run()
			{
				if (callback != null)
					callback.onError(request, e);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void sendSuccessResultCallback(final Object object, final ResultCallback callback)
	{
		mDelivery.post(new Runnable()
		{

			@Override
			public void run()
			{
				if (callback != null)
				{
					callback.onResponse(object);
				}
			}
		});
	}

	private Request buildPostRequest(String url, Param[] params)
	{
		if (params == null)
		{
			params = new Param[0];
		}
		FormEncodingBuilder builder = new FormEncodingBuilder();
		for (Param param : params)
		{
			builder.add(param.key, param.value);
		}
		RequestBody requestBody = builder.build();
		return new Request.Builder().url(url).post(requestBody).build();
	}

	public static abstract class ResultCallback<T>
	{
		Type mType;

		public ResultCallback()
		{
			mType = getSuperclassTypeParameter(getClass());
		}

		static Type getSuperclassTypeParameter(Class<?> subclass)
		{
			Type superclass = subclass.getGenericSuperclass();
			if (superclass instanceof Class)
			{
				throw new RuntimeException("Missing type parameter.");
			}
			ParameterizedType parameterized = (ParameterizedType) superclass;
			return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
		}

		public abstract void onError(Request request, Exception e);

		public abstract void onResponse(T response);
	}

	public static class Param
	{
		public Param()
		{
		}

		public Param(String key, String value)
		{
			this.key = key;
			this.value = value;
		}

		String key;
		String value;
	}

}
