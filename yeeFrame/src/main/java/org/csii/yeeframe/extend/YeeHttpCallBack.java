package org.csii.yeeframe.extend;

import android.text.TextUtils;
import android.view.View;

import org.csii.yeeframe.R;
import org.csii.yeeframe.http.HttpCallBack;
import org.csii.yeeframe.utils.YeeLoger;
import org.csii.yeeframe.utils.YeeUtilsString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class YeeHttpCallBack extends HttpCallBack {

	public YeeDialog dialog;

	public YeeHttpCallBack(YeeDialog dialog) {
		super();
		this.dialog = dialog;
	}

	public boolean isSuccess(JSONObject json) {
		try {
			if (!YeeUtilsString.isEmpty(json.getString(Constants.RET_ERROR))) {
				return false;
			} else {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void onPreStart() {
		dialog.show();
		YeeLoger.debug("即将开始http请求");
	}

	@Override
	public void onSuccess(String result) {
		dialog.dismiss();
		try {
			JSONObject json = new JSONObject(result);
			if (!isSuccess(json)) {
				RespondError error = null;

				String retMsg = json.getString(Constants.RET_ERROR);
				if (YeeUtilsString.isEmpty(retMsg)) {
					error = new RespondError(Constants.ERROR_HOST_NOCODE, "服务端返回异常(未提供错误码/错误信息)");
				} else {
					JSONArray retArr = new JSONArray(retMsg);
					JSONObject retObj = retArr.getJSONObject(0);
					if (retObj != null) {
						String errCode = retObj.getString(Constants.RET_ERROR_CODE) != null ? retObj
								.getString(Constants.RET_ERROR_CODE) : Constants.BLANK;
						String errMsg = retObj.getString(Constants.RET_ERROR_MSG) != null ? retObj
								.getString(Constants.RET_ERROR_MSG) : Constants.BLANK;
						if (Constants.INVALID_USER_CODE.equals(errCode)) {
							YeeApplication. LOGINSTATUS = Constants.LOGIN_STATUS_NOTLOGIN;
						}
						error = new RespondError(errCode, errMsg);
					} else
						error = new RespondError(Constants.ERROR_HOST_NOCODE, "服务端返回异常(未提供错误码/错误信息)");
				}

				failCallBack(error);
			} else {
				YeeLoger.debug("请求成功:" + result.toString());
				successCallBack(json);
			}
		} catch (JSONException e) {
			failCallBack(new RespondError(Constants.ERROR_JSON_FORMAT, "数据解析失败", e));
		}
	}

	@Override
	public void onFailure(int errorNo, String strMsg) {
		dialog.dismiss();
		YeeLoger.debug("出现异常:[" + errorNo + "]-" + strMsg);
		RespondError error = new RespondError(String.valueOf(errorNo), strMsg);

		failCallBack(error);
	}

	@Override
	public void onFinish() {
		YeeLoger.debug("请求完成，不管成功还是失败");
	}

	boolean flag = false;

	public void failCallBack(RespondError error) {
		YeeLoger.debug("请求失败:", error);
		dialog.show();
		if (YeeUtilsString.isEmpty(error.getMessage())) {
			dialog.content.setText("通讯异常");
		} else if (TextUtils.equals(Constants.SESSION_OUTTIME, error.getMessage())) {
			dialog.content.setText(error.getMessage());
			flag = true;
		} else {
			dialog.content.setText(error.getMessage());
		}
		dialog.findViewById(R.id.dialog_confirm).setVisibility(View.GONE);
	}

	public abstract void successCallBack(JSONObject json) throws JSONException;

}
