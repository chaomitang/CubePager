package com.qiwu.widget.cubepage.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public abstract class AbsCubeView extends ViewGroup implements IPageView {

    private static final int SHOWING = 1;
    private static final int DISMISS = 2;

    private boolean selectFlag;
    private boolean showingFlag;
    private boolean dismissFlag;

    private int pageIndex;

    private int preloadPageIndex = -1; // 记录下该页面l的index并且该页面需要在create之后进行preLoad;

    private int showStatus = DISMISS;
    private ScrollStateListener scrollStateListener;

    public AbsCubeView(Context context) {
        super(context);
    }

    public AbsCubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsCubeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public void setScrollStateListener(ScrollStateListener scrollStateListener) {
        this.scrollStateListener = scrollStateListener;
    }

    @Override
    public void select(boolean select) {
        selectFlag = select;
        checkStatus();
    }

    @Override
    public void preload(int preloadPageIndex) {
        this.preloadPageIndex = preloadPageIndex;
        checkStatus();
    }

    private void checkStatus() {

        // preload
        if (preloadPageIndex != -1) {
            onPreload();
            preloadPageIndex = -1;
        }

        // showing ? dismiss
        int previousStatus = showStatus;
        showStatus = selectFlag ? SHOWING : DISMISS;
        if (showStatus == SHOWING && previousStatus != showStatus) {
            showingFlag = true;
            dismissFlag = false;
            onShowing();
        } else if (showStatus == DISMISS && previousStatus != showStatus) {
            dismissFlag = true;
            showingFlag = false;
            onDismiss();
        }
    }

    protected boolean isShowing() {
        return showingFlag;
    }

    protected boolean isDismiss() {
        return dismissFlag;
    }

    protected boolean isScrolling() {
        return scrollStateListener != null && scrollStateListener.isScrolling();
    }

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public void setPageIndex(int index) {
        pageIndex = index;
    }

    public abstract View getView(); // 必须实现，这个AbsCubeView只是个抽象类，你可以选择把真实的View放到它的内部
    protected abstract void onPreload();
    protected abstract void onShowing();
    protected abstract void onDismiss();
    protected abstract void onScrollingChanged(boolean scrolling);
}
