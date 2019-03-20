package com.studentapp.viewmodel;

import android.app.Application;

import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.model.base.DataWrapper;
import com.studentapp.repository.SignUpRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SignUpViewModel extends AndroidViewModel {

    public SignUpViewModel(Application application) {
        super(application);
    }


    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<DataWrapper<ModelUser>> getSignUpObservable() {
        return SignUpRepository.getInstance().getSignUpObservable();
    }

    /**
     * make user login API call and notify observe for data change
     */
    public void signup(String firstName, String lastName, String gender, String mobile, String password, String schoolId, String studentCalss, String section) {
        SignUpRepository.getInstance().signup(firstName, lastName, gender, mobile, password, schoolId, studentCalss, section);
    }
}