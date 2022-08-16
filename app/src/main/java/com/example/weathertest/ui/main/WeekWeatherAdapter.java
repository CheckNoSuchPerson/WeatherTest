package com.example.weathertest.ui.main;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.weathertest.R;
import com.example.weathertest.database.week.Data;
import com.example.weathertest.database.week.WeekWeather;

import java.util.List;

/**
 * Create by Hastur
 * on 2022/8/11
 */
public class WeekWeatherAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {
    private List<Data> weatherList;

    public WeekWeatherAdapter(@Nullable List<Data> data) {
        super(R.layout.item_weather, data);
        weatherList = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Data data) {
        holder.setText(R.id.tv_date_item, regularString(data.date)); //规范日期
        holder.setText(R.id.tv_wea_item, data.wea); //天气
        holder.setText(R.id.tv_windy_item, data.win); //风向
        holder.setText(R.id.tv_temp_item, getContext().getResources().getString(R.string.temp_range, data.tem_day, data.tem_night)); //高低温
        Glide.with(getContext()).load(getImage(data.wea_img)).into((ImageView) holder.getView(R.id.iv_wea_item));
    }

    private @RawRes
    int getImage(String wea_img) {
        int resId = -1;
        switch (wea_img) {
            case "xue":
                resId = R.mipmap.snow;
                break;
            case "bingbao":
                resId = R.mipmap.hail;
                break;
            case "lei":
                resId = R.mipmap.thunder;
                break;
            case "wu":
                resId = R.mipmap.fog;
            case "shachen":
                resId = R.mipmap.smog;
                break;
            case "yun":
                resId = R.mipmap.cloud;
                break;
            case "yu":
                resId = R.mipmap.rain;
                break;
            case "yin":
                resId = R.mipmap.overcast;
                break;
            case "qing":
            default:
                resId = R.mipmap.sun;
                break;
        }
        return resId;
    }

    private String regularString(String date) {
        String substring = date.substring(5);
        return substring.replace("-", "/");
    }
}
