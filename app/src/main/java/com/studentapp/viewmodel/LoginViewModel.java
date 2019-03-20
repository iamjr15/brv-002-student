package com.studentapp.viewmodel;

import android.app.Application;

import com.studentapp.main.login.model.Schools;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.model.base.DataWrapper;
import com.studentapp.repository.LoginRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoginViewModel extends AndroidViewModel {

    public LoginViewModel(Application application) {
        super(application);
    }


    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<DataWrapper<ModelUser>> getLoginObservable() {
        return LoginRepository.getInstance().getLoginObservable();
    }

    /**
     * make user login API call and notify observe for data change
     */
    public void login(String mobile, String password, String schoolId) {
        LoginRepository.getInstance().login(mobile, password, schoolId);
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<DataWrapper<List<Schools>>> getSchoolsObservable() {
        return LoginRepository.getInstance().getSchoolsObservable();
    }

    /**
     * make schools call and notify observe for data change
     */
    public void getSchools() {
        LoginRepository.getInstance().getSchools();
    }

}