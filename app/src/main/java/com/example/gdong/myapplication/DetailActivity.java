package com.example.gdong.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD;
import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_UPDATE;
import static com.example.gdong.myapplication.MyApplication.orderArrayList;
import static com.example.gdong.myapplication.util.ContentUriUtil.getPath;

/**
 * Created by Gdong on 2017/8/28.
 */

public class DetailActivity extends Activity {
    List<Uri> mSelected;
    private TextView xiadan;
    private TextView jiaoqi;
    private TextView queren;
    private TextView detail_id;
    private EditText detail_remark;
    private List<String> image_urls;
    private List<String> image_urls_cdn;
    private int image_index=0;
    private Bundle mbundle;
    private Calendar cal;
    private int year,month,day;
    private ImageView currentImageView;
    private Intent intent;
    private String companyobjectid;
    private String orderobjectid;
    private Button save;
    private int type;
    private int img_upload=0;//等待上传的图片数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        image_urls=new ArrayList();
        image_urls_cdn=new ArrayList();
        for(int i=0;i<8;i++){
            image_urls_cdn.add("");
            image_urls.add("");
        }
        companyobjectid=getIntent().getExtras().getString("companyid");
        orderobjectid=getIntent().getExtras().getString("ObjectId");
        type=getIntent().getExtras().getInt("type");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getDate();
        xiadan= (TextView) findViewById(R.id.detail_xiadan);
        jiaoqi= (TextView) findViewById(R.id.detail_jiaoqi);
        queren= (TextView) findViewById(R.id.detail_queren);
        detail_id= (TextView) findViewById(R.id.detail_id);
        detail_remark= (EditText) findViewById(R.id.detail_remark);
        save= (Button) findViewById(R.id.detail_save);
        initData();
    }


    public void initData(){
        intent= getIntent();
        if(type==REQUESTCODE_ADD){
            xiadan.setText("");
            jiaoqi.setText("");
            queren.setText("");
            detail_id.setText("");
            detail_remark.setText("");
            for(int i=R.id.img1;i<R.id.img1+8;i++){
                ImageView iv= (ImageView)findViewById(i);
                iv.setImageResource(R.drawable.a1);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseimg(v);
                    }
                });

            }


        }else {
            BmobQuery<Order> bmobQuery = new BmobQuery<Order>();
            bmobQuery.getObject(orderobjectid, new QueryListener<Order>() {
                @Override
                public void done(Order object,BmobException e) {
                    if(e==null){
                        Order order=object;
                        image_urls=order.getImage_urls();
                        for(int i=R.id.img1;i<R.id.img1+8;i++){
                            if(!(image_urls.get(i-R.id.img1)==null||image_urls.get(i-R.id.img1).equals("")))
                            {
                                ImageView iv= (ImageView)findViewById(i);
                                Glide.with(DetailActivity.this)
                                        .load(order.getImage_urls().get(i-R.id.img1))
                                        .fitCenter()
                                        .into(new GlideDrawableImageViewTarget(iv) {
                                            @Override
                                            public void onLoadStarted(Drawable placeholder) {
                                                super.onLoadStarted(placeholder);
                                            }
                                            @Override
                                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                                super.onResourceReady(resource, animation);
                                            }
                                        });
                                iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        seeimg(v);
                                    }
                                });

                                //iv.setTag(i,"see");
                            }
                            else {
                                ImageView iv= (ImageView)findViewById(i);
                                iv.setImageResource(R.drawable.a1);
                                iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        chooseimg(v);
                                    }
                                });
                              //  iv.setTag(i,"add");
                            }
                        }
                        xiadan.setText(order.getDetail_xiadan());
                        jiaoqi.setText(order.getDetail_jiaoqi());
                        queren.setText(order.getDetail_queren());
                        detail_id.setText(order.getId());
                        detail_remark.setText(order.getRemark());
                    }else{
                    }
                }
            });

        }





    }
    public void click(View view){
        int id= view.getId();
        if(view instanceof ImageView){
            if(!(view.getId()<=R.id.img8&&view.getId()>=R.id.img1)){
                ViewGroup vg =(ViewGroup)view.getParent().getParent();
                ImageView  imgview = (ImageView) vg.getChildAt(0);
                imgview.setImageResource(R.drawable.a1);
                if(image_urls.get(imgview.getId()-R.id.img1).startsWith("http")){
                    BmobFile file = new BmobFile();
                    file.setUrl(image_urls.get(imgview.getId()-R.id.img1));
                    file.delete();
                }

                image_urls.set(imgview.getId()-R.id.img1,"");
                imgview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseimg(v);
                    }
                });
            }

        }
        switch (id){
            case R.id.detail_cancel:
                finish();
                break;

            case R.id.detail_save:
                save.setClickable(false);
                if(detail_id.getText().toString().trim().equals("")){
                    new MyDialog(this,"请输入订单编号");
                    save.setClickable(true);
                    return;
            }

                BmobQuery<Order> bmobQuery = new BmobQuery<Order>();
                bmobQuery.addWhereEqualTo("id", detail_id.getText().toString().trim());
                bmobQuery.findObjects(new FindListener<Order>() {
                    @Override
                    public void done(List<Order> list, BmobException e) {
                        if (e == null&&list.size()!=0) {
                            if(!list.get(0).getObjectId().equals(orderobjectid)){
                                new MyDialog(DetailActivity.this, "已经存在相同编号的订单，请重新输入。");
                                save.setClickable(true);
                                return;
                            }
                            upLoadImgList();
                        } else {
                            upLoadImgList();
                        }
                    }
                });


                break;
            case R.id.detail_xiadan_clickarea:
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        xiadan.setText(+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(DetailActivity.this, 0,listener,year,month,day);
                dialog.show();
                break;
            case R.id.detail_jiaoqi_clickarea:
                DatePickerDialog.OnDateSetListener listener2=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        jiaoqi.setText(+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog2=new DatePickerDialog(DetailActivity.this, 0,listener2,year,month,day);
                dialog2.show();
                break;
            case R.id.detail_queren_clickarea:
                DatePickerDialog.OnDateSetListener listener3=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        queren.setText(+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
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
                    mSelected = Matisse.obtainResult(data);
                    currentImageView.setImageURI(mSelected.get(0));
                    image_urls.set(currentImageView.getId()-R.id.img1,mSelected.get(0).toString());
                    currentImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            seeimg(v);
                        }
                    });
                 //   currentImageView.setTag(currentImageView.getId(),"see");
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
    private void upLoadImgList(){

        final List<String> truepath=new ArrayList();;
        for (int i=0;i<8;i++){
            truepath.add(getPath(this, Uri.parse(image_urls.get(i))));
        }
        for( int i = 0;i<8;i++){
            if (!(image_urls.get(i).equals("")||image_urls.get(i)==null||image_urls.get(i).startsWith("http"))) {
                img_upload++;
                final BmobFile bmobFile = new BmobFile(new File(getPath(this, Uri.parse(image_urls.get(i)))));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            for (int k=0;k<8;k++){
                                if(bmobFile.getLocalFile().toURI().toString().split("file:")[1].equals(truepath.get(k))){
                                image_urls_cdn.set(k,bmobFile.getUrl());
                                }
                            }
                            img_upload--;
                            if (img_upload ==0){
                                upLoadOrder();
                            }
                        } else {
                            Log.i("info","上传失败"+e.toString());
                            save.setClickable(true);

                        }
                    }
                });
            }else{
                image_urls_cdn.set(i,image_urls.get(i));
                if (i==7&&img_upload==0){
                    upLoadOrder();
                }
            }
        }
    }
    private void upLoadOrder(){

        Order order = new Order(detail_id.getText().toString().trim(),
                image_urls_cdn,
                xiadan.getText().toString().trim(),
                jiaoqi.getText().toString().trim(),
                queren.getText().toString().trim(),
                detail_remark.getText().toString().trim(),
                companyobjectid
        );
        if(type==REQUESTCODE_ADD){
            order.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        setResult(200,intent);
                        finish();

                    }
                    else {
                        Log.i("info","上传失败"+e.toString());
                        save.setClickable(true);

                    }


                }
            });
        }else {
            order.update(orderobjectid, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        setResult(200,intent);
                        finish();

                    }
                    else {
                        Log.i("info","上传失败"+e.toString());
                        save.setClickable(true);

                    }
                }
            });

        }
    }
    private void chooseimg(View view){
        currentImageView= (ImageView) view;
        Matisse.from(DetailActivity.this)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(100); // 设置作为标记的请求码
        image_index=view.getId()-R.id.img1+1;;
    }
    private void seeimg(View view){
        Intent intent = new Intent();
        mbundle= new Bundle();
        mbundle.putSerializable("image_urls",(Serializable)image_urls);
        mbundle.putInt("image_index",image_index);
        intent.setClass(DetailActivity.this, ImgDetailActivity.class);
        intent.putExtras(mbundle);
        startActivity(intent);
    }


}
