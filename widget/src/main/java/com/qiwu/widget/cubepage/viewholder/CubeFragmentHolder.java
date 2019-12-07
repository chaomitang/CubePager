package com.qiwu.widget.cubepage.viewholder;

import com.qiwu.widget.cubepage.base.AbsCubeFragment;
import com.qiwu.widget.cubepage.base.AbsPagerViewHolder;

/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public abstract class CubeFragmentHolder<F extends AbsCubeFragment> extends AbsPagerViewHolder<F> {

    public CubeFragmentHolder() {
        this(PAGE_NUM);
    }

    public CubeFragmentHolder(int pageNum) {
        super(pageNum);
    }
}
