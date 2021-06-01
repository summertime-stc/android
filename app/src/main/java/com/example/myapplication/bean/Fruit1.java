package com.example.myapplication.bean;

import org.litepal.crud.LitePalSupport;

public class Fruit1 extends LitePalSupport {
    public int ID;
    public String name;
    public String type;

    public Fruit1(int ID) {
        this.ID = ID;
    }

    public Fruit1(int ID, String name, String type) {
        this.ID = ID;
        this.name = name;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
