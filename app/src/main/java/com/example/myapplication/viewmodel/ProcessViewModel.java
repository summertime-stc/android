package com.example.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProcessViewModel extends ViewModel {
    private MutableLiveData<Integer> process;

    public MutableLiveData<Integer> getProcess(){
        if (process==null){
            process=new MutableLiveData<>();
            process.setValue(0);
        }
        return process;
    }
}
