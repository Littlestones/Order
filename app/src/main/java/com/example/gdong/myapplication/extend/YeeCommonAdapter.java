package com.example.gdong.myapplication.extend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.csii.yeeframe.utils.YeeJSONUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by DuMing on 2015/11/5.
 *      通用适配器，可用于Spinner和ListView
 *      样式自行定义
 */
public class YeeCommonAdapter extends BaseAdapter {

    private Context mContext;
    private int mLayout;
    private JSONArray mJsonArray;


    public YeeCommonAdapter(Context mContext, int mLayout, JSONArray mJsonArray) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mJsonArray = mJsonArray;
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        Object item=null;
        try {
            item=mJsonArray.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=mInflater.inflate(mLayout, parent, false);
        }
        JSONObject mJObject= YeeJSONUtil.getJSONObject(mJsonArray, position);
        Iterator keys=mJObject.keys();
        while (keys.hasNext()){
            String key=keys.next().toString();
            String value=YeeJSONUtil.getString(mJObject, key);
            /*if(jsonDICT.has(key)){
                value=YeeJSONUtil.getJArrayName(JSONUtil.getJSONArray(jsonDICT,key),value);
            }*/
            TextView textView=(TextView)convertView.findViewWithTag(key);
            if(textView!=null){
                textView.setText(value);
            }
        }
        return convertView;
    }
}
