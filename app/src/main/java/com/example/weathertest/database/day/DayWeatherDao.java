package com.example.weathertest.database.day;


import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


/**
 * Create by Hastur
 * on 2022/8/9
 */
@Dao
public interface DayWeatherDao {
    @Insert
    void insert(WeatherDay weather);

    @Query("SELECT * FROM weatherDay WHERE cityid = :cityid LIMIT 1")
    WeatherDay query(String cityid);

    @Delete
    void delete(WeatherDay weather);

    @Update
    void update(WeatherDay weather);
}
