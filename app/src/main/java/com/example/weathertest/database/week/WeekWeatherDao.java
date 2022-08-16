package com.example.weathertest.database.week;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Create by Hastur
 * on 2022/8/9
 */
@Dao
public interface WeekWeatherDao {
    @Insert
    void insert(WeekWeather weather);

    @Query("SELECT * FROM week WHERE cityid = :cityid LIMIT 1")
    WeekWeather query(String cityid);

    @Update
    void update(WeekWeather weather);
}
