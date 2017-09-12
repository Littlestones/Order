package org.csii.yeeframe.extend;

import org.json.JSONException;
import org.json.JSONObject;

public class YeeSpinnerElement{

	JSONObject data;
	
	public YeeSpinnerElement(JSONObject obj){
		this.data = obj;
	}
	
	public String getOption() {
		try {
			return data.getString(SpinnerConstants.configOption);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	public String getText() {
		try {
			return data.getString(SpinnerConstants.configText);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
}
