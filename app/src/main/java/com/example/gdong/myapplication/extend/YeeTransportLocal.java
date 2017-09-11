package com.example.gdong.myapplication.extend;

import android.content.Context;

import org.csii.yeeframe.http.HttpCallBack;
import org.csii.yeeframe.http.HttpParams;
import org.csii.yeeframe.utils.YeeUtilsStream;

public class YeeTransportLocal implements ITransport{

	private Context mContext;

	public YeeTransportLocal(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public void submit(String apiUrl, HttpParams params, HttpCallBack callback) {
	    try {
	    	callback.onPreStart();
			byte[] bytes = YeeUtilsStream.readStream(mContext.getAssets().open(Constants.ANDROID_ASSET_API_FILES + apiUrl));
			callback.onSuccess(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
			callback.onFailure(1, "ERR0001");
		}
	    callback.onFinish();
	}
	
}
