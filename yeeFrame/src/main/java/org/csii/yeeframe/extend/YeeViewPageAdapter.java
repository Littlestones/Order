package org.csii.yeeframe.extend;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DuMing on 2015/11/06.
 *      ViewPager适配器
 *      View放在ListView中传入该适配器
 */
public class YeeViewPageAdapter extends PagerAdapter {

    private List<View> viewList;

    public YeeViewPageAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }
}
