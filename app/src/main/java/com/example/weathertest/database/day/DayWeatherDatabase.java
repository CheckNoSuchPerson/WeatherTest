package com.example.weathertest.database.day;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Create by Hastur
 * on 2022/8/9
 */
@Database(entities = {WeatherDay.class},version = 1,exportSchema = false)
public abstract class DayWeatherDatabase extends RoomDatabase {
    public abstract DayWeatherDao dayWeatherDao();
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };
}
