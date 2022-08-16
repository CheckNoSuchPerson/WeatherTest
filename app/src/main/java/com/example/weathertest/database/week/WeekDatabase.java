package com.example.weathertest.database.week;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Create by Hastur
 * on 2022/8/9
 */
@Database(entities = {WeekWeather.class}, version = 1, exportSchema = false)
public abstract class WeekDatabase extends RoomDatabase {
    public abstract WeekWeatherDao weekWeatherDao();
}
