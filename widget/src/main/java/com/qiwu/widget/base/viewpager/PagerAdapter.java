package com.qiwu.widget.base.viewpager;

import android.database.DataSetObserver;


/**
 * Author: qi.wu
 * Date: 2019-11-19
 */
public abstract class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    private DataSetObserver mViewPagerObserver2;


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        synchronized (this) {
            if (mViewPagerObserver2 != null) {
                mViewPagerObserver2.onChanged();
            }
        }
    }

    void setViewPagerObserver(DataSetObserver observer) {
        synchronized (this) {
            mViewPagerObserver2 = observer;
        }
    }
}
