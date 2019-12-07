package com.qiwu.widget.cubepage.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.qiwu.widget.base.viewpager.PagerAdapter;
import com.qiwu.widget.cubepage.base.AbsCubeView;
import com.qiwu.widget.cubepage.viewholder.CubeViewHolder;

/**
 * Author: qi.wu
 * Date: 2019-10-10
 */
public class CubeViewPagerAdapter<V extends AbsCubeView> extends PagerAdapter {

    private CubeViewHolder<V> holder;

    public CubeViewPagerAdapter(CubeViewHolder<V> holder) {
        this.holder = holder;
    }

    @Override
    public int getCount() {
        return this.holder.getSize();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        V v = holder.get(position);
        if (v == null) {
            // 需要创建
            v = holder.instantiate(position);
        }
        holder.onPageInstantiated(v, position);

        if (v.getView() != null && v.getView().getParent() != null) {
            container.removeView(v.getView());
        }
        container.addView(v.getView());
        return v.getView();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) { }
}
