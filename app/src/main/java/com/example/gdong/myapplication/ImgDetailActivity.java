package com.example.gdong.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.gdong.myapplication.ui.HackyViewPager;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/**
 * Created by Gdong on 2017/9/26.
 */

public class ImgDetailActivity extends Activity {

    private static final String STATE_POSITION = "STATE_POSITION";
    private ArrayList<String> image_urls;
    private int image_index;
    private LinearLayout ll_indicator;
    private ViewPager mPager;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_detail);
        mPager = (HackyViewPager) findViewById(R.id.pager);
        image_urls = getIntent().getStringArrayListExtra("image_urls");
        image_index = getIntent().getIntExtra("image_index",0);
        mPager.setAdapter(new SamplePagerAdapter());
        ll_indicator = (LinearLayout) findViewById(R.id.ll_indicator);
        if (image_urls.size() > 1) {
            addIndicator(image_urls, ll_indicator);
        }
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                setImageBackground(arg0);
            }

        });
        if (savedInstanceState != null) {
            image_index = savedInstanceState.getInt(STATE_POSITION);
        }
        mPager.setCurrentItem(image_index);

    }
    class SamplePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return image_urls.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            String mImageUrl = image_urls.get(position);
            Log.i("1111",mImageUrl);
            Glide.with(ImgDetailActivity.this)
                    .load(mImageUrl)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fitCenter()
                    .into(new GlideDrawableImageViewTarget(photoView) {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                        }

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                        }
                    });
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;

        }

    }private ArrayList<ImageView> indicatorList = new ArrayList<>();

    private void addIndicator(ArrayList<String> image_urls, LinearLayout ll_indicator) {
        int paddingDp = dip2px(this, 4);
        int pointsize = dip2px(this, 10);
        for (int i = 0; i < image_urls.size(); i++) {
            ImageView child = new ImageView(this);
            child.setImageResource(R.drawable.point_grey);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointsize,pointsize);
            child.setLayoutParams(params);
            child.setPadding(paddingDp, 0, paddingDp, 0);
            indicatorList.add(child);
            ll_indicator.addView(child);
        }
        setPointLight(0);
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    private void setPointLight(int position) {

        if (indicatorList.size() < 2) {
            return;
        }

        for (ImageView imageView : indicatorList) {
            imageView.setImageResource(R.drawable.point_grey);
        }

        ImageView imageView = indicatorList.get(position);
        imageView.setImageResource(R.drawable.point_white);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < indicatorList.size(); i++) {
            if (i == selectItems) {
                indicatorList.get(i).setImageResource(R.drawable.point_white);
            } else {
                indicatorList.get(i).setImageResource(R.drawable.point_grey);
            }
        }
    }
}
