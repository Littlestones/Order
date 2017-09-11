package com.example.gdong.myapplication.extend;

import android.app.Application;

import com.vondear.rxtools.RxUtils;

import org.csii.yeeframe.utils.YeeUtilsFile;
import org.csii.yeeframe.utils.YeeUtilsSystem;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

public class YeeApplication extends Application {
    public final static Properties mCommonProperties = new Properties();
    public static Properties httpApi;
    public static YeeApplication anyXApplication;
    public static JSONObject spinnerApi;
	public static String IMEI="";
    protected static int defaultTransport = Constants.TRANSPORT_TYPE_ANDROID;
    
    public static String LOGINSTATUS = "0";

    @Override
    public void onCreate() {
		super.onCreate();
		RxUtils.init(this);
        anyXApplication = this;
		IMEI= YeeUtilsSystem.getPhoneIMEI(anyXApplication);

    }

	
	public static ITransport getTransport(){
		return getTransport(defaultTransport);
	}
	
	public static ITransport getTransport(int type) {
		switch (type) {
		case Constants.TRANSPORT_TYPE_ANDROID:
			return new YeeTransportLocal(anyXApplication);
		case Constants.TRANSPORT_TYPE_HTTP:
			return new YeeTransportHttp();
		}
		return new YeeTransportLocal(anyXApplication);
	}
}
