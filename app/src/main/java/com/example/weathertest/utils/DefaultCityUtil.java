package com.example.weathertest.utils;

import static com.example.weathertest.constant.GlobalConstant.DEFAULT_CITY;

import com.example.weathertest.database.city.City;
import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

/**
 * Create by Hastur
 * on 2022/8/8
 */
public class DefaultCityUtil {
    public static void setDefaultCity(City city){
        String json = new Gson().toJson(city);
        MMKV.defaultMMKV().putString(DEFAULT_CITY,json);
    }
}
