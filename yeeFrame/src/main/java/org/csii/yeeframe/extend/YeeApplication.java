package org.csii.yeeframe.extend;

import java.io.IOException;
import java.util.Properties;

import org.csii.yeeframe.utils.YeeUtilsFile;
import org.csii.yeeframe.utils.YeeUtilsSystem;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;

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
        anyXApplication = this;
		IMEI= YeeUtilsSystem.getPhoneIMEI(anyXApplication);
        String mappings = YeeUtilsFile.readFileFromAssets(anyXApplication, "spinnerApi");
		try {
			spinnerApi = new JSONObject(mappings);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (httpApi == null) {
			try {
				httpApi = new Properties();
				httpApi.load(this.getAssets().open("httpApi.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
