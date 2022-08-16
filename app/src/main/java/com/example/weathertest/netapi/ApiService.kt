package com.example.weathertest.netapi

import com.example.constant.Result
import com.example.weathertest.database.day.WeatherDay
import com.example.weathertest.database.week.WeekWeather
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Create by Hastur
 * on 2022/8/5
 */
interface ApiService {

    @GET("day")
    fun getDayWeatherInfo(
        @Query("appid") appid: String = "49132222",
        @Query("appsecret") appsecret: String = "alQkZ60N",
        @Query("cityid") cityid: String, //城市ID
        @Query("unescape") unescape:Int = 1 //如果您希望json不被unicode, 直接输出中文, 请传此参数: 1
    ): Observable<WeatherDay>

    @GET("week")
    fun getWeekWeatherInfo(
        @Query("appid") appid: String = "49132222",
        @Query("appsecret") appsecret: String = "alQkZ60N",
        @Query("cityid") cityid: String, //城市ID
        @Query("unescape") unescape:Int = 1 //如果您希望json不被unicode, 直接输出中文, 请传此参数: 1
    ): Observable<WeekWeather>
}