package com.example.mvvm;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Create by Hastur
 * on 2022/8/5
 */
public abstract class BaseRepository {
    private CompositeDisposable mCompositeDisposable;

    public BaseRepository(){
        mCompositeDisposable = new CompositeDisposable();
    }

    public void clear(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    public CompositeDisposable getCompositeDisposable(){
        return mCompositeDisposable;
    }
}
