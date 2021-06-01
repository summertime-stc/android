package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.litepal.crud.LitePalSupport;

@Entity
public class Fruit {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER)
    private Integer id;

    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    private String name;

    @ColumnInfo(name = "name1",typeAffinity = ColumnInfo.TEXT)
    private String name1;

    @ColumnInfo(name = "imageId",typeAffinity = ColumnInfo.INTEGER)
    private int imageId;

    @Ignore
    public Fruit(Integer id) {
        this.id = id;
    }

    @Ignore
    public Fruit(String name, String name1, int imageId) {
        this.name = name;
        this.name1 = name1;
        this.imageId = imageId;
    }

    @Ignore
    public Fruit(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fruit(Integer id, String name, String name1, int imageId) {
        this.id = id;
        this.name = name;
        this.name1 = name1;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId(){
        return imageId;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", name1='" + name1 + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}