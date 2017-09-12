package org.csii.yeeframe.extend;

import org.csii.yeeframe.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 通用下拉框组件适配器，获取集中配置下拉框属性映射，回传给UI层
 * 
 * @author anyX.zhangh
 * 
 */
public class YeeSpinnerAdapter extends BaseAdapter {

	Context context;
	JSONArray items;
	private int mLayout;
	private int dropDownLayout=-1;

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param dictKey 字典表对应key
	 *
	 * @param mLayout Adapter布局文件
	 *                   其TextView的Tag必须设置为“test”
	 *@param  dropDownLayout 按下视图
	 * 
	 */
	public YeeSpinnerAdapter(Context context, String dictKey,int mLayout,int dropDownLayout) {
		super();
		this.context = context;
		this.mLayout=mLayout;
		this.dropDownLayout=dropDownLayout;
		try {
			items = YeeApplication.spinnerApi.getJSONArray(dictKey);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public YeeSpinnerAdapter(Context context, String dictKey,int mLayout) {
		super();
		this.context = context;
		this.mLayout=mLayout;
		try {
			items = YeeApplication.spinnerApi.getJSONArray(dictKey);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated Use {@link #YeeSpinnerAdapter(Context,String,int )} instead.
	 */
	public YeeSpinnerAdapter(Context context, String dictKey) {
		super();
		this.context = context;
		try {
			items = YeeApplication.spinnerApi.getJSONArray(dictKey);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		return items.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			JSONObject item = items.getJSONObject(position);
			if (item != null && item.length() != 0) {
				return new YeeSpinnerElement(item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取下拉框Text值
	 * 
	 * @param position
	 * @return
	 */
	public String getText(int position) {
		YeeSpinnerElement item = (YeeSpinnerElement) getItem(position);
		String text = Constants.BLANK;
		text = item.getText();
		return text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (mLayout==0){
				mLayout= R.layout.common_spinner_item_view;
			}
			convertView=mInflater.inflate(mLayout, parent, false);
		}
		TextView textView = (TextView) convertView.findViewWithTag("text");
		textView.setText(getText(position));
		return convertView;
	}

	/**
	 * Spinner 下拉Item中返回的Item 默认返回getView中的View
	 */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		convertView=super.getDropDownView(position,convertView,parent);
		if (dropDownLayout!=-1){
			LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=mInflater.inflate(dropDownLayout, parent, false);
			TextView textView = (TextView) convertView.findViewWithTag("text");
			textView.setText(getText(position));
		}
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
