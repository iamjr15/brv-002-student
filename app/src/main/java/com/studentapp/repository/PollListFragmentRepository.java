package com.studentapp.repository;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.studentapp.App;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.interfaces.SetIdListOfPolls;
import com.studentapp.main.home.model.PollsModel;

import java.util.ArrayList;
import java.util.List;

public class PollListFragmentRepository {

    public void getListOfPollID(SetIdListOfPolls setIdListOfPolls, String schoolId,
                                String classId, String divisionId){
        List<String> pollIdList = new ArrayList<String>();

        App.mFirestore.collection(Constants.TBL_SCHOOLS).document(schoolId)
                .collection(Constants.TBL_CLASS).document(classId).collection(Constants.TBL_DIVISION)
                .document(divisionId).collection(Constants.TBL_POLLS).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("waste", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d("waste", "Error getting documents: ", task.getException());
                    }
                }

        );

    }
}
