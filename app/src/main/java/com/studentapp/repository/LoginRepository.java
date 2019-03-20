package com.studentapp.repository;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.studentapp.App;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.login.model.Schools;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.model.base.DataWrapper;
import com.studentapp.utils.LogUtils;
import com.studentapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginRepository {
    private static LoginRepository loginRepository;
    private MutableLiveData<DataWrapper<ModelUser>> loginData;
    private MutableLiveData<DataWrapper<List<Schools>>> schoolsData;

    public synchronized static LoginRepository getInstance() {
        if (loginRepository == null) {
            loginRepository = new LoginRepository();
        }
        return loginRepository;
    }

    public LiveData<DataWrapper<ModelUser>> getLoginObservable() {
        loginData = new MutableLiveData<>();
        return loginData;
    }

    public LiveData<DataWrapper<List<Schools>>> getSchoolsObservable() {
        schoolsData = new MutableLiveData<>();
        return schoolsData;
    }

    public void login(String mobile, String password, String schoolId) {
        DataWrapper<ModelUser> dataWrapper = new DataWrapper<>();
        boolean isError = false;
        if (!Utils.IsInternetOn()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_internet));
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
        }
        if (isError) {
            dataWrapper.setState(Constants.STATE_VALIDATION_ERROR);
            loginData.setValue(dataWrapper);
            return;
        }
        dataWrapper.setState(Constants.STATE_LOADING);
        loginData.setValue(dataWrapper);

        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(schoolId)
                .collection(Constants.TBL_USERS)
                .whereEqualTo(Constants.mobile, mobile)
                .whereEqualTo(Constants.password, password)
                .whereEqualTo(Constants.role, Constants.student)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (Objects.requireNonNull(task.getResult()).size() > 0) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                LogUtils.Print(TAG, document.getId() + " ====> " + document.getData());
                                ModelUser modelUser = document.toObject(ModelUser.class);
                                modelUser.setUserId(document.getId());
                                DataWrapper<ModelUser> data = new DataWrapper<>();
                                data.setData(modelUser);
                                data.setState(Constants.STATE_SUCCESS);
                                loginData.postValue(data);
                                break;
                            }
                        } else {
                            DataWrapper<ModelUser> data = new DataWrapper<>();
                            data.setState(Constants.STATE_ERROR);
                            data.setErrorMsg(App.getInstance().getString(R.string.text_login_error));
                            loginData.postValue(data);
                        }
                    } else {
                        DataWrapper<ModelUser> data = new DataWrapper<>();
                        data.setState(Constants.STATE_ERROR);
                        data.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
                        loginData.postValue(data);
                    }
                });
    }


    public void getSchools() {
        DataWrapper<List<Schools>> dataWrapper = new DataWrapper<>();
        boolean isError = false;
        if (!Utils.IsInternetOn()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_internet));
            isError = true;
        }
        if (isError) {
            dataWrapper.setState(Constants.STATE_VALIDATION_ERROR);
            schoolsData.setValue(dataWrapper);
            return;
        }
        dataWrapper.setState(Constants.STATE_LOADING);
        schoolsData.setValue(dataWrapper);
        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (Objects.requireNonNull(task.getResult()).size() > 0) {
                            List<Schools> schoolsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Schools schools = document.toObject(Schools.class);
                                schools.setId("" + document.getId());
                                schoolsList.add(schools);
                            }
                            DataWrapper<List<Schools>> data = new DataWrapper<>();
                            data.setData(schoolsList);
                            data.setState(Constants.STATE_SUCCESS);
                            schoolsData.postValue(data);
                        } else {
                            DataWrapper<List<Schools>> data = new DataWrapper<>();
                            data.setState(Constants.STATE_ERROR);
                            data.setErrorMsg(App.getInstance().getString(R.string.text_no_school));
                            schoolsData.postValue(data);
                        }
                    }
                });
    }
}