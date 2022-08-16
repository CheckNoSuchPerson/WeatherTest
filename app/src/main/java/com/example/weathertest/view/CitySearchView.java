package com.example.weathertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weathertest.R;

/**
 * Create by Hastur
 * on 2022/8/10
 */
public class CitySearchView extends FrameLayout {
    private TextView tv_city;
    private SearchView searchView;

    private int cityStatus;

    //事件监听
    private EventListener eventListener;

    public CitySearchView(@NonNull Context context) {
        this(context, null);
    }

    public CitySearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CitySearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View inflate = inflate(context, R.layout.layout_city_search, this);
        tv_city = inflate.findViewById(R.id.tv_city_search);
        searchView = inflate.findViewById(R.id.search_view);
        cityStatus = tv_city.getVisibility();
        searchView.setClickListener(new SearchView.ViewClickListener() {
            @Override
            public void onSearchClick(View v) {
                tv_city.setVisibility(GONE);
                cityStatus = tv_city.getVisibility();
            }

            @Override
            public void onCancelClick(View v) {
                tv_city.setVisibility(VISIBLE);
                cityStatus = tv_city.getVisibility();
                if (eventListener != null) {
                    eventListener.onCancelClick(v);
                }
            }
        });

        searchView.setEditSearchListener(new SearchView.EditSearchListener() {
            @Override
            public void searchListener(String value) {
                if (eventListener != null)
                    eventListener.onSearchClick(value);
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        tv_city.setVisibility(cityStatus);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private int sp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }

    public interface EventListener {
        void onCancelClick(View v);

        void onSearchClick(String value);
    }

    public void setEventListener(EventListener listener) {
        eventListener = listener;
    }
}
