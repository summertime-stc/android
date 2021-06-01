package com.example.myapplication.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Fruit;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FruitRepository {

    private FruitDao fruitDao;

    public FruitRepository(Context context) {
        FruitDatabase database=FruitDatabase.getInstance(context);
        this.fruitDao=database.getFruitDao();
    }

    public void insertFruit(Fruit...fruits){
        new InsertFruitTask(fruitDao).execute(fruits);
    }

    public void delFruit(Fruit...fruits){
        new DelFruitTask(fruitDao).execute(fruits);
    }

    public void updateFruit(Fruit...fruits){
        new UpdateFruitTask(fruitDao).execute(fruits);
    }

    public Fruit getFruitById(int id){
        AsyncTask<Integer, Void, Fruit> execute = new GetFruitByIdTask(fruitDao).execute(id);

        try {
            return execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Fruit>> getAllFruitsLive(){
        return fruitDao.getAllFruitsLive();
    }

    class InsertFruitTask extends AsyncTask<Fruit,Void,Void> {

        private FruitDao fruitDao;

        public InsertFruitTask(FruitDao fruitDao) {
            this.fruitDao = fruitDao;
        }

        @Override
        protected Void doInBackground(Fruit... fruits) {
            fruitDao.insertFruit(fruits);
            return null;
        }
    }


    class DelFruitTask extends AsyncTask<Fruit,Void,Void>{

        private FruitDao fruitDao;

        public DelFruitTask(FruitDao fruitDao) {
            this.fruitDao = fruitDao;
        }

        @Override
        protected Void doInBackground(Fruit... fruits) {
            fruitDao.delStudents(fruits);
            return null;
        }
    }

    class UpdateFruitTask extends AsyncTask<Fruit,Void,Void>{

        private FruitDao fruitDao;

        public UpdateFruitTask(FruitDao fruitDao) {
            this.fruitDao = fruitDao;
        }

        @Override
        protected Void doInBackground(Fruit... fruits) {
            fruitDao.updateFruit(fruits);
            return null;
        }
    }

    class GetFruitByIdTask extends AsyncTask<Integer,Void, Fruit>{

        private FruitDao fruitDao;

        public GetFruitByIdTask(FruitDao fruitDao) {
            this.fruitDao = fruitDao;
        }

        @Override
        protected Fruit doInBackground(Integer... integers) {
            return fruitDao.getFruitById(integers[0]).get(0);
        }
    }
}
