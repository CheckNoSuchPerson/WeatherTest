package com.example.weathertest.base;

import com.example.weathertest.datainterface.DataInterface;
import com.example.weathertest.constant.Response;

/**
 * Create by Hastur
 * on 2022/8/7
 */
public abstract class BaseActivity extends com.example.mvvm.BaseActivity {
    public <T> void handleResult(Response<T> data, DataInterface.OnSuccess<T> onSuccess, DataInterface.OnFailure onFailure) {
        //2000表示数据库查询
        if (data.getCode() != 200 && data.getCode() != 2000) {
            onFailure.OnFailure(data.getException().getErrorMsg());
        } else if (data.getData() == null) {
            onFailure.OnFailure("no data");

        } else {
            onSuccess.OnSuccess(data.getData());
        }
    }
}
