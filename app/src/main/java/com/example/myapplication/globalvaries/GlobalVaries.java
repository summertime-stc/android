package com.example.myapplication.globalvaries;

import android.app.Application;

public final class GlobalVaries extends Application {
    private static String telNum;
    private static String showchecked="1";

    public static String getShowchecked() {
        return showchecked;
    }

    public static void setShowchecked(String showchecked) {
        GlobalVaries.showchecked = showchecked;
    }

    public static String getTelNum() {
        return telNum;
    }

    public static void setTelNum(String s) {
        telNum = s;
    }

    @Override
    public void onCreate() {
        telNum = " ";

        super.onCreate();
    }
 }