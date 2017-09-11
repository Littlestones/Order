package com.example.gdong.myapplication.extend;

import org.csii.yeeframe.YeeHttp;
import org.csii.yeeframe.http.HttpCallBack;
import org.csii.yeeframe.http.HttpConfig;
import org.csii.yeeframe.http.HttpParams;
import org.csii.yeeframe.utils.YeeLoger;
import org.csii.yeeframe.utils.YeeUtilsSystem;

public class YeeTransportHttp implements ITransport {

	private static HttpConfig config;
	static {
		config = new HttpConfig();
		// config.maxRetries = 3;// 出错重连次数
		// 向服务器写cookie
		// config.httpHeader.put("ContentType", "UTF8");
		config.useDelayCache = false;
		config.cacheTime = 0;
		config.TIMEOUT = Constants.TimeOut;
	}

	@Override
	public void submit(String apiUrl, HttpParams httpParams, HttpCallBack callback) {
		YeeLoger.debug("请求报文：" + httpParams.toString());
		YeeHttp yeeHttp = new YeeHttp();
		httpParams.putHeaders("charset", "UTF-8");
		httpParams.putHeaders("Accept", "application/json");
		httpParams.put("Imei", YeeApplication.IMEI);
		httpParams.put("Clientip", YeeUtilsSystem.getDeviceIP());

		// 获取服务器返回的cookie
		String cookieString = config.getCookieString();
		if (cookieString != null && !"".equals(cookieString)) {
				config.setCookieString(cookieString); // 多个cookie用分号隔开
				YeeLoger.debug("这个是cookie" + cookieString);
		}
		yeeHttp.setConfig(config);
		yeeHttp.post(resolveApi(apiUrl), httpParams, callback);

	}

	private String resolveApi(String apiCode) {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(Constants.HTTP);
		stringBuilder.append(Constants.HTTP_URL);
		stringBuilder.append((String)YeeApplication.httpApi.get(apiCode));
		return stringBuilder.toString();
	}
}
