package com.example.myapplication.globalvaries;

import android.app.Application;

public final class GlobalVaries extends Application {
    private static String telNum;
    private static String showchecked="1";
    private static String location;
    private static String locationcode;

    public static String getLocationcode() {
        return locationcode;
    }

    public static void setLocationcode(String locationcode) {
        GlobalVaries.locationcode = locationcode;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        GlobalVaries.location = location;
    }

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