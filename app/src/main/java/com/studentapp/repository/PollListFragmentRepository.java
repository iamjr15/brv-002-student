package com.studentapp.repository;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.studentapp.App;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.interfaces.GetAllPoll;
import com.studentapp.main.home.interfaces.SetIdListOfPolls;
import com.studentapp.main.home.model.PollsModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PollListFragmentRepository {

    public void getListOfPollID(SetIdListOfPolls setIdListOfPolls, String schoolId,
                                String classId, String divisionId){
        List<String> pollIdList = new ArrayList<String>();

        App.mFirestore.collection(Constants.TBL_SCHOOLS).document(schoolId)
                .collection(Constants.TBL_CLASS).document(classId).collection(Constants.TBL_DIVISION)
                .document(divisionId).collection(Constants.TBL_POLLS).get()
                .addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("waste", document.getId() + " => " + document.getData());
                            Map<String, Object> map = new HashMap<String, Object>();
                            map = document.getData();

                            pollIdList.add(map.get(Constants.POLL_ID).toString());

                        }
                        setIdListOfPolls.setPollId(pollIdList);
                    } else {
                        Log.d("waste", "Error getting documents: ", task.getException());
                    }
                }

        );

    }

    public void getAllPoll(GetAllPoll getAllPoll, List<String> pollIdList, String schoolId){

        List<PollsModel> polls = new ArrayList<PollsModel>();

        App.mFirestore.collection(Constants.TBL_SCHOOLS).document(schoolId)
                .collection(Constants.TBL_POLLS).get()
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    for (int i=0; i<pollIdList.size();i++) {

                                       if (pollIdList.get(i).equals(document.getId())) {
                                           PollsModel pollsModel = document.toObject(PollsModel.class);
                                           pollsModel.setPollId(document.getId());
                                           Log.d("WASTE", "PollModel: " + pollsModel.getQuestion());
                                           Log.d("WASTE", "List ID: " + pollIdList.get(i) + " Document ID: " + document.getId());

                                           polls.add(pollsModel);
                                       }

                                    }
                                }
                                getAllPoll.getPollListForSpecificStudent(polls);

                            } else {
                                Log.d("waste", "Error getting documents: ", task.getException());
                            }
                        });
    }
}
