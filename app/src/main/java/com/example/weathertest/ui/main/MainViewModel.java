package com.example.weathertest.ui.main;

import androidx.lifecycle.MutableLiveData;

import com.example.constant.FailException;
import com.example.mvvm.BaseViewModel;
import com.example.net.HttpObserver;
import com.example.weathertest.constant.Response;

import com.example.weathertest.database.day.DayWeatherManager;
import com.example.weathertest.database.day.WeatherDay;
import com.example.weathertest.database.week.WeekDbManager;
import com.example.weathertest.database.week.WeekWeather;
import com.example.weathertest.ui.main.MainRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

/**
 * Create by Hastur
 * on 2022/8/8
 */
@HiltViewModel
public class MainViewModel extends BaseViewModel {
    private MainRepository repository;
    public MutableLiveData<Response<WeatherDay>> dayLivedata;
    public MutableLiveData<Response<WeekWeather>> weekLivedata;
    private String cityId;

    @Inject
    public MainViewModel(MainRepository repository) {
        super(repository);
        this.repository = repository;
        dayLivedata = new MutableLiveData<Response<WeatherDay>>();
        weekLivedata = new MutableLiveData<>();
    }

    //当日天气
    public void getDayWeatherInfo(String cityid) {
        cityId = cityid;
        repository.getDayWeatherInfo(cityid).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpObserver<WeatherDay>(getCompositeDisposable()) {
            @Override
            public void OnSuccess(WeatherDay data) {
                dayLivedata.postValue(new Response<>(200, data, null));
                DayWeatherManager.getInstance().insert(data, cityId);
            }


            @Override
            public void OnFailure(FailException e) {
                WeatherDay dayWeather = DayWeatherManager.getInstance().query(cityId);
                dayLivedata.postValue(new Response<>(2000, dayWeather, null));
                /**if (dayWeather == null) {
                    dayLivedata.postValue(new Response<>(e.getErrorCode(), null, e));
                }else {
                    dayLivedata.postValue(new Response<>(2000, dayWeather, null));
                }*/
            }
        });
    }


    //未来一周
    public void getWeekWeatherInfo(String cityid) {
        cityId = cityid;
        repository.getWeekWeatherInfo(cityid).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpObserver<WeekWeather>(getCompositeDisposable()) {
            @Override
            public void OnSuccess(WeekWeather data) {
                weekLivedata.postValue(new Response<>(200, data, null));
                WeekDbManager.getInstance().insert(data, cityId);
            }


            @Override
            public void OnFailure(FailException e) {
                WeekWeather dayWeather = WeekDbManager.getInstance().query(cityId);
                weekLivedata.postValue(new Response<>(2000, dayWeather, null));

            }
        });
    }
}
