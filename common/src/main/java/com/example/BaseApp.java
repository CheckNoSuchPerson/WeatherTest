package com.example;

import android.app.Application;
import com.tencent.mmkv.MMKV;

/**
 * Create by Hastur
 * on 2022/8/8
 */
public class BaseApp extends Application {
    public BaseApp() {
    }

    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
    }
}
