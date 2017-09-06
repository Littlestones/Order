package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gdong on 2017/8/28.
 */

public class ResultActivity extends Activity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.review);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());mRecyclerView.addItemDecoration(new DividerItemDecoration(
           this, DividerItemDecoration.HORIZONTAL));


    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    ResultActivity.this).inflate(R.layout.activity_result_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(ResultActivity.this,DetailActivity.class);
                    startActivity(intent);
                }
            });
            holder.tv1.setText(mDatas.get(position));
            holder.tv2.setText(mDatas.get(position));
            holder.tv3.setText(mDatas.get(position));
            holder.tv4.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }



        class MyViewHolder extends RecyclerView.ViewHolder
        {
            LinearLayout linearLayout;
            TextView tv1;
            TextView tv2;
            TextView tv3;
            TextView tv4;

            public MyViewHolder(View view)
            {
                super(view);
                linearLayout= (LinearLayout) view.findViewById(R.id.item);
                tv1 = (TextView) view.findViewById(R.id.itv1);
                tv2 = (TextView) view.findViewById(R.id.itv2);
                tv3 = (TextView) view.findViewById(R.id.itv3);
                tv4 = (TextView) view.findViewById(R.id.itv4);

            }
        }
    }

    private void deletetitem() {
    }
}
