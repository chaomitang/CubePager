package com.qiwu.widget.cubepage.viewholder;

import com.qiwu.widget.cubepage.base.AbsCubeView;
import com.qiwu.widget.cubepage.base.AbsPagerViewHolder;

/**
 * Author: qi.wu
 * Date: 2019-11-18
 */
public abstract class CubeViewHolder<V extends AbsCubeView> extends AbsPagerViewHolder<V> {

    public CubeViewHolder() {
        this(PAGE_NUM);
    }

    public CubeViewHolder(int pageNum) {
        super(pageNum);
    }
}
