package com.studentapp.repository;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.studentapp.App;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.interfaces.GetUser;
import com.studentapp.main.signup.model.ModelUser;

import java.util.HashMap;
import java.util.Map;

public class HomeRepository {

    public void getUserForSpecificId(GetUser getUser, String schollId, String studentId){
        App.mFirestore.collection(Constants.TBL_SCHOOLS).document(schollId)
                .collection(Constants.TBL_USERS).document(studentId).get()
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("waste", "DocumentSnapshot data: " + document.getData());

                                    ModelUser modelUser = new ModelUser();
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map = document.getData();

                                    modelUser.setFirstName(map.get(Constants.firstName).toString());
                                    modelUser.setStudentClass(map.get(Constants.studentClass).toString());
                                    modelUser.setSection(map.get(Constants.section).toString());
                                    modelUser.setSchoolId(map.get(Constants.SCHOOL_ID).toString());
                                    modelUser.setGender(map.get(Constants.gender).toString());
                                    modelUser.setMobile(map.get(Constants.mobile).toString());
                                    modelUser.setPassword(map.get(Constants.password).toString());
                                    modelUser.setLastName(map.get(Constants.lastName).toString());

                                    getUser.getUserData(modelUser);
                                } else {
                                    Log.d("waste", "No such document");
                                }
                            } else {
                                Log.d("waste", "get failed with "+task.getException());
                            }
                        });
    }
}
