package com.example.myapplication.thread;

import com.example.myapplication.bean.MessageWrap;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

public class TimeThread extends Thread{

    public static boolean mPaused;
    public static boolean mFinished;
    private static Object lock=new Object();

    /**
     * 单例
     */
    private static volatile TimeThread instance = null;

    public static TimeThread getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new TimeThread();
                    mPaused=false;
                    mFinished=false;
                }
            }
        }
        return instance;
    }

    public void myPause() {
        synchronized (lock) {
            mPaused = true;
        }
    }

    public void myResume() {
        synchronized (lock) {
            mPaused = false;
            System.out.println(currentThread()+"继续线程");
            lock.notify();

            //mPauseLock.notifyAll();
        }
    }

    public void myStop() {
        mFinished = true;
    }

    public void run() {
        while ( !mFinished ) {
            synchronized (lock) {
                while ( mPaused ) {
                    try {
                        System.out.println(currentThread()+"--------暂停线程");
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
            //操作
            try {
                Thread.sleep(1000*2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentThread()+"-----"+new Date());
            EventBus.getDefault().post(MessageWrap.getInstance(new Date().toString()));
        }
    }
}
