package com.example.gdong.myapplication;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.gdong.myapplication.mode.Company;
import com.example.gdong.myapplication.mode.Order;
import com.vondear.rxtools.RxUtils;

import org.csii.yeeframe.utils.YeeUtilsSystem;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

import static com.example.gdong.myapplication.util.ContentUriUtil.getPath;

/**
 * Created by Gdong on 2017/10/17.
 */

public class MyApplication extends Application{
    public static final int REQUESTCODE_ADD    = 100;
    public static final int REQUESTCODE_UPDATE    = 101;
    public static final int REQUESTCODE_select    = 102;
    public static final int REQUESTCODE_ADD_SUCCESS    = 200;

    public static List<Company> companyArrayList ;
    public static ArrayList<Order> orderArrayList;
    @Override
    public void onCreate() {
        super.onCreate();

        RxUtils.init(this);
        Bmob.initialize(this, "1331f3743c9d29b1328129111b1e32f6");
      //  Bmob.initialize(this, "6db775fd0cd7f4a12af076d1e77e600d");

        initData();

    }

    private void initData() {
        companyArrayList = new ArrayList<Company>();

        BmobQuery<Company> query = new BmobQuery<Company>();
        query.setLimit(50);
        query.findObjects(new FindListener<Company>() {
            @Override
            public void done(List<Company> list, BmobException e) {
                if(e==null){
                    companyArrayList=list;
                }
            }
        });




    }


}
