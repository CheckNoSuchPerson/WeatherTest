package com.example.weathertest.database.day;

import androidx.room.Room;

import com.example.executor.ExecutorManager;
import com.example.weathertest.App;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * Create by Hastur
 * on 2022/8/9
 */
public class DayWeatherManager {
    private static volatile DayWeatherManager instance;
    private volatile DayWeatherDatabase dayWeatherDatabase;

    private DayWeatherManager() {
        dayWeatherDatabase = Room.databaseBuilder(App.getApp(),
                DayWeatherDatabase.class, "dayWeather_db")
                .build();

    }

    public static DayWeatherManager getInstance() {
        if (instance == null) {
            synchronized (DayWeatherManager.class) {
                if (instance == null) {
                    instance = new DayWeatherManager();
                }
            }
        }
        return instance;
    }

    /**
     * @param dayWeather 将新数据加入数据库
     * @param cityid     用来查询数据库现有内容
     */
    public void insert(WeatherDay dayWeather, String cityid) {
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WeatherDay query = query(cityid);
                if (query == null) {
                    dayWeatherDatabase.dayWeatherDao().insert(dayWeather);
                } else {
                    dayWeatherDatabase.dayWeatherDao().update(dayWeather);
                }
            }
        });
    }

    public WeatherDay query(String cityid) {
        final WeatherDay[] list = new WeatherDay[1];
        CountDownLatch count = new CountDownLatch(1);
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list[0] = dayWeatherDatabase.dayWeatherDao().query(cityid);
                count.countDown();
            }
        });
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return list[0];
    }
}
