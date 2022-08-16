package com.example.weathertest.base;

import androidx.annotation.Nullable;

import com.example.constant.FailException;
import com.example.net.HttpObserver;
import com.example.weathertest.App;
import com.example.weathertest.database.city.City;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Create by Hastur
 * on 2022/8/7
 * 本来想着把数据库请求封装在网络请求中，发现不太好实现
 */
@Deprecated
public abstract class BaseObserver<T> extends HttpObserver<T> {

    public BaseObserver(@Nullable CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
    }

    @Override
    public void OnSuccess(T data) {
        OnRealSuccess(data);
    }

    @Override
    public void OnFailure(FailException exception) {

    }

    abstract public void OnRealSuccess(T data);

    abstract public void OnRealFailure(FailException exception);

    abstract public void OnDbSuccess(List<City> data);
}
