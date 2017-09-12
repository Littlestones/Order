package org.csii.yeeframe.extend;

import org.csii.yeeframe.http.HttpCallBack;
import org.csii.yeeframe.http.HttpParams;

public interface ITransport {

	void submit(String code, HttpParams params, HttpCallBack callback);
}
