package com.studentapp.repository;

import com.studentapp.App;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.interfaces.InsertOptionInPoll;
import com.studentapp.main.home.interfaces.InsertOptionInUser;

import java.util.HashMap;
import java.util.Map;

public class DisplayDetailedPollRepository {

    public void insertOptionInUser(InsertOptionInUser insertOptionInUser, HashMap<String, String> map, String schoolId, String userId){

        App.mFirestore.collection(Constants.TBL_SCHOOLS).document(schoolId)
                .collection(Constants.TBL_USERS).document(userId)
                .collection(Constants.TBL_ANSWERED_POLLS).document(map.get("pollId")).set(map);

        insertOptionInUser.optionInserted(true);
    }

    public void insertStudentIdInAnsweredPoll(InsertOptionInPoll insertOptionInPoll,HashMap<String, String> map, String schoolId, String pollId){
        App.mFirestore.collection(Constants.TBL_SCHOOLS).document(schoolId)
                .collection(Constants.TBL_POLLS).document(pollId)
                .collection(Constants.TBL_ANSWERED_POLLS).document(map.get("studentId")).set(map);
        insertOptionInPoll.insertOptionInPoll(true);
    }
}
