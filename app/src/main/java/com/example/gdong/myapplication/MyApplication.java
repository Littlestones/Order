package com.example.gdong.myapplication;

import android.app.Application;

import com.example.gdong.myapplication.mode.Company;
import com.example.gdong.myapplication.mode.Order;
import com.vondear.rxtools.RxUtils;

import org.csii.yeeframe.utils.YeeUtilsSystem;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Gdong on 2017/10/17.
 */

public class MyApplication extends Application{
    public static ArrayList<Company> companyArrayList ;
    public static ArrayList<Order> orderArrayList;
    @Override
    public void onCreate() {
        super.onCreate();
        RxUtils.init(this);
        initData();

    }

    private void initData() {
        companyArrayList = new ArrayList<Company>();

        for (int i = 'A'; i < 'z'; i++)
        {
            Company company =new Company("" + (char) i,"" + (char) i,"" + (char) i);
            companyArrayList.add(company);
        }

        orderArrayList = new ArrayList<Order>();

        for (int i = '1'; i < '9'; i++)
        {
            ArrayList<String> imageslist = new ArrayList<String>();
            Order order =new Order("" + (char) i,imageslist,"" + (char) i,"" + (char) i,"" + (char) i,"" + (char) i);
            orderArrayList.add(order);
        }


    }

}
