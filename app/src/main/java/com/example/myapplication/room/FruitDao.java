package com.example.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Fruit;

import java.util.List;

@Dao
public interface FruitDao {

    //增
    @Insert
    void insertFruit(Fruit... fruits);

    //改
    @Update
    void updateFruit(Fruit... fruits);

    //删除所有
    @Query("DELETE FROM Fruit")
    void delAllStudents();

    //删除单个
    @Delete
    void delStudents(Fruit... fruits);

    //查询所有
    @Query("SELECT * FROM Fruit ORDER BY ID DESC")
    LiveData<List<Fruit>> getAllFruitsLive();

    //根据id查询
    @Query("SELECT * FROM Fruit WHERE id=:id")
    List<Fruit> getFruitById(int id);
}
