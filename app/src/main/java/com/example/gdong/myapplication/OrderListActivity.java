package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.gdong.myapplication.mode.Company;
import com.example.gdong.myapplication.mode.Order;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_ADD;
import static com.example.gdong.myapplication.MyApplication.REQUESTCODE_UPDATE;

/**
 * Created by Gdong on 2017/8/28.
 */

public class OrderListActivity extends Activity{
    private ImageView iv_add;
    private SearchView iv_search;
    private RecyclerView mRecyclerView;
    private List<Order> mDatas = null;
    private List<Order> searchresult;
    private HomeAdapter mAdapter;
    private String companyobjectid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        companyobjectid=getIntent().getStringExtra("ObjectId");
        initData(companyobjectid);
        setContentView(R.layout.activity_result);
        iv_add= (ImageView) findViewById(R.id.add);
        iv_search= (SearchView) findViewById(R.id.search);
        iv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.trim().equals("")) {
                    searchresult = new ArrayList<>();
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).getId().contains(newText) || mDatas.get(i).getRemark().contains(newText)) {
                            searchresult.add(mDatas.get(i));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }else {
                    searchresult=mDatas;
                    mAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.review);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());


    }

    public  void initData(String companyobjectid){
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.setLimit(50);
        query.addWhereEqualTo("companyid", companyobjectid);
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if(e==null){
                    mDatas=list;
                    searchresult=mDatas;
                    mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
                    mAdapter.notifyDataSetChanged();
                }else {
                    mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
                    mAdapter.notifyDataSetChanged();

                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatas = null;
        searchresult = null;
    }

    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(OrderListActivity.this,DetailActivity.class);
                Bundle  bundle =new Bundle();
                bundle.putString("companyid",companyobjectid);
                bundle.putInt("type",REQUESTCODE_ADD);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUESTCODE_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        initData(companyobjectid);
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
            holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(OrderListActivity.this);
                    builder.setTitle("警告");
                    builder.setMessage("确定要删除该订单信息？（操作不可撤销）");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Order order = new Order();
                            order.setObjectId(searchresult.get(position).getObjectId());
                            order.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Log.i("bmob","成功");
                                    }else{
                                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    }
                                }
                            });
                            searchresult.remove(position);
                            notifyItemRemoved(position);
                        }
                    });
                    builder.setNeutralButton("取消",null);
                    builder.show();
                    return false;
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(OrderListActivity.this,DetailActivity.class);
                    Bundle  bundle =new Bundle();
                    bundle.putString("companyid",companyobjectid);
                    bundle.putString("ObjectId",searchresult.get(position).getObjectId());
                    bundle.putInt("type",REQUESTCODE_UPDATE);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,REQUESTCODE_UPDATE);
                }
            });
            holder.tv1.setText(searchresult.get(position).getId());
            boolean flag=false;
            for (int i=7;i>=0;i--){
                if(!(searchresult.get(position).getImage_urls().get(i)==null||searchresult.get(position).getImage_urls().get(i).length()==0)){

                    Glide.with(OrderListActivity.this)
                            .load(searchresult.get(position).getImage_urls().get(i))
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
                    flag=true;
                    break;
                }
            }
            if ( flag==false){
            holder.iv1.setImageResource(R.drawable.prompt);
             }

        }

        @Override
        public int getItemCount()
        {
            if (searchresult==null)
            {
                return 0;
            }else {
                return searchresult.size();
            }
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
