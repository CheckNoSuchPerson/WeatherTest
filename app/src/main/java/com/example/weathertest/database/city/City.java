package com.example.weathertest.database.city;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Create by Hastur
 * on 2022/8/7
 */
@Entity(tableName = "city")
public class City {
    // ('101010100','beijing','北京','beijing','北京','China','中国','beijing','北京','39.904989','116.405285')
    //最后两项应该是经纬度

    @PrimaryKey
    @NonNull
    public String id;

    public String cityEn;

    public String cityZh;

    public String provinceEn;

    public String provinceZh;

    public String countryEn;

    public String countryZh;

    public String leaderEn;

    public String leaderZh;

    public String lat;

    public String lon;

    public City(String id, String cityEn, String cityZh, String provinceEn, String provinceZh, String countryEn, String countryZh, String leaderEn, String leaderZh, String lat, String lon) {
        this.id = id;
        this.cityEn = cityEn;
        this.cityZh = cityZh;
        this.provinceEn = provinceEn;
        this.provinceZh = provinceZh;
        this.countryEn = countryEn;
        this.countryZh = countryZh;
        this.leaderEn = leaderEn;
        this.leaderZh = leaderZh;
        this.lat = lat;
        this.lon = lon;
    }
}
