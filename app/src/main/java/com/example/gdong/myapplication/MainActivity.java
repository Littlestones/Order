package com.example.gdong.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.csii.yeeframe.YeeBitmap;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.main_select);
        editText = (EditText) findViewById(R.id.main_editText);
        linearLayout= (LinearLayout) findViewById(R.id.content);
        linearLayout.setBackgroundResource(R.drawable.main_background);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString()!=null&&editText.getText().toString().equals("123456789") ){
                    Intent intent= new Intent(MainActivity.this,ResultActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent= new Intent(MainActivity.this,CusDetailActivity.class);
                    startActivity(intent);

                }

            }
        });



    }



}
