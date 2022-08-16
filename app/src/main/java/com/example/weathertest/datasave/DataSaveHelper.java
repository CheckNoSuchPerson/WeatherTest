package com.example.weathertest.datasave;

import com.example.weathertest.database.day.WeatherDay;

/**
 * Create by Hastur
 * on 2022/8/10
 */
public class DataSaveHelper {
    private static WeatherDay data;

    public static void setData(WeatherDay day) {
        data = day;
    }

    public static WeatherDay getData() {
        return data;
    }
}
