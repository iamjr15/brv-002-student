package com.studentapp.viewmodel;

import android.app.Application;

import com.studentapp.model.base.DataWrapper;
import com.studentapp.model.school.ModelSchool;
import com.studentapp.repository.SchoolNameRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SchoolNameViewModel extends AndroidViewModel {

    public SchoolNameViewModel(Application application) {
        super(application);
    }


    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<DataWrapper<ModelSchool>> getSchoolNameObservable() {
        return SchoolNameRepository.getInstance().getSchoolNameObservable();
    }

    /**
     * make user login API call and notify observe for data change
     */
    public void schoolname(String schoolId) {
        SchoolNameRepository.getInstance().schoolname(schoolId);
    }
}