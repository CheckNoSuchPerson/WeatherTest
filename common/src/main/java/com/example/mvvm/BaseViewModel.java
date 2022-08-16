package com.example.mvvm;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Create by Hastur
 * on 2022/8/5
 */
public abstract class BaseViewModel extends ViewModel {
    private BaseRepository mBaseRepository;

    public BaseViewModel(BaseRepository repository) {
        mBaseRepository = repository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        cancel();
    }

    private void cancel() {
        mBaseRepository.clear();
    }

    protected BaseRepository getBaseRepository() {
        return mBaseRepository;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mBaseRepository.getCompositeDisposable();
    }
}
