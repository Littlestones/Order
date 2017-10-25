package com.example.gdong.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.gdong.myapplication.mode.Order;
import com.example.gdong.myapplication.ui.MyDialog;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
//            @Override
//            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
//
//            }
//        });
        button= (Button) findViewById(R.id.main_select);
        editText = (EditText) findViewById(R.id.main_editText);
        linearLayout= (LinearLayout) findViewById(R.id.content);
        linearLayout.setBackgroundResource(R.drawable.main_background);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString()!=null&&editText.getText().toString().equals("123456789") ){
                    Intent intent= new Intent(MainActivity.this,CompanyListActivity.class);
                    startActivity(intent);
                }
                else {
                    button.setClickable(false);
                    search(editText.getText().toString().trim());
                }

            }
        });



    }

    private void search(String id) {
        BmobQuery<Order> bmobQuery = new BmobQuery<Order>();
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.findObjects(new FindListener<Order>() {

            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {
                    Intent intent = new Intent(MainActivity.this, CusDetailActivity.class);
                    intent.putExtra("data", (Serializable) list.get(0));
                    startActivity(intent);
                } else {
                    new MyDialog(MainActivity.this, "没有找到对应的订单,请输入正确的编号");
                }
                button.setClickable(true);

            }

        });
    }


}
