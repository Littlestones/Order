/*
 * Copyright (c) 2014,CSII.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.csii.yeeframe.http;

import android.os.Environment;
import android.util.Log;

import org.csii.yeeframe.extend.Constants;
import org.csii.yeeframe.utils.YeeUtilsFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Form表单形式的Http请求
 *
 * @author yee.zh
 */
public class FormRequest extends Request<byte[]> {

    private final HttpParams mParams;

    private String url;

    public FormRequest(String url, HttpCallBack callback) {
        this(HttpMethod.GET, url, null, callback);
    }

    public FormRequest(int httpMethod, String url, HttpParams params,
                       HttpCallBack callback) {
        super(httpMethod, url, callback);
        this.url = url;
        if (params == null) {
            params = new HttpParams();
        }
        this.mParams = params;
    }

    @Override
    public String getCacheKey() {
        if (getMethod() == HttpMethod.POST) {
            return getUrl() + mParams.getUrlParams();
        } else {
            return getUrl();
        }
    }

    @Override
    public String getBodyContentType() {
        if (mParams.getContentType() != null) {
            return mParams.getContentType().getValue();
        } else {
            return super.getBodyContentType();
        }
    }

    @Override
    public Map<String, String> getHeaders() {
        return mParams.getHeaders();
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mParams.writeTo(bos);
        } catch (IOException e) {
            Log.e("yee", "IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
        //StringBuilder是用来组拼请求参数
//        Map<String, String> params = mParams.getUrlParamsBody();
//        StringBuilder sb = new StringBuilder();
//        if (params != null && params.size() != 0) {
//            try {
//                for (Map.Entry<String, String> entry : params.entrySet()) {
//                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), getParamsEncoding()));
//                    sb.append("&");
//                }
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException("Encoding not supported: " + getParamsEncoding(), e);
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            YeeLoger.debug(getUrl(),sb.toString());
//        }
//        return sb.toString().getBytes();
    }

    @Override
    public Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, response.headers,
                HttpHeaderParser.parseCacheHeaders(mConfig, response));
    }

    @Override
    protected void deliverResponse(Map<String, String> headers, byte[] response) {
        if (mCallback != null) {
            if (Constants.TEST) {
                String[] split = url.split("/");
                String s = split[split.length - 1];
                String path = "";
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android" + File.separator + "data" + File.separator + "csii.com.jxbank" + File.separator + "json" + File.separator + s.replaceAll(".do", "");
                }
                YeeUtilsFile.writeFile(response, path, false);
            }
            mCallback.onSuccess(headers, response);
        }
    }
}
