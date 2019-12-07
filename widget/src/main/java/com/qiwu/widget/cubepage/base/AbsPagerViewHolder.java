package com.qiwu.widget.cubepage.base;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

/**
 * Describe: ViewHolder or FragmentHolder
 * Author: qi.wu
 * Date: 2019-11-17
 */
public abstract class AbsPagerViewHolder<V extends IPageView> {
    protected static final int PAGE_NUM = 3; // default

    private int pageNumber;
    private int size;
    private SparseArray<V> pages;

    private ScrollStateListener scrollStateListener;

    protected SparseBooleanArray preLoadWhenInstantiate; // 这个变量缓存的 是否需要 在实例化page时候   调用onPreLoad()方法 预加载
    protected SparseIntArray selectWhenInstantiate; // 1 select-true 2 select-false  0 不调用 // 是否需要 在实例化该page的时候   调用onSelect()方法  加载内容

    public int getSize() {
        return size;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public V get(int pos) {
        V v = pages.get(pos);
        if (v == null) {
            if (pos == 0) {
                return pages.get(pageNumber);
            } else if (pos == 1) {
                return pages.get(pageNumber + 1);
            } else if (pos == pageNumber) {
                return pages.get(0);
            } else if (pos == pageNumber + 1) {
                return pages.get(1);
            }
        }
        return pages.get(pos);
    }

    public AbsPagerViewHolder(int pageNumber) throws IllegalArgumentException {
        if (pageNumber < 3) {
            throw new IllegalArgumentException("Sorry, the pages' number must be at lease 3");
        }
        pages = new SparseArray<>();
        this.pageNumber = pageNumber;
        this.size = pageNumber + 2;
        preLoadWhenInstantiate = new SparseBooleanArray();
        selectWhenInstantiate = new SparseIntArray();
    }

    public void setScrollStateListener(ScrollStateListener scrollStateListener) {
        this.scrollStateListener = scrollStateListener;
    }

    /**
     * 必须实现，用于实例化该position的View
     * @return
     */
    public abstract V instantiate();

    /**
     * 必须实现，用于实例化该position的View
     * @param position
     * @return
     */
    public V instantiate(int position) {
        V view = this.pages.get(position);
        if (view == null) {
            view = instantiate();
            view.setScrollStateListener(this.scrollStateListener);
            this.pages.put(position, view);

            int otherPos = -1;
            if (position == 0) {
                otherPos = this.pageNumber;
            } else if (position == 1) {
                otherPos = this.pageNumber + 1;
            } else if (position == this.pageNumber) {
                otherPos = 0;
            } else if (position == this.pageNumber + 1) {
                otherPos = 1;
            }
            if (otherPos != -1) {
                this.pages.put(otherPos, view);
            }
        }

        view.setPageIndex(position == 0 ? this.pageNumber : (position == this.pageNumber + 1 ? 1 : position));// 写入该页面是哪个页面，从1开始， 1， 2， 3

        if (preLoadWhenInstantiate.get(position)) {
            view.preload(position);
            preLoadWhenInstantiate.put(position, false);
        }
        if (selectWhenInstantiate.get(position) > 0) {
            view.select(selectWhenInstantiate.get(position) == 1);
            selectWhenInstantiate.put(position, 0);
        }

        return view;
    }

    /**
     * 在Page View创建完毕后 调用这个方法会告诉你，该位置的View已经实例化了
     * @param v
     * @param position
     */
    public void onPageInstantiated(V v, int position) {
        this.pages.put(position, v);
    }

    public void notifyPageSelect(boolean select, int position) {
        selectWhenInstantiate.put(position, select ? 1 : 2);
    }

    public void notifyPagePreload(int position) {
        preLoadWhenInstantiate.put(position, true);
    }
}
