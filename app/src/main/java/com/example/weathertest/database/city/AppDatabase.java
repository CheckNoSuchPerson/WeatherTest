package com.example.weathertest.database.city;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Create by Hastur
 * on 2022/8/7
 */
@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();

    /**static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };*/
}
