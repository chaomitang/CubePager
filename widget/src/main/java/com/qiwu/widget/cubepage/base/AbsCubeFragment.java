package com.qiwu.widget.cubepage.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public abstract class AbsCubeFragment extends Fragment implements IPageView {
    private static final int SHOWING = 1;
    private static final int DISMISS = 2;

    private boolean createFlag;
    private boolean selectFlag;
    private boolean showingFlag;
    private boolean dismissFlag;

    private int pageIndex;
    private int preloadPageIndex; // 记录下该页面l的index并且该页面需要在create之后进行preLoad;

    private int showStatus = DISMISS;
    private ScrollStateListener scrollStateListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createFlag = false;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        created();
    }

    public void onDetach() {
        super.onDetach();
        destroyed();
    }

    /**
     * Important!
     */
    private void created() {
        if (!createFlag) { // 需要检查一下, 以防初始化后的onShowing未被调用
            createFlag = true;
            checkStatus();
        }
    }

    private void destroyed() {
        if (isShowing()) {
            onDismiss(); // 当前页在结束时，也会执行一次dismiss
        }
    }

    @Override
    public void setScrollStateListener(ScrollStateListener listener) {
        this.scrollStateListener = listener;
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

    @Override
    public void setPageIndex(int index) {
        this.pageIndex = index;
    }

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    private void checkStatus() {
        if (!createFlag) return;

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

    public boolean isCreated() {
        return createFlag;
    }

    public boolean isShowing() {
        return showingFlag;
    }

    public boolean isDismiss() {
        return dismissFlag;
    }

    public boolean isScrolling() {
        return scrollStateListener != null && scrollStateListener.isScrolling();
    }

    protected abstract void onPreload();
    protected abstract void onShowing();
    protected abstract void onDismiss();
    protected abstract void onScrollingChanged(boolean scrolling);
}
