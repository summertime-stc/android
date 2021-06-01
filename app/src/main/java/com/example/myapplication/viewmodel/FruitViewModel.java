package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Fruit;
import com.example.myapplication.room.FruitRepository;

import java.util.List;

public class FruitViewModel extends AndroidViewModel {

    private FruitRepository fruitRepository;

    public FruitViewModel(@NonNull Application application) {
        super(application);
        this.fruitRepository=new FruitRepository(application);
    }

    public void insertFruit(Fruit...fruits){
        fruitRepository.insertFruit(fruits);
    }

    public void DelFruit(Fruit...fruits){
        fruitRepository.delFruit(fruits);
    }

    public void updateFruit(Fruit...fruits){
        fruitRepository.updateFruit(fruits);
    }

    public LiveData<List<Fruit>> getAllFruitsLive(){
        return fruitRepository.getAllFruitsLive();
    }
}
