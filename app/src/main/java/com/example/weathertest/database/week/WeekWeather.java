/**
 * Copyright 2022 bejson.com
 */
package com.example.weathertest.database.week;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.List;

/**
 * Auto-generated: 2022-08-09 13:59:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Entity(tableName = "week")
@TypeConverters(DataConverter.class)
public class WeekWeather {

    @NonNull
    @PrimaryKey
    public String cityid;
    public String city;
    public String update_time;
    public List<Data> data;

    public WeekWeather(@NonNull String cityid, String city, String update_time, List<Data> data) {
        this.cityid = cityid;
        this.city = city;
        this.update_time = update_time;
        this.data = data;
    }
}