package com.example.weathertest.utils;

/**
 * Create by Hastur
 * on 2022/8/9
 *
 * 天气与json文件对应的工具类
 */
public class WeatherMapUtil {
    public static String getFileMap(String weather){
        String filename;
        switch (weather){
            case "xue":
            case "bingbao":
                filename = "json/light-snow.json";
                break;
            case "lei":
                filename = "json/thunder.json";
                break;
            case "wu":
            case "shachen":
                filename = "json/fog.json";
                break;
            case "yun":
                filename = "json/cloudy.json";
                break;
            case "yu":
                filename = "json/Rain.json";
                break;
            case "yin":
                filename = "json/cloudy-little.json";
                break;
            case "qing":
            default:
                filename = "json/sunny-day.json";
                break;
        }
        return filename;
    }
}
