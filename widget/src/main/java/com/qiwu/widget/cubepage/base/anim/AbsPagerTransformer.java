package com.qiwu.widget.cubepage.base.anim;

import android.view.View;

import androidx.annotation.NonNull;

import com.qiwu.widget.base.viewpager.ViewPager;

/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public abstract class AbsPagerTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1.0f) {
            // [-Infinity,-1)
            handleInvisiblePage(page, position);
        } else if (position <= 0.0f) {
            // [-1,0]
            handleLeftPage(page, position);
        } else if (position <= 1.0f) {
            // (0,1]
            handleRightPage(page, position);
        } else {
            // (1,+Infinity]
            handleInvisiblePage(page, position);
        }
    }

    protected abstract void handleInvisiblePage(View page, float position);

    protected abstract void handleLeftPage(View page, float position);

    protected abstract void handleRightPage(View page, float position);
}
