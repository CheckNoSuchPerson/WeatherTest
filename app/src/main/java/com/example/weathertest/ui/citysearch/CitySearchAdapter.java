package com.example.weathertest.ui.citysearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.weathertest.R;
import com.example.weathertest.database.city.City;

import java.util.List;

/**
 * Create by Hastur
 * on 2022/8/11
 */
public class CitySearchAdapter extends BaseQuickAdapter<City, BaseViewHolder> {


    public CitySearchAdapter(@Nullable List<City> data) {
        super(R.layout.city_search_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, City city) {
        holder.setText(R.id.tv_city_item, getContext().getResources().getString(R.string.search_city, city.provinceZh, city.leaderZh, city.cityZh));
    }
}
