package com.example.myapplication.bean;

import androidx.lifecycle.MutableLiveData;

public class Title {
    private String titleTime;
    private String titleMiddle;
    private String titleWendu;

    public String getTitleTime() {
        return titleTime;
    }

    public void setTitleTime(String titleTime) {
        this.titleTime = titleTime;
    }

    public String getTitleMiddle() {
        return titleMiddle;
    }

    public void setTitleMiddle(String titleMiddle) {
        this.titleMiddle = titleMiddle;
    }

    public String getTitleWendu() {
        return titleWendu;
    }

    public void setTitleWendu(String titleWendu) {
        this.titleWendu = titleWendu;
    }

    public Title(String titleTime, String titleMiddle, String titleWendu) {
        this.titleTime = titleTime;
        this.titleMiddle = titleMiddle;
        this.titleWendu = titleWendu;
    }

    public Title(){};
}
