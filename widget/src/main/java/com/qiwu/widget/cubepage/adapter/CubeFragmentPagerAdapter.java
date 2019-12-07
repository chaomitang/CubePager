package com.qiwu.widget.cubepage.adapter;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.qiwu.widget.base.viewpager.PagerAdapter;
import com.qiwu.widget.cubepage.base.AbsCubeFragment;
import com.qiwu.widget.cubepage.viewholder.CubeFragmentHolder;


/**
 * Author: qi.wu
 * Date: 2019-11-17
 */
public class CubeFragmentPagerAdapter<F extends AbsCubeFragment> extends PagerAdapter {

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @SuppressWarnings("ReferenceEquality")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        final long itemId = getItemId(position);

        // Do we already have this fragment?
        String name = makeFragmentName(container.getId(), itemId);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mCurTransaction.hide(fragment);
            mCurTransaction.show(fragment);
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;

        } else {
            fragment = getItem(position);
            mCurTransaction = mFragmentManager.beginTransaction();
            if (!fragment.isAdded()) {
                mCurTransaction.add(container.getId(), fragment,
                        makeFragmentName(container.getId(), itemId));
                mCurTransaction.commitNowAllowingStateLoss();
                mCurTransaction = null;
            }
        }
        holder.onPageInstantiated((F) fragment, position);
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    @SuppressWarnings("ReferenceEquality")
    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment)object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment)object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    /**
     * Return a unique identifier for the item at the given position.
     *
     * <p>The default implementation returns the given position.
     * Subclasses should override this method if the positions of items can change.</p>
     *
     * @param position Position within this adapter
     * @return Unique identifier for the item at position
     */
    public long getItemId(int position) {
        if (position == 0) {
            return holder.getSize() - 2;
        } else if (position == holder.getSize() - 1) {
            return 1;
        }
        return position;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }


    private CubeFragmentHolder<F> holder;
    public CubeFragmentPagerAdapter(FragmentManager fm, CubeFragmentHolder<F> holder) {
        mFragmentManager = fm;
        this.holder = holder;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    public Fragment getItem(int position) {
        return this.holder.instantiate(position);
    }

    @Override
    public int getCount() {
        return this.holder.getSize();
    }
}
