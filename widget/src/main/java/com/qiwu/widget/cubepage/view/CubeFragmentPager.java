package com.qiwu.widget.cubepage.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.qiwu.widget.cubepage.adapter.CubeFragmentPagerAdapter;
import com.qiwu.widget.cubepage.base.AbsCubeFragment;
import com.qiwu.widget.cubepage.viewholder.CubeFragmentHolder;

/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public class CubeFragmentPager<F extends AbsCubeFragment> extends BaseCubePager<F> {

    public CubeFragmentPager(@NonNull Context context) {
        this(context, null);
    }

    public CubeFragmentPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(@NonNull FragmentManager fragmentManager, @NonNull CubeFragmentHolder holder) {
        holder.setScrollStateListener(CubeFragmentPager.this::isScrolling);
        CubeFragmentPagerAdapter<F> adapter = new CubeFragmentPagerAdapter<F>(fragmentManager, holder);
        super.init(adapter, holder);
    }
}
