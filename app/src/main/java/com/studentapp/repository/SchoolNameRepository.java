package com.studentapp.repository;


import com.google.firebase.firestore.DocumentSnapshot;
import com.studentapp.App;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.login.model.Cities;
import com.studentapp.main.login.model.Schools;
import com.studentapp.model.base.DataWrapper;
import com.studentapp.model.school.ModelSchool;
import com.studentapp.utils.LogUtils;
import com.studentapp.utils.Utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SchoolNameRepository {
    private static SchoolNameRepository schoolNameRepository;
    private MutableLiveData<DataWrapper<ModelSchool>> schoolData;

    public synchronized static SchoolNameRepository getInstance() {
        if (schoolNameRepository == null) {
            schoolNameRepository = new SchoolNameRepository();
        }
        return schoolNameRepository;
    }

    public LiveData<DataWrapper<ModelSchool>> getSchoolNameObservable() {
        schoolData = new MutableLiveData<>();
        return schoolData;
    }

    public void schoolname(String schoolId) {
        DataWrapper<ModelSchool> dataWrapper = new DataWrapper<>();
        boolean isError = false;
        if (!Utils.IsInternetOn()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_internet));
            isError = true;
        } else if (schoolId.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_please_select_school));
            isError = true;
        }
        if (isError) {
            dataWrapper.setState(Constants.STATE_VALIDATION_ERROR);
            schoolData.setValue(dataWrapper);
            return;
        }
        dataWrapper.setState(Constants.STATE_LOADING);
        schoolData.setValue(dataWrapper);

        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(schoolId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            Schools schools = document.toObject(Schools.class);
                            if (schools != null) {
                                schools.setId("" + document.getId());
//                                        Utils.makeToast(getActivity(), schools.getSchoolName());
                                showCityName(schools.getCityNameRef(), schools.getSchoolName());
                            }
                        }
                    } else {
                        DataWrapper<ModelSchool> data = new DataWrapper<>();
                        data.setState(Constants.STATE_ERROR);
                        data.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
                        schoolData.postValue(data);
                        LogUtils.Print(TAG, "Error getting documents.==>" + task.getException());
                    }
                });
    }

    /**
     * show city name
     */
    private void showCityName(String cityNameRef, String schoolname) {
        App.mFirestore.document(cityNameRef)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            Cities city = document.toObject(Cities.class);
                            if (city != null) {
                                DataWrapper<ModelSchool> dataWrapper = new DataWrapper<>();
                                dataWrapper.setState(Constants.STATE_SUCCESS);
                                ModelSchool data = new ModelSchool();
                                data.setSchoolName(schoolname);
                                data.setCityName(city.getName());
                                dataWrapper.setData(data);
                                schoolData.postValue(dataWrapper);
                            } else {
                                DataWrapper<ModelSchool> data = new DataWrapper<>();
                                data.setState(Constants.STATE_ERROR);
                                data.setErrorMsg(App.getInstance().getString(R.string.text_no_school));
                                schoolData.postValue(data);
                            }
                        }
                    } else {
                        DataWrapper<ModelSchool> data = new DataWrapper<>();
                        data.setState(Constants.STATE_ERROR);
                        data.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
                        schoolData.postValue(data);
                        LogUtils.Print(TAG, "Error getting documents.==>" + task.getException());
                    }
                });
    }
}