package com.example.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Create by Hastur
 * on 2022/8/9
 */
public class MyBehavior extends FloatingActionButton.Behavior {
    private float h;//滑动的距离
    private boolean isAnimation; //动画是否在执行
    private ViewPropertyAnimator animator;

    public MyBehavior() {
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, @NonNull View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    //竖直滑动
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        h = coordinatorLayout.getHeight() - child.getX();
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        //super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
        //dyConsumed大于0是向上滚动 小于0是向下滚动
        if (!isAnimation) {
            hideAnimator(child);
        }
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        if (!isAnimation) {
            hideAnimator(child);
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int type) {
        showAnimator(child);
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    private void showAnimator(FloatingActionButton child) {
        animator = child.animate().translationY(0).setDuration(200);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
            }
        });
        animator.start();
    }

    private void hideAnimator(FloatingActionButton child) {
        animator = child.animate().translationY(h).setDuration(200);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = true;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
            }
        });
        animator.start();
    }
}
