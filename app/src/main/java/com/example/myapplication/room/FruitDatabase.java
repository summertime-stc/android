package com.example.myapplication.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.Fruit;

@Database(entities = {Fruit.class},version = 1,exportSchema = false)
public abstract class FruitDatabase extends RoomDatabase {

    public abstract FruitDao getFruitDao();

    //单例模式 返回db
    private static FruitDatabase INSTANSE;
    public static synchronized FruitDatabase getInstance(Context context){
        if (INSTANSE==null){
            INSTANSE= Room.databaseBuilder(context.getApplicationContext(),FruitDatabase.class,"fruit_database").build();
        }
        return INSTANSE;
    }
}
