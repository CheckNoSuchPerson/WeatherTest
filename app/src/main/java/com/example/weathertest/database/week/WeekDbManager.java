package com.example.weathertest.database.week;

import androidx.room.Room;

import com.example.executor.ExecutorManager;
import com.example.weathertest.App;


import java.util.concurrent.CountDownLatch;

/**
 * Create by Hastur
 * on 2022/8/9
 */
public class WeekDbManager {
    private static volatile WeekDbManager instance;
    private volatile WeekDatabase WeekDatabase;

    private WeekDbManager() {
        WeekDatabase = Room.databaseBuilder(App.getApp(),
                WeekDatabase.class, "weekWeather_db")
                .build();

    }

    public static WeekDbManager getInstance() {
        if (instance == null) {
            synchronized (WeekDbManager.class) {
                if (instance == null) {
                    instance = new WeekDbManager();
                }
            }
        }
        return instance;
    }

    /**
     * @param dayWeather 将新数据加入数据库
     * @param cityid     用来查询数据库现有内容
     */
    public void insert(WeekWeather dayWeather, String cityid) {
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WeekWeather query = query(cityid);
                if (query == null) {
                    WeekDatabase.weekWeatherDao().insert(dayWeather);
                } else {
                    WeekDatabase.weekWeatherDao().update(dayWeather);
                }
            }
        });
    }

    public WeekWeather query(String cityid) {
        final WeekWeather[] list = new WeekWeather[1];
        CountDownLatch count = new CountDownLatch(1);
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list[0] = WeekDatabase.weekWeatherDao().query(cityid);
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
