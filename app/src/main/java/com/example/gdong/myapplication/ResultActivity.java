package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gdong on 2017/8/28.
 */

public class ResultActivity extends Activity {
    ImageView iv_add;
    SearchView iv_search;
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        iv_add= (ImageView) findViewById(R.id.add);
        iv_search= (SearchView) findViewById(R.id.search);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.review);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());


    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(ResultActivity.this,DetailActivity.class);
                startActivity(intent);
                break;

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


            public MyViewHolder(View view)
            {
                super(view);
                linearLayout= (LinearLayout) view.findViewById(R.id.item);
                tv1 = (TextView) view.findViewById(R.id.itv1);

            }
        }
    }

    private void deletetitem() {
    }
}
