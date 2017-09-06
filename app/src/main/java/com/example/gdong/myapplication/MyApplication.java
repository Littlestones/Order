package com.example.gdong.myapplication;

import android.app.Application;

import com.vondear.rxtools.RxUtils;

/**
 * Created by Gdong on 2017/8/23.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxUtils.init(this);
    }
}
