package com.example.weathertest.database.week;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Create by Hastur
 * on 2022/8/9
 */
public class DataConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public String toString(List<Data> data) {
        return gson.toJson(data);
    }

    @TypeConverter
    public List<Data> toList(String json) {
        Type listType = new TypeToken<List<Data>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }
}
