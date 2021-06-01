package com.example.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.bean.Title;

public class TitleViewModel extends ViewModel {
    private MutableLiveData<Title> processTitle;

    public MutableLiveData<Title> getTitle() {
        if (processTitle == null) {
            processTitle = new MutableLiveData<>();
            processTitle.setValue(new Title("", "", ""));
        }
        return processTitle;
    }
}
