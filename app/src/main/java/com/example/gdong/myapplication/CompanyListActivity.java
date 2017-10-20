package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.gdong.myapplication.mode.Company;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD;
import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD_SUCCESS;
import static com.example.gdong.myapplication.MyApplication.companyArrayList;

/**
 * Created by Gdong on 2017/8/28.
 */

public class CompanyListActivity extends Activity {
    private ImageView iv_add;
    private SearchView iv_search;
    private TextView detail_title;
    private RecyclerView mRecyclerView;
    public List<Company> companies =companyArrayList ;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        iv_add= (ImageView) findViewById(R.id.add);
        iv_search= (SearchView) findViewById(R.id.search);
        detail_title= (TextView) findViewById(R.id.detail_title);
        initData();
        detail_title.setText("公司列表");
        mRecyclerView = (RecyclerView) findViewById(R.id.review);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
    }

    @Override
    protected void onResume() {
        initData();
        super.onResume();
    }

    protected void initData()
    {
        BmobQuery<Company> query = new BmobQuery<Company>();
        query.setLimit(50);
        query.findObjects(new FindListener<Company>() {
            @Override
            public void done(List<Company> list, BmobException e) {
                if(e==null){
                    companies=list;
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(CompanyListActivity.this,NewCompany.class);
                startActivityForResult(intent,REQUESTCODE_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==REQUESTCODE_ADD_SUCCESS&&requestCode==REQUESTCODE_ADD){
            initData();
        }
        mAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    CompanyListActivity.this).inflate(R.layout.activity_result_item, parent,
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
                    Intent intent =new Intent(CompanyListActivity.this,OrderListActivity.class);
                    intent.putExtra("ObjectId",companies.get(position).getObjectId());
                    startActivity(intent);
                }
            });
                Log.i("123",companies.get(position).getIcon());
            if(!(companies.get(position).getIcon()==null||companies.get(position).getIcon()=="")){
                Glide.with(CompanyListActivity.this)
                        .load(Uri.parse(companies.get(position).getIcon()))
                        .fitCenter()
                        .into(new GlideDrawableImageViewTarget(holder.iv1) {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                            }
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                            }
                        });

            }else {
                Glide.with(CompanyListActivity.this)
                        .load(R.drawable.company)
                        .fitCenter()
                        .into(new GlideDrawableImageViewTarget(holder.iv1) {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                            }
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                            }
                        });

            }
            holder.tv1.setText(companies.get(position).getName());
        }

        @Override
        public int getItemCount()
        {
            return companies.size();
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
