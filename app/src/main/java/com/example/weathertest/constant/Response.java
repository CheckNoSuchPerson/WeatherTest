package com.example.weathertest.constant;

import com.example.constant.FailException;

/**
 * Create by Hastur
 * on 2022/8/8
 */
public class Response<T> {
    private int code;
    private T data;
    private FailException exception;

    public Response(int code, T data, FailException exception) {
        this.code = code;
        this.data = data;
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public FailException getException() {
        return exception;
    }
}
