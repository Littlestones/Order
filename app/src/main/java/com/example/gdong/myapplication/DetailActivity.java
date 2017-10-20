package com.example.gdong.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.gdong.myapplication.extend.YeeDialog;
import com.example.gdong.myapplication.mode.Order;
import com.example.gdong.myapplication.ui.MyDialog;
import com.vondear.rxtools.RxCameraUtils;
import com.vondear.rxtools.view.dialog.RxDialog;
import com.vondear.rxtools.view.dialog.RxDialogWheelYearMonthDay;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD;
import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_UPDATE;
import static com.example.gdong.myapplication.MyApplication.orderArrayList;

/**
 * Created by Gdong on 2017/8/28.
 */

public class DetailActivity extends Activity {
    private TextView xiadan;
    private TextView jiaoqi;
    private TextView queren;
    private TextView detail_id;
    private EditText detail_remark;
    private HashMap<Integer,String> image_urls;
    private int image_index=0;
    private Bundle mbundle;
    private Calendar cal;
    private int year,month,day;
    private File currentFile;
    private ImageView currentImageView;
    private Intent intent;
    private String companyobjectid;
    private String orderobjectid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        image_urls =new HashMap<Integer,String>();
        companyobjectid=getIntent().getStringExtra("companyid");
        orderobjectid=getIntent().getStringExtra("ObjectId");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getDate();
        xiadan= (TextView) findViewById(R.id.detail_xiadan);
        jiaoqi= (TextView) findViewById(R.id.detail_jiaoqi);
        queren= (TextView) findViewById(R.id.detail_queren);
        detail_id= (TextView) findViewById(R.id.detail_id);
        detail_remark= (EditText) findViewById(R.id.detail_remark);
        initData();
    }



    public void initData(){

        intent= getIntent();

        Order order = (Order) intent.getSerializableExtra("data");
        Log.i("order内容",order.toString());
        if(intent.getIntExtra("type",REQUESTCODE_ADD)==REQUESTCODE_ADD){
            xiadan.setText("");
            jiaoqi.setText("");
            queren.setText("");
            detail_id.setText("");
            detail_remark.setText("");

        }else {
            image_urls=order.getImage_urls();
            for(int i=R.id.img1;i<R.id.img1+8;i++){
                if(order.getImage_urls().containsKey(i)){
                   ImageView iv= (ImageView)findViewById(i);
                   iv.setImageURI(Uri.parse(order.getImage_urls().get(i)));
                   iv.setTag(i,"see");
                }
            }
            xiadan.setText(order.getDetail_xiadan());
            jiaoqi.setText(order.getDetail_jiaoqi());
            queren.setText(order.getDetail_queren());
            detail_id.setText(order.getId());
            detail_remark.setText(order.getRemark());
        }





    }
    public void click(View view){
        int id= view.getId();
        if(view instanceof ImageView){
            if(view.getTag().toString().trim().equals("add")){
            currentImageView = (ImageView) view;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            currentFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/test/" + System.currentTimeMillis() + ".jpg");
            currentFile.getParentFile().mkdirs();
            Uri uri = Uri.fromFile(currentFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 100);
            image_index=view.getId()-R.id.img1+1;;

            }else if (view.getTag().toString().trim().equals("see")){
                Intent intent = new Intent();
                mbundle= new Bundle();
                mbundle.putSerializable("image_urls",(Serializable)image_urls);
                mbundle.putInt("image_index",image_index);
                intent.setClass(DetailActivity.this, ImgDetailActivity.class);
                intent.putExtras(mbundle);
                startActivity(intent);
            }else {
                ViewGroup vg =(ViewGroup)view.getParent().getParent();
                ImageView  imgview = (ImageView) vg.getChildAt(0);
                imgview.setImageResource(R.drawable.a1);
                imgview.setTag(id,"add");
            }

        }
        switch (id){
            case R.id.detail_cancel:
                finish();
                break;

            case R.id.detail_save:
                if(detail_id.getText().toString().trim().equals("")){
                    new MyDialog(this,"请输入订单编号");
                    return;
            }
                Order order = new Order(detail_id.getText().toString().trim(),
                        image_urls,
                        xiadan.getText().toString().trim(),
                        jiaoqi.getText().toString().trim(),
                        queren.getText().toString().trim(),
                        detail_remark.getText().toString().trim(),
                        companyobjectid
                        );
                order.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        setResult(200,intent);
                        finish();

                    }
                });

                break;
            case R.id.detail_xiadan_clickarea:
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        xiadan.setText("下单日期："+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(DetailActivity.this, 0,listener,year,month,day);
                dialog.show();
                break;
            case R.id.detail_jiaoqi_clickarea:
                DatePickerDialog.OnDateSetListener listener2=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        jiaoqi.setText("交付日期："+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog2=new DatePickerDialog(DetailActivity.this, 0,listener2,year,month,day);
                dialog2.show();
                break;
            case R.id.detail_queren_clickarea:
                DatePickerDialog.OnDateSetListener listener3=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        queren.setText("确认日期："+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog3=new DatePickerDialog(DetailActivity.this, 0,listener3,year,month,day);
                dialog3.show();
                break;



        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:
                    currentImageView.setImageURI(Uri.fromFile(currentFile));
                    image_urls.put(currentImageView.getId(),currentFile.toString());
                    currentImageView.setTag(currentImageView.getId(),"see");
                    break;


            }
        }
    }
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }
}
