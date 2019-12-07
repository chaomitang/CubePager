package com.qiwu.widget.cubepage.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiwu.widget.base.viewpager.PagerAdapter;
import com.qiwu.widget.base.viewpager.ViewPager;
import com.qiwu.widget.cubepage.base.AbsPagerViewHolder;
import com.qiwu.widget.cubepage.base.AbsSwipeControlViewPager;
import com.qiwu.widget.cubepage.base.IPageView;
import com.qiwu.widget.cubepage.base.anim.CubePagerTransformer;


/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public class BaseCubePager<V extends IPageView> extends AbsSwipeControlViewPager {

    private static final int SCROLL_LEFT = 1;
    private static final int SCROLL_RIGHT = 2;

    private boolean mPositionChanged = false;
    private int mCurrentPosition = 1;

    private int mPageSize = 0;
    private boolean mInit;

    protected PageListener<V> mPageListener;
    protected int mScrollingStatus;

    public BaseCubePager(@NonNull Context context) {
        this(context, null);
    }

    public BaseCubePager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setPageTransformer(false, new CubePagerTransformer());
    }

    public void init(PagerAdapter adapter, AbsPagerViewHolder<V> holder) {
        if (mInit) {
            return;
        }
        mPageSize = holder.getSize();

        setPreloadListener(currentChoose -> {
            int left = currentChoose - 1;
            int right = currentChoose + 1;
            if (holder.get(left) != null) {
                holder.get(left).preload(left);
            } else {
                holder.notifyPagePreload(left);
            }
            if (holder.get(right) != null) {
                holder.get(right).preload(right);
            } else {
                holder.notifyPagePreload(right);
            }
        });

        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mPageListener == null) return;
                if (Math.abs(position - mCurrentPosition) < 2 && Math.abs(positionOffset) > 0.00001f && mScrollingStatus == 0) {
                    if (position == mCurrentPosition) {
                        mScrollingStatus = SCROLL_RIGHT;
                        int target = getCheckedPosition(position + 1);
                        mPageListener.onPageScrolling(target, holder.get(target));
                        return;

                    } else if (position < mCurrentPosition) {
                        mScrollingStatus = SCROLL_LEFT;
                        int target = getCheckedPosition(position - 1);
                        mPageListener.onPageScrolling(target, holder.get(target));
                        return;
                    }
                }
                if (Math.abs(positionOffset) <= 0.001f) {
                    mScrollingStatus = 0;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (holder.getSize() <= 0) {
                    return;
                }

                int previousPos = mCurrentPosition;
                mCurrentPosition = position;

                if (mPageListener != null) {
                    if (mCurrentPosition > previousPos) {
                        mPageListener.onPageScrollToRight();
                    } else if (mCurrentPosition < previousPos) {
                        mPageListener.onPageScrollToLeft();
                    }
                }

                if (position >= mPageSize - 1) {
                    // 末位之后，跳转到首位（1）
                    mCurrentPosition = 1;
                    mPositionChanged = true;

                } else if (position < 1) {
                    // 首位之前，跳转到末尾（N）
                    mCurrentPosition = mPageSize - 2;
                    mPositionChanged = true;
                }
                // init初始化时调用的setCurrentItem,不会回调onPageScrollStateChanged，所以在此处执行 fragments.onSelect
                if (!mInit) {
                    mInit = true;
                    for (int i = 1; i < mPageSize - 1; i++) {
                        if (i == mCurrentPosition) {
                            continue;
                        }
                        if (holder.get(i) == null) {
                            holder.notifyPageSelect(false, i);
                        } else {
                            holder.get(i).select(false);
                        }
                    }
                    if (holder.get(mCurrentPosition) != null) {
                        holder.get(mCurrentPosition).select(true);
                        if (preloadListener != null) {
                            preloadListener.onPreload(mCurrentPosition);
                        }
                    } else {
                        holder.notifyPageSelect(true, mCurrentPosition);
                        if (preloadListener != null) {
                            preloadListener.onPreload(mCurrentPosition);
                        }
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_IDLE == state) {
                    if (mPositionChanged) {
                        mPositionChanged = false;
                        setCurrentItem(mCurrentPosition, false);
                    }
                    for (int i = 1; i < mPageSize - 1; i++) {
                        if (i == mCurrentPosition) {
                            continue;
                        }
                        if (holder.get(i) == null) {
                            holder.notifyPageSelect(false, i);
                        } else {
                            holder.get(i).select(false);
                        }
                    }
                    if (holder.get(mCurrentPosition) != null) {
                        holder.get(mCurrentPosition).select(true);
                        if (preloadListener != null) {
                            preloadListener.onPreload(mCurrentPosition);
                        }
                    } else {
                        holder.notifyPageSelect(true, mCurrentPosition);
                        if (preloadListener != null) {
                            preloadListener.onPreload(mCurrentPosition);
                        }
                    }
                }
            }
        });

        setAdapter(adapter);
        setCurrentItem(mCurrentPosition, false);
    }

    /**
     * return origin fragmentsList index!!!
     *
     */
    public int getCurrentIndex() {  // from 1 to PAGE_NUM
        if (mPageSize < 2) {
            return 0;
        }
        int index = getCurrentItem();
        if (index <= 0) index = mPageSize - 2;
        if (index >= (mPageSize - 1)) index = 1;
        return index;
    }


    public boolean isScrolling() {
        return mScrollingStatus != 0;
    }

    public void setPageListener(PageListener<V> pageListener) {
        this.mPageListener = pageListener;
    }

    public interface PageListener<V> {
        default void onPageScrolling(int checkedPosition, V view) {}
        default void onPageScrollToLeft() {}
        default void onPageScrollToRight() {}
    }


    private PreloadListener preloadListener;
    protected interface PreloadListener {
        void onPreload(int currentChoose);
    }
    protected void setPreloadListener(PreloadListener listener) {
        this.preloadListener = listener;
    }

    /**
     * 该方法返回的page的position是从1开始的，1，2，3
     * @param pos
     * @return
     */
    private int getCheckedPosition(int pos) {
        if (pos >= mPageSize - 1) {
            return 1;
        } else if (pos <= 0) {
            return mPageSize - 2;
        }
        return pos;
    }
}
