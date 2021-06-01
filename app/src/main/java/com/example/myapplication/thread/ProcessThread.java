package com.example.myapplication.thread;

import com.example.myapplication.bean.ProcessValue;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

public class ProcessThread extends Thread{
    public void run() {

        for (int i=0;i<=100;i++){
            try {
               sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            EventBus.getDefault().post(ProcessValue.getInstance(String.valueOf(i)));
        }
    }
}
