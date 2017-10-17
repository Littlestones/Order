package com.example.gdong.myapplication.ui;

import android.content.Context;

/**
 * Created by Gdong on 2017/10/17.
 */

public class MyDialog {
    public MyDialog(Context context,String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(content);
        builder.setNegativeButton("确定", null);
        builder.show();
    }


}
