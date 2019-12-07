package com.qiwu.widget.cubepage.base;

/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public interface IPageView {
    public void setPageIndex(int index);
    public int getPageIndex();
    public void setScrollStateListener(ScrollStateListener scrollListener); // 在该页面内监听 Pager是否正在滑动
    public void preload(int preloadPageIndex); // 功能，为预加载 preloadPageIndex是该页面的index 1.2.3...
    public void select(boolean select); // 该页面是否显示，在滑屏结束后执行
}
