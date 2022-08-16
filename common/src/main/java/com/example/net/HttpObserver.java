package com.example.net;

import androidx.annotation.Nullable;

import com.example.constant.Constants;
import com.example.constant.FailException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Create by Hastur
 * on 2022/8/5
 */
public abstract class HttpObserver<T> extends DisposableObserver<T> {

    private @Nullable CompositeDisposable compositeDisposable;

    public HttpObserver(@Nullable CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    protected void onStart() {
        try {
            if (compositeDisposable == null){
                return;
            }
            compositeDisposable.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        if (compositeDisposable != null){
            compositeDisposable.remove(this);
        }
        OnSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (compositeDisposable != null){
            compositeDisposable.remove(this);
        }
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            OnFailure(new FailException(Constants.HTTP_ERROR_MSG, httpException.code()));
        } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
            OnFailure(new FailException(Constants.NETWORK_ERROR_MSG,Constants.NETWORK_ERROR_CODE));
        } else if (e instanceof SocketTimeoutException) {
            OnFailure(new FailException(Constants.TIMEOUT_ERROR_MSG,Constants.TIMEOUT_ERROR_CODE));
        } else {
            OnFailure(new FailException(Constants.UNKNOWN_ERROR_MSG, Constants.UNKNOWN_ERROR_CODE));
        }
    }

    @Override
    public void onComplete() {

    }

    abstract public void OnSuccess(T data);
    abstract public void OnFailure(FailException exception);
}
