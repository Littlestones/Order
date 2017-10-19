package com.example.gdong.myapplication;

import android.app.Application;

import com.example.gdong.myapplication.mode.Company;
import com.example.gdong.myapplication.mode.Order;
import com.vondear.rxtools.RxUtils;

import org.csii.yeeframe.utils.YeeUtilsSystem;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gdong on 2017/10/17.
 */

public class MyApplication extends Application{
    public static final int REQUESTCODE_ADD    = 100;
    public static final int REQUESTCODE_UPDATE    = 101;
    public static final int REQUESTCODE_select    = 102;
    public static final int REQUESTCODE_ADD_SUCCESS    = 200;



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
            Company company =new Company("" + (char) i,"" + (char) i,"");
            companyArrayList.add(company);
        }

        orderArrayList = new ArrayList<Order>();

        for (int i = 'A'; i < 'z'; i++)
        {
            HashMap<Integer,String> imageslist = new HashMap<Integer,String>();
            Order order =new Order("" + (char) i,imageslist,"" + (char) i,"" + (char) i,"" + (char) i,"" + (char) i);
            orderArrayList.add(order);
        }


    }

}
