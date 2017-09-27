package com.example.gdong.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gdong.myapplication.extend.YeeDialog;
import com.vondear.rxtools.RxCameraUtils;
import com.vondear.rxtools.view.dialog.RxDialog;
import com.vondear.rxtools.view.dialog.RxDialogWheelYearMonthDay;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Gdong on 2017/8/28.
 */

public class DetailActivity extends Activity {
    TextView xiadan;
    TextView jiaoqi;
    TextView queren;
    private ArrayList<String> image_urls;
    private int image_index=0;
    private Bundle mbundle;
    private Calendar cal;
    private int year,month,day;
    private File currentFile;
    private ImageView currentImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        image_urls =new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getDate();
        xiadan= (TextView) findViewById(R.id.detail_xiadan);
        jiaoqi= (TextView) findViewById(R.id.detail_jiaoqi);
        queren= (TextView) findViewById(R.id.detail_queren);
    }
    public void click(View view){
        int id= view.getId();
        if(view instanceof ImageView){
            currentImageView = (ImageView) view;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            currentFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/test/" + System.currentTimeMillis() + ".jpg");
            currentFile.getParentFile().mkdirs();
            Uri uri = Uri.fromFile(currentFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 100);
            image_index=0;

        }
        switch (id){
            case R.id.add1:

                break;
            case R.id.detail_save:
                break;
            case R.id.detail_xiadan:
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        xiadan.setText("下单日期："+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(DetailActivity.this, 0,listener,year,month,day);
                dialog.show();
                break;
            case R.id.detail_jiaoqi:
                DatePickerDialog.OnDateSetListener listener2=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        jiaoqi.setText("交付日期："+year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog2=new DatePickerDialog(DetailActivity.this, 0,listener2,year,month,day);
                dialog2.show();
                break;
            case R.id.detail_queren:
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
                    image_urls.add(currentFile.toString());
                    currentImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                             mbundle= new Bundle();
                             mbundle.putStringArrayList("image_urls",image_urls);
                             mbundle.putInt("image_index",image_index);
                             intent.setClass(DetailActivity.this, ImgDetailActivity.class);
                             intent.putExtras(mbundle);
                             startActivity(intent);
                        }
                    });
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
