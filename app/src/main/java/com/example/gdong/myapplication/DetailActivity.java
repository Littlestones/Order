package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Gdong on 2017/8/28.
 */

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
    public void click(View view){
        int id= view.getId();
        switch (id){
            case R.id.add1:
                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
                break;
        }

    }
}
