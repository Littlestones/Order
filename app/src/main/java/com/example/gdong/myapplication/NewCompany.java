package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.gdong.myapplication.mode.Company;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;


import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD;
import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD_SUCCESS;

/**
 * Created by Gdong on 2017/10/19.
 */

public class NewCompany extends Activity {
    List<Uri> mSelected;

    private EditText companyid;
    private EditText companyname;
    private ImageView icon ;
    private Company company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_company);
        icon= (ImageView) findViewById(R.id.icon);
        companyid= (EditText) findViewById(R.id.company_id);
        companyname= (EditText) findViewById(R.id.company_title);
        super.onCreate(savedInstanceState);
    }
    public void click(View view){
        int id = view.getId();
        if (view instanceof ImageView) {
                Matisse.from(NewCompany.this)
                        .choose(MimeType.allOf()) // 选择 mime 的类型
                        .countable(true)
                        .maxSelectable(1) // 图片选择的最多数量
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                        .forResult(100); // 设置作为标记的请求码

        }

        switch (id) {
            case R.id.new_company_save:
                company= new Company(companyid.getText().toString().trim(),
                        companyname.getText().toString().trim(),
                        mSelected.get(0).toString()
                        );
                Intent intent =getIntent();
                intent.putExtra("data",company);
                setResult(REQUESTCODE_ADD_SUCCESS,intent);
                finish();
                break;
            case R.id.new_company_cancel:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Glide.with(NewCompany.this)
                    .load(mSelected.get(0))
                    .fitCenter()
                    .into(new GlideDrawableImageViewTarget(icon) {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                        }
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                        }
                    });
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }
}