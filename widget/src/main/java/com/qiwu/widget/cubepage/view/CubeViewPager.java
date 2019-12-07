package com.qiwu.widget.cubepage.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiwu.widget.cubepage.adapter.CubeViewPagerAdapter;
import com.qiwu.widget.cubepage.base.AbsCubeView;
import com.qiwu.widget.cubepage.viewholder.CubeViewHolder;

/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public class CubeViewPager<V extends AbsCubeView> extends BaseCubePager<V> {
    public CubeViewPager(@NonNull Context context) {
        super(context);
    }

    public CubeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(CubeViewHolder holder) {
        holder.setScrollStateListener(this::isScrolling);
        CubeViewPagerAdapter<V> adapter = new CubeViewPagerAdapter<V>(holder);
        super.init(adapter, holder);
    }
}
