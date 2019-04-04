package com.studentapp.viewmodel;

import android.app.Application;

import com.studentapp.main.home.interfaces.SetIdListOfPolls;
import com.studentapp.repository.PollListFragmentRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PollListFragmentViewModel extends AndroidViewModel implements SetIdListOfPolls {
    private LiveData<List<String>> pollIdList;

    public PollListFragmentViewModel(@NonNull Application application) {
        super(application);
        pollIdList = new MutableLiveData<>();
    }

    public LiveData<List<String>> getPollIdList(String schoolId, String classId, String divisionId){
        new PollListFragmentRepository().getListOfPollID(this, schoolId, classId, divisionId);
        return pollIdList;
    }

    @Override
    public void setPollId(List<String> pollIdList) {

    }
}
