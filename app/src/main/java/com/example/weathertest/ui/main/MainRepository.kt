package com.example.weathertest.ui.main

import javax.inject.Inject
import com.example.mvvm.BaseRepository
import com.example.net.HttpManager
import com.example.weathertest.database.day.WeatherDay
import com.example.weathertest.database.week.WeekWeather
import com.example.weathertest.netapi.ApiService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Create by Hastur
 * on 2022/8/8
 */
class MainRepository @Inject constructor() : BaseRepository() {
    fun getDayWeatherInfo(city: String): Observable<WeatherDay> {
        return HttpManager.getInstance().create(ApiService::class.java).getDayWeatherInfo(cityid = city).subscribeOn(Schedulers.io())
    }

    fun getWeekWeatherInfo(city: String):Observable<WeekWeather>{
        return HttpManager.getInstance().create(ApiService::class.java).getWeekWeatherInfo(cityid = city).subscribeOn(Schedulers.io())
    }
}