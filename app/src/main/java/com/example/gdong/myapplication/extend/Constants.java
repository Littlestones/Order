package com.example.gdong.myapplication.extend;


/**
 * @Description:常量定义类，系统所需常量，尽量在该类定义
 * @author zh
 * 
 */
public class Constants {

    public static boolean TEST = false;
    public static final boolean isDebug = false;
    public static final String BLANK = "";
    public static final String ENCODE_UTF = "UTF-8";
    public static final String ENCODE_GBK = "GBK";
    public static String HTTP = "http://";
    public static String HTTP_URL = "127.0.0.1:8888";

    public static final int TimeOut = 120000;	//加载超时时间
    public final static String IMEI = "IMEI";		//机器的IMEI号
    
    public final static String socketIp = "127.0.0.1";
    public final static int socketPort = 5000;
    
    public final static String CURRENCY_CNY = "CNY";
    public final static String CURRENCY_USD = "USD";
    
    public static final String SESSION_OUTTIME = "会话已超时";
    
    public static final String ANDROID_ASSET_API_FILES = "api/";
	public static final int TRANSPORT_TYPE_ANDROID = 0;
	public static final int TRANSPORT_TYPE_HTTP = 1;
	
	public static final String RET_ERROR_CODE = "_exceptionMessageCode";
	public static final String RET_ERROR_MSG = "_exceptionMessage";
	public static final String RET_ERROR = "jsonError";
	
	public static final String ERROR_HOST_NOCODE = "999999";// 服务端系统错误，无返回码
	public static final String ERROR_JSON_FORMAT = "999998";// 服务端返回数据格式错误
	public static final String INVALID_USER_CODE = "role.invalid_user";
	
	public static final String LOGIN_STATUS_LOGINED = "1";// 已登录
	public static final String LOGIN_STATUS_NOTLOGIN = "0"; // 未登录
	
	public static final int DIALOG_CONFRIM = 0;
	public static final int DIALOG_TIP = 1;
    
}
