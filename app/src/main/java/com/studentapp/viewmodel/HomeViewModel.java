package com.studentapp.viewmodel;

import android.app.Application;

import com.studentapp.main.home.interfaces.GetUser;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.repository.HomeRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel implements GetUser {

    private MutableLiveData<ModelUser> userLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        userLiveData = new MutableLiveData<ModelUser>();
    }

    public LiveData<ModelUser> getUserLiveData(String schoolId, String studentId){
        new HomeRepository().getUserForSpecificId(this,schoolId,studentId);
        return userLiveData;
    }


    @Override
    public void getUserData(ModelUser modelUser) {
        userLiveData.postValue(modelUser);
    }
}
