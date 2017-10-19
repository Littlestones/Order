package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.gdong.myapplication.mode.Order;

import java.util.HashMap;

/**
 * Created by Gdong on 2017/9/6.
 */

public class CusDetailActivity extends Activity{
    private TextView progress;
    private ImageView cusdetail_img;
    private TextView xiadan;
    private TextView jiaoqi;
    private TextView queren;
    private TextView detail_id;
    private TextView detail_remark;
    private HashMap<Integer,String> image_urls;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusdetail);
        xiadan= (TextView) findViewById(R.id.cusdetail_xiadan);
        jiaoqi= (TextView) findViewById(R.id.cusdetail_jiaoqi);
        queren= (TextView) findViewById(R.id.cusdetail_queren);
        detail_id= (TextView) findViewById(R.id.cusdetail_id);
        detail_remark= (TextView) findViewById(R.id.cusdetail_remark);
        cusdetail_img= (ImageView) findViewById(R.id.cusdetail_img);
        progress= (TextView) findViewById(R.id.progress);
        initData();

    }

    private void initData() {
        Intent intent =getIntent();
        Order order = (Order) intent.getSerializableExtra("data");
        xiadan.setText(order.getDetail_xiadan());
        jiaoqi.setText(order.getDetail_jiaoqi());
        queren.setText(order.getDetail_queren());
        detail_id.setText(order.getId());
        detail_remark.setText(order.getRemark());

        boolean flag=false;
        for (int i=R.id.img8;i>=R.id.img1;i--){
            if(order.getImage_urls().containsKey(i)){
                Glide.with(CusDetailActivity.this)
                        .load(order.getImage_urls().get(i))
                        .fitCenter()
                        .into(new GlideDrawableImageViewTarget(cusdetail_img) {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                            }
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                            }
                        });
                flag=true;
                break;
            }
        }
        if ( flag==false){
            progress.setText("暂无进度");
            cusdetail_img.setImageResource(R.drawable.prompt);
        }

    }
}
