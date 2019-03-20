package com.studentapp.viewmodel;

import android.app.Activity;
import android.app.Application;

import com.studentapp.model.base.DataWrapper;
import com.studentapp.model.verification.ModelVerification;
import com.studentapp.repository.VerificationRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class VerificationViewModel extends AndroidViewModel {

    public VerificationViewModel(Application application) {
        super(application);
    }


    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<DataWrapper<ModelVerification>> getVerificationObservable() {
        return VerificationRepository.getInstance().getVerificationObservable();
    }

    /**
     * send OTP call and notify observe for data change
     */
    public void sendOTP(Activity activity) {
        VerificationRepository.getInstance().sendOTP(activity);
    }

    /**
     * send OTP call and notify observe for data change
     */
    public void verifyOTP(String code) {
        VerificationRepository.getInstance().verifyPhoneNumberWithCode(code);
    }

    /**
     * resend OTP call and notify observe for data change
     */
    public void resendOTP(Activity activity) {
        VerificationRepository.getInstance().reSendOTP(activity);
    }

}