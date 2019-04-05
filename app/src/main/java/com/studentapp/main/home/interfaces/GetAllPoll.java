package com.studentapp.main.home.interfaces;

import com.studentapp.main.home.model.PollsModel;

import java.util.List;

public interface GetAllPoll {
    void getPollListForSpecificStudent(List<PollsModel> pollsModelList);
}
