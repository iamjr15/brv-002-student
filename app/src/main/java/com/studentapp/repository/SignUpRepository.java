package com.studentapp.repository;


import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.studentapp.App;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.model.base.DataWrapper;
import com.studentapp.utils.LogUtils;
import com.studentapp.utils.Utils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SignUpRepository {
    private static SignUpRepository signUpRepository;
    private MutableLiveData<DataWrapper<ModelUser>> signupData;

    public synchronized static SignUpRepository getInstance() {
        if (signUpRepository == null) {
            signUpRepository = new SignUpRepository();
        }
        return signUpRepository;
    }

    public LiveData<DataWrapper<ModelUser>> getSignUpObservable() {
        signupData = new MutableLiveData<>();
        return signupData;
    }

    public void signup(String firstName, String lastName, String gender, String mobile, String password, String schoolId, String studentCalss, String section) {
        DataWrapper<ModelUser> dataWrapper = new DataWrapper<>();
        boolean isError = false;
        if (!Utils.IsInternetOn()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_internet));
            isError = true;
        } else if (firstName.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_enter_first_name));
            isError = true;
        } else if (lastName.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_enter_last_name));
            isError = true;
        } else if (gender.isEmpty() && gender.equals("")) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_select_gender));
            isError = true;
        } else if (mobile.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_enter_contact_number));
            isError = true;
        } else if (password.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_enter_password));
            isError = true;
        } else if (password.length() < 6) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_enter_password_length));
            isError = true;
        } else if (schoolId.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_select_school));
            isError = true;
        } else if (studentCalss.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_enter_class));
            isError = true;
        } else if (section.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_enter_section));
            isError = true;
        }
        if (isError) {
            dataWrapper.setState(Constants.STATE_VALIDATION_ERROR);
            signupData.setValue(dataWrapper);
            return;
        }
        dataWrapper.setState(Constants.STATE_LOADING);
        signupData.setValue(dataWrapper);


        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(schoolId)
                .collection(Constants.TBL_USERS)
                .whereEqualTo(Constants.mobile, mobile)
                .whereEqualTo(Constants.role, Constants.student)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (Objects.requireNonNull(task.getResult()).size() > 0) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    LogUtils.Print(TAG, document.getId() + " ====> " + document.getData());
                                }
                                DataWrapper<ModelUser> data = new DataWrapper<>();
                                data.setState(Constants.STATE_ERROR);
                                data.setErrorMsg(App.getInstance().getString(R.string.text_mobile_already_register));
                                signupData.postValue(data);
                            } else {
//                                Utils.makeToast(SignUp.this, "Number Not Already Register");
                                ModelUser modelUser = new ModelUser();
                                modelUser.setFirstName(firstName);
                                modelUser.setLastName(lastName);
                                modelUser.setGender(gender);
                                modelUser.setMobile(mobile);
                                modelUser.setPassword(password);
                                modelUser.setSchoolId(schoolId);
                                modelUser.setStudentClass(studentCalss);
                                modelUser.setSection(section);
                                modelUser.setVerified(false);
                                registerNewUserIntoFireStore(modelUser);
                            }
                        }
                    }
                });
    }

    private void registerNewUserIntoFireStore(ModelUser modelUser) {
        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(modelUser.getSchoolId())
                .collection(Constants.TBL_USERS)
                .add(modelUser)
                .addOnSuccessListener(documentReference -> {
                    modelUser.setUserId(documentReference.getId());
                    Utils.putString(Constants.STUDENT_ID,modelUser.getUserId());
                    Utils.putString(Constants.SCHOOL_ID,modelUser.getSchoolId());

                    DataWrapper<ModelUser> data = new DataWrapper<>();
                    data.setState(Constants.STATE_SUCCESS);
                    data.setData(modelUser);
                    signupData.postValue(data);
                    /*incerementStudentCount(modelUser.getSchoolId(), modelUser.getStudentClass(), modelUser.getSection());*/
                })
                .addOnFailureListener(e -> {

                    DataWrapper<ModelUser> data = new DataWrapper<>();
                    data.setState(Constants.STATE_ERROR);
                    data.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
                    signupData.postValue(data);
                    LogUtils.Print(TAG, "Error adding document==>" + e);
//                        Utils.makeToast(SignUp.this, "Error while register");
                });
    }

/*    private void incerementStudentCount(String schoolId,String className, String divisionName){

       DocumentReference documentReference = App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(schoolId)
                .collection(Constants.TBL_CLASS)
                .document(className)
                .collection(Constants.TBL_DIVISION)
                .document(divisionName);

        App.mFirestore.runTransaction(new Transaction.Function<Integer>() {
            @Nullable
            @Override
            public Integer apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                int count = documentSnapshot.get("count", Integer.class) == null ? 1 : (documentSnapshot.get("count", Integer.class) + 1);

                return count;
            }

        }).addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                Log.d("WASTE","Student Count updated to: "+integer);
            }
        });
    }*/

}