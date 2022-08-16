package com.example.weathertest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.weathertest.R;

import java.lang.ref.WeakReference;


/**
 * Create by Hastur
 * on 2022/8/8
 */
public class WeatherMainView extends FrameLayout {
    private TextView tempText; //温度
    private TextView weatherText; //天气
    private TextView feedbackText; // 反馈
    private LottieAnimationView animationView; //动画
    private FeedbackListener listener;

    public WeatherMainView(Context context) {
        this(context, null);
    }

    public WeatherMainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View inflate = inflate(getContext(), R.layout.layout_weather_main, this);

        //addView(inflate);
        tempText = inflate.findViewById(R.id.tv_temp);
        weatherText = inflate.findViewById(R.id.tv_weather);
        tempText.setText(getResources().getString(R.string.temperature, 0));
        feedbackText = inflate.findViewById(R.id.tv_feedback);
        feedbackText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(new WeakReference<>(v));
                }
            }
        });

        animationView = findViewById(R.id.weather_lottie);

    }

    //设置监听，弱引用是写着玩的，这里没有内存泄漏
    public interface FeedbackListener{
        void onClick(WeakReference<View> v);
    }

    public void setListener(FeedbackListener listener) {
        this.listener = listener;
    }

    public void setData(int temp,String weather){
        tempText.setText(getResources().getString(R.string.temperature, temp));
        weatherText.setText(weather);
    }

    public void setAnimation(String json){
        animationView.setAnimation(json);
        animationView.setRepeatCount(ValueAnimator.INFINITE);
        animationView.playAnimation();
    }

    public void stopAnimation(){
        animationView.cancelAnimation();
    }
}
