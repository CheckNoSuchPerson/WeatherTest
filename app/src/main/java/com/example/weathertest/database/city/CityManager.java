package com.example.weathertest.database.city;

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
public class CityManager {
    private static volatile CityManager instance;
    private volatile AppDatabase cityDb;

    private CityManager() {
        cityDb = Room.databaseBuilder(App.getApp(),
                AppDatabase.class, "city_db")
                .build();

    }

    public static CityManager getInstance() {
        if (instance == null) {
            synchronized (CityManager.class) {
                if (instance == null) {
                    instance = new CityManager();
                }
            }
        }
        return instance;
    }

    public void insertCityList(List<City> cities) {
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cityDb.cityDao().insertList(cities);
            }
        });

    }

    public void insertCity(City city) {
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cityDb.cityDao().insertSingle(city);
            }
        });
    }

    public List<City> queryCitiesByProvince(String province) {
        List<City> list = new ArrayList<>();
        CountDownLatch count = new CountDownLatch(1);
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list.addAll(cityDb.cityDao().queryCitiesByProvince(province));
                count.countDown();
            }
        });
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public List<City> queryCities(String s) {
        List<City> list = new ArrayList<>();
        CountDownLatch count = new CountDownLatch(1);
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list.addAll(cityDb.cityDao().queryCities(s));
                count.countDown();
            }
        });
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public List<City> queryFormSearch(String s) {
        List<City> list = new ArrayList<>();
        CountDownLatch count = new CountDownLatch(1);
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list.addAll(cityDb.cityDao().queryFormSearch(s));
                count.countDown();
            }
        });
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
