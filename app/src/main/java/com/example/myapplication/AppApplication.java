package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.LongDef;

import com.example.myapplication.thread.TimeThread;

public class AppApplication extends Application {
    private static String TAG = "AppApplication";
    private static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"------------------运行------------------");
        TimeThread.getInstance().start();

    }

    // 获取Application
    public static Context getAppContext() {
        return instance;
    }
}
