/**
 * Copyright 2022 bejson.com
 */
package com.example.weathertest.database.week;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Auto-generated: 2022-08-09 13:59:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Entity(tableName = "data")
public class Data {

    @PrimaryKey
    @NonNull
    public String date;
    public String wea;
    public String wea_img;
    public String tem_day;
    public String tem_night;
    public String win;
    public String win_speed;


    public Data(String date, String wea, String wea_img, String tem_day, String tem_night, String win, String win_speed) {
        this.date = date;
        this.wea = wea;
        this.wea_img = wea_img;
        this.tem_day = tem_day;
        this.tem_night = tem_night;
        this.win = win;
        this.win_speed = win_speed;
    }
}