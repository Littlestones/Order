package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.gdong.myapplication.mode.Company;
import com.example.gdong.myapplication.mode.Order;

import java.util.ArrayList;
import java.util.List;

import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD;
import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_UPDATE;

/**
 * Created by Gdong on 2017/8/28.
 */

public class OrderListActivity extends Activity{




    ImageView iv_add;
    SearchView iv_search;
    private RecyclerView mRecyclerView;
    private ArrayList<Order> mDatas = MyApplication.orderArrayList;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        iv_add= (ImageView) findViewById(R.id.add);
        iv_search= (SearchView) findViewById(R.id.search);

        mRecyclerView = (RecyclerView) findViewById(R.id.review);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());


    }

    @Override
    protected void onResume() {
        mDatas = MyApplication.orderArrayList;

        mAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(OrderListActivity.this,DetailActivity.class);
                intent.putExtra("data",new Order(null,null,"","","",null));
                intent.putExtra("type",REQUESTCODE_ADD);
                startActivityForResult(intent,REQUESTCODE_ADD);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    OrderListActivity.this).inflate(R.layout.activity_result_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
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
                    Intent intent =new Intent(OrderListActivity.this,DetailActivity.class);
                    intent.putExtra("index",position);
                    intent.putExtra("data",mDatas.get(position));
                    intent.putExtra("type",REQUESTCODE_UPDATE);
                    startActivityForResult(intent,REQUESTCODE_UPDATE);
                }
            });
            holder.tv1.setText(mDatas.get(position).getId());
            holder.iv1.setImageResource(R.drawable.prompt);

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
            ImageView iv1;


            public MyViewHolder(View view)
            {
                super(view);
                linearLayout= (LinearLayout) view.findViewById(R.id.item);
                tv1 = (TextView) view.findViewById(R.id.itv1);
                iv1 = (ImageView) view.findViewById(R.id.iv1);
            }
        }
    }

    private void deletetitem() {
    }
}
