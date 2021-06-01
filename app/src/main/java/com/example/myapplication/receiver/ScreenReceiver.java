package com.example.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if("android.intent.action.SCREEN_OFF".equals(action))
        {
            System.out.println("锁屏！");
            Log.e("screen","锁屏");
        }
        else if("android.intent.action.SCREEN_ON".equals(action))
        {
            System.out.println("解锁！");
            Log.e("screen","解锁");
        }
    }


}
