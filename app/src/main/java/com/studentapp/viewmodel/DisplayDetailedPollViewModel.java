package com.studentapp.viewmodel;

import android.app.Application;

import com.studentapp.main.home.interfaces.InsertOptionInPoll;
import com.studentapp.main.home.interfaces.InsertOptionInUser;
import com.studentapp.repository.DisplayDetailedPollRepository;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class DisplayDetailedPollViewModel extends AndroidViewModel implements InsertOptionInUser,
        InsertOptionInPoll {
    private MutableLiveData<Boolean> insertOptionInUser;
    private MutableLiveData<Boolean> insertOptionInPoll;

    public DisplayDetailedPollViewModel(@NonNull Application application) {
        super(application);
        insertOptionInUser = new MutableLiveData<Boolean>();
        insertOptionInPoll = new MutableLiveData<Boolean>();
    }

    public MutableLiveData<Boolean> insertAnsweredOption(HashMap<String, String> map, String schoolId, String userId){
        new DisplayDetailedPollRepository().insertOptionInUser(this,map,schoolId,userId);
        return insertOptionInUser;
    }

    @Override
    public void optionInserted(Boolean success) {
        insertOptionInUser.postValue(success);
    }

    public MutableLiveData<Boolean> insertAnsweredOptionInPoll(HashMap<String, String> map, String schoolId, String pollId){
        new DisplayDetailedPollRepository().insertStudentIdInAnsweredPoll(this,map,schoolId,pollId);
        return insertOptionInPoll;
    }

    @Override
    public void insertOptionInPoll(Boolean success) {
        insertOptionInPoll.postValue(true);
    }
}
