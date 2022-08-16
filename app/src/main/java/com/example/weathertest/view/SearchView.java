package com.example.weathertest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.weathertest.R;

/**
 * Create by Hastur
 * on 2022/8/10
 */
public class SearchView extends LinearLayout {
    //搜索与取消
    private ImageView mSearch, mCancel;
    //输入框
    private EditText mEdit;
    private LinearLayoutCompat.LayoutParams openParams;
    private int screenPxWidth;

    //edit hilt
    private String hilt;

    private ViewClickListener clickListener;
    private EditSearchListener searchListener;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {

        //初始化根布局
        setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);

        //添加3个控件
        mSearch = new ImageView(context);
        mCancel = new ImageView(context);
        mEdit = new EditText(context);

        //获取真实屏幕的宽度以px为单位
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);//获取WM对象
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screenPxWidth = dm.widthPixels;

        //imageView的布局
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(50, 50);
        params.gravity = Gravity.CENTER;

        //Edit的布局
        openParams = new LinearLayoutCompat.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        openParams.width = 0;
        openParams.weight = 1;

        //设置布局及属性
        mSearch.setLayoutParams(params);
        mCancel.setLayoutParams(params);
        mEdit.setLayoutParams(openParams);
        mCancel.setImageResource(R.mipmap.cancel);
        mEdit.setSingleLine(true);

        //添加view
        addView(mSearch);
        addView(mEdit);
        addView(mCancel);

        //默认不显示
        mEdit.setVisibility(GONE);
        mCancel.setVisibility(GONE);

        //设置监听
        mCancel.setOnClickListener(v -> {
            setHideLayout();
            if (clickListener != null) {
                clickListener.onCancelClick(v);
            }
        });

        mSearch.setOnClickListener(v -> {
            setOpenLayout();
            if (clickListener != null) {
                clickListener.onSearchClick(v);
            }
        });

        //输入法键盘监听
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_GO:
                    case EditorInfo.IME_ACTION_SEARCH:
                    case EditorInfo.IME_ACTION_NEXT:
                        if (searchListener == null) {
                            throw new NullPointerException("you must init searchListener");
                        } else {
                            searchListener.searchListener(mEdit.getText().toString());
                        }
                        hideInput();
                        //mEdit.setText("");
                        break;
                }
                return false;
            }
        });
    }

    //展开动画
    private void setOpenLayout() {
        ViewGroup.LayoutParams params = getLayoutParams();

        params.width = screenPxWidth;
        setLayoutParams(params);

        mEdit.setVisibility(VISIBLE);
        mCancel.setVisibility(VISIBLE);

        mEdit.setHint(hilt == null ? "搜索" : hilt);

        beginDelayedTransition(this);
    }

    private void hideInput() {
        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    //隐藏动画
    private void setHideLayout() {
        hideInput();

        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = mSearch.getWidth();

        setLayoutParams(params);

        mEdit.setText("");
        mEdit.setVisibility(GONE);
        mCancel.setVisibility(GONE);

        //开始动画
        beginDelayedTransition(this);


    }

    private void beginDelayedTransition(ViewGroup viewGroup) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(viewGroup, autoTransition);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Glide.with(this).load(R.mipmap.search).into(mSearch);
    }

    /**
     * 设置edit hilt的文本
     *
     * @param hilt
     */
    public void setEditHilt(String hilt) {
        this.hilt = hilt;
    }

    //点击事件回调
    public interface ViewClickListener {
        void onSearchClick(View v);

        void onCancelClick(View v);
    }

    /**
     * 设置两个按钮的点击监听
     *
     * @param listener
     */
    public void setClickListener(ViewClickListener listener) {
        clickListener = listener;
    }

    //输入法事件监听
    public interface EditSearchListener {
        void searchListener(String value);
    }

    public void setEditSearchListener(EditSearchListener listener) {
        searchListener = listener;
    }
}
