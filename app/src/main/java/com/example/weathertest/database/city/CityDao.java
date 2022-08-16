package com.example.weathertest.database.city;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weathertest.database.city.City;

import java.util.List;

/**
 * Create by Hastur
 * on 2022/8/7
 */
@Dao
public interface CityDao {
    @Insert
    void insertSingle(City city);

    @Insert
    void insertList(List<City> cities);

    @Query("SELECT * FROM city WHERE cityZh == :cityZh")
    List<City> queryCities(String cityZh);

    @Query("SELECT * FROM city WHERE provinceZh == :provinceZh ")
    List<City> queryCitiesByProvince(String provinceZh);

    @Query("SELECT * FROM city WHERE provinceZh LIKE '%' || :value || '%' OR cityZh LIKE '%' || :value || '%' OR leaderZh LIKE '%' || :value || '%' ")
    List<City> queryFormSearch(String value);
}
