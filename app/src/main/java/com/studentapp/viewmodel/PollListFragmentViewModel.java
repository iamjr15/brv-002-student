package com.studentapp.viewmodel;

import android.app.Application;

import com.studentapp.main.home.interfaces.GetAllPoll;
import com.studentapp.main.home.interfaces.SetIdListOfPolls;
import com.studentapp.main.home.model.PollsModel;
import com.studentapp.repository.PollListFragmentRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PollListFragmentViewModel extends AndroidViewModel implements SetIdListOfPolls, GetAllPoll {

    private MutableLiveData<List<String>> pollIdList;
    private MutableLiveData<List<PollsModel>> pollList;

    public PollListFragmentViewModel(@NonNull Application application) {
        super(application);
        pollIdList = new MutableLiveData<List<String>>();
        pollList = new MutableLiveData<List<PollsModel>>();
    }

    public LiveData<List<String>> getPollIdList(String schoolId, String classId, String divisionId){
        new PollListFragmentRepository().getListOfPollID(this, schoolId, classId, divisionId);
        return pollIdList;
    }

    public LiveData<List<PollsModel>> getPollList(List<String> pollIdList, String schoolId){
        new PollListFragmentRepository().getAllPoll(this,pollIdList,schoolId);
        return pollList;
    }

    @Override
    public void setPollId(List<String> pollIdList) {
        this.pollIdList.postValue(pollIdList);
    }

    @Override
    public void getPollListForSpecificStudent(List<PollsModel> pollsModelList) {
        pollList.postValue(pollsModelList);
    }
}
