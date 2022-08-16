package com.example.constant;

import java.io.Serializable;

/**
 * Create by Hastur
 * on 2022/8/5
 */
public class Result<T> implements Serializable {
    private T data;
    private int code;
    private String msg;

    public Result(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
