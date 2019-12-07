package com.qiwu.widget.base.viewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Author: qi.wu
 * Date: 2019-11-19
 */
public class SpeedControlScroller extends Scroller {
    private static final int DURATION = 1000;
    private int mScrollDuration = DURATION;

    private Interpolator interpolator = t -> {
        t -= 1.0f;
        return t * t * t * t * t + 1.0f;
    };

    public SpeedControlScroller(Context context) {
        this(context, null);
    }

    public SpeedControlScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public SpeedControlScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX,startY,dx,dy, mScrollDuration);
    }

    @Override public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        //管你 ViewPager 传来什么时间，我完全不鸟你
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    public int getScrollDuration() {
        return mScrollDuration;
    }

    public void setScrollDuration(int mScrollDuration) {
        this.mScrollDuration = mScrollDuration;
    }
}
