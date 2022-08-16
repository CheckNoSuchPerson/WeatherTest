package com.example.weathertest.database.day

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 *
 * Create by Hastur
 * on 2022/8/9
 *
 * {
 * "nums":226, //今日实时请求次数
 * "cityid":"101120101", //城市ID
 * "city":"济南",
 * "date":"2022-05-05",
 * "week":"星期四",
 * "update_time":"22:38", //更新时间
 * "wea":"多云", //天气情况
 * "wea_img":"yun", //天气标识
 * "tem":"25", //实况温度
 * "tem_day":"30", //白天温度(高温)
 * "tem_night":"23", //夜间温度(低温)
 * "win":"南风", //风向
 * "win_speed":"3级", //风力
 * "win_meter":"19km\/h", //风速
 * "air":"53", //空气质量
 * "pressure":"987", //气压
 * "humidity":"27%" //湿度
 * }
 */
@Entity(tableName = "weatherDay")
data class WeatherDay @JvmOverloads constructor(
    @PrimaryKey
    val cityid: String,
    val city: String,
    val update_time: String,
    val wea: String,
    val wea_img: String,
    val tem: String,
    val tem_day: String,
    val tem_night: String,
    val win: String,
    val win_speed:String,
    val win_meter:String,
    val air:String

) {
    @Ignore
    override fun toString(): String {
        return "DayWeather(cityid='$cityid', city='$city', update_time='$update_time', wea='$wea', wea_img='$wea_img', tem='$tem', tem_day='$tem_day', tem_night='$tem_night', win='$win', win_speed='$win_speed', win_meter='$win_meter', air='$air')"
    }
}