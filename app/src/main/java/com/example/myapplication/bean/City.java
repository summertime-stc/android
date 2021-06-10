package com.example.myapplication.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {

    public long id;
    private String cityname;
    private String citycode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public City(long id, String cityname, String citycode) {
        this.id = id;
        this.cityname = cityname;
        this.citycode = citycode;
    }

    public City(String cityname, String citycode) {
        this.cityname = cityname;
        this.citycode = citycode;
    }

    public City(){}

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
}
