package com.example.constant;

/**
 * Create by Hastur
 * on 2022/8/8
 */
public class FailException {
    private int errorCode;
    private String errorMsg;
    public FailException( String errorMsg,int errorCode) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
