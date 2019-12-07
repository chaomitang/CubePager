package com.qiwu.widget.cubepage.base.anim;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

/**
 * Describe: 添加transformer，左右滑动的时候，左边page和右边page的动画效果
 * Author: qi.wu
 * Date: 2019-11-17
 */
public class CubePagerTransformer extends AbsPagerTransformer {

    private static final float F_ROTATION = 0.75f;
    private static final float F_SCALE = 5f;
    private float ROTATION_MAX = 55;

    private Interpolator mLeftRotationInterpolator;
    private Interpolator mRightRotationInterpolator;
    private Interpolator mLeftScaleInterpolator;
    private Interpolator mRightScaleInterpolator;
    private float rotation = ROTATION_MAX;

    public CubePagerTransformer() {
        mLeftRotationInterpolator = new DecelerateInterpolator(F_ROTATION);
        mRightRotationInterpolator = new AccelerateInterpolator(F_ROTATION);
        mLeftScaleInterpolator = new DecelerateInterpolator(F_SCALE);
        mRightScaleInterpolator = new AccelerateInterpolator(F_SCALE);
    }

    public CubePagerTransformer(float rotation,
                                @NonNull Interpolator leftRotation, @NonNull Interpolator rightRotation,
                                @NonNull Interpolator leftScale, @NonNull Interpolator rightScale) {
        this.rotation = rotation;
        mLeftRotationInterpolator = leftRotation;
        mRightRotationInterpolator = rightRotation;
        mLeftScaleInterpolator = leftScale;
        mRightRotationInterpolator = rightScale;
    }

    @Override
    public void handleInvisiblePage(View page, float position) {
        page.setPivotX(page.getMeasuredWidth());
        page.setPivotY(page.getMeasuredHeight() * 0.5f);
        page.setRotationY(0f);
    }

    @Override
    public void handleLeftPage(View page, float position) {
        page.setPivotX(page.getMeasuredWidth());
        page.setPivotY(page.getMeasuredHeight() * 0.5f);
        float progress = 1 + position; // 进度从0到1
        // 前半段减速，后半段匀速
        float rY;
        float scaleX;
        rY = -rotation + rotation * mLeftRotationInterpolator.getInterpolation(progress);
        scaleX = 1.0f * mLeftScaleInterpolator.getInterpolation(progress);
        page.setScaleX(scaleX);
        page.setRotationY(rY);
    }

    @Override
    public void handleRightPage(View page, float position) {
        page.setPivotX(0f);
        page.setPivotY(page.getMeasuredHeight() * 0.5f);
        // 前半段匀速，后半段加速
        float rY;
        float scaleX;
        rY = rotation * mRightRotationInterpolator.getInterpolation(position);
        scaleX = 1 - mRightScaleInterpolator.getInterpolation(position);
        page.setScaleX(scaleX);
        page.setRotationY(rY);
    }
}
