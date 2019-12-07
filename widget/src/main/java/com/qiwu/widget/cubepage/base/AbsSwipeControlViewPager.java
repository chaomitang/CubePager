package com.qiwu.widget.cubepage.base;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.qiwu.widget.base.viewpager.SwipeDirection;
import com.qiwu.widget.base.viewpager.ViewPager;


/**
 * Author: qi.wu
 * Date: 2019-10-31
 */
public abstract class AbsSwipeControlViewPager extends ViewPager {

    private float initialXValue;
    private SwipeDirection swipeDirection;
    private Rect mSwipeForbiddenRect;

    public AbsSwipeControlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeForbiddenRect(int left, int top, int right, int bottom) {
        mSwipeForbiddenRect = new Rect(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && mSwipeForbiddenRect != null) {
            if (event.getX() <= mSwipeForbiddenRect.right && event.getX() >= mSwipeForbiddenRect.left &&
                    event.getY() <= mSwipeForbiddenRect.bottom && event.getY() >= mSwipeForbiddenRect.top) {
                return false;
            }
        }
        return super.onTouchEvent(event);
    }
}