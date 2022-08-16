package com.example.weathertest.datainterface;

/**
 * Create by Hastur
 * on 2022/8/8
 */
public interface DataInterface {
    public interface OnSuccess<T>{
        void OnSuccess(T data);
    }

    public interface OnFailure{
        void OnFailure(String s);
    }
}
