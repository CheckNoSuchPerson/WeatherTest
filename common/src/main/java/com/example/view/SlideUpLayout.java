package com.example.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvvm.R;

/**
 * Create by Hastur
 * on 2022/8/15
 */
public class SlideUpLayout extends FrameLayout {
    private View mShadeView;
    private View mRealContentView;
    private MarginLayoutParams marginLayoutParams;
    private int heightPixels;// 屏幕高度

    private ValueAnimator showAnimator; //显示动画
    private ValueAnimator hideAnimator; //隐藏动画

    private float mDownY; //down 事件起始y
    private float mDeltaY; //记录偏差值

    private GestureDetector gestureDetector;
    private boolean isAnimation;

    private VelocityTracker velocityTracker;

    //处理快速滑动事件
    private GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("debugt", "velocityY = " + velocityY + ",velocityX = " + velocityX);
            if (velocityY > 800) {
                hideAnimation(GONE, (int) -mDeltaY);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

    };


    public SlideUpLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlideUpLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideUpLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        mShadeView = new View(getContext());
        mShadeView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mShadeView.setBackgroundColor(getResources().getColor(R.color.color_808A87, null));
        addView(mShadeView);
        //获取真实屏幕的高度以px为单位
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);//获取WM对象
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        heightPixels = dm.heightPixels;

        gestureDetector = new GestureDetector(getContext(), listener);
        velocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new IllegalArgumentException("仅支持1个子view");
        }
        //获得第一个子view
        //mShadeView = getChildAt(0);
        mShadeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideUpLayout.this.setVisibility(GONE);
            }
        });
        //获得第二个子view
        mRealContentView = getChildAt(1);
        marginLayoutParams = (MarginLayoutParams) mRealContentView.getLayoutParams();

        //默认在屏幕外
        marginLayoutParams.bottomMargin = -heightPixels;
        mRealContentView.setLayoutParams(marginLayoutParams);

        mRealContentView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                //velocityTracker.addMovement(event);

                //处理普通滑动事件
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mDeltaY = event.getRawY() - mDownY;
                        if (mDeltaY > 0) {
                            marginLayoutParams.bottomMargin = (int) -mDeltaY;
                            mRealContentView.setLayoutParams(marginLayoutParams);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //计算滑动速度
                        /**velocityTracker.computeCurrentVelocity(500);//计算速度
                         float xVelocity = velocityTracker.getXVelocity();
                         float yVelocity = velocityTracker.getYVelocity();
                         Log.e("debugt","&&&-->x = "+xVelocity+"---> y = "+yVelocity);*/
                        mDeltaY = event.getRawY() - mDownY;
                        if (mDeltaY < 0) break;
                        int height = mRealContentView.getMeasuredHeight();
                        if (mDeltaY > height / 2.0f) {
                            hideAnimation(GONE, (int) -mDeltaY);
                        } else {
                            showAnimation((int) -mDeltaY);
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            super.setVisibility(visibility);
            showAnimation(-heightPixels);
        } else {
            hideAnimation(visibility, 0);
        }
    }

    //隐藏动画，执行完之后设置隐藏
    private void hideAnimation(int visibility, int dy) {
        if (isAnimation) return;
        hideAnimator = ValueAnimator.ofInt(dy, -heightPixels);
        hideAnimator.setDuration(300);

        hideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                marginLayoutParams.bottomMargin = (int) animation.getAnimatedValue();
                mRealContentView.setLayoutParams(marginLayoutParams);
            }
        });

        hideAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
                SlideUpLayout.super.setVisibility(visibility);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimation = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
            }
        });

        hideAnimator.start();
    }

    //显示动画
    private void showAnimation(int dy) {
        if (isAnimation) return;
        showAnimator = ValueAnimator.ofInt(dy, 0);
        showAnimator.setDuration(300);

        showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                marginLayoutParams.bottomMargin = (int) animation.getAnimatedValue();
                mRealContentView.setLayoutParams(marginLayoutParams);
            }
        });

        showAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimation = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
            }
        });
        showAnimator.start();
    }
}
