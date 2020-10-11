package com.projects.tarea.ui.sites;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SiteViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mText;


    public SiteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is site fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



}