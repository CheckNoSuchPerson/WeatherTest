package com.example.weathertest.constant;

/**
 * Create by Hastur
 * on 2022/8/8
 */
public class GlobalConstant {
    //是否已经初始化了，未初始化时将写入一部分数据进数据库
    public static final String IS_INIT = "is_init";

    //天气请求api
    public static final String BASE_URL = "https://v0.yiketianqi.com/free/";

    //默认城市
    public static final String DEFAULT_CITY = "default_city";

    //城市id
    public static String CITY_ID = "city_id";

    //城市中文
    public static String CITY_ZH = "city_zh";
}
