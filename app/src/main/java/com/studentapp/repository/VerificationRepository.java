package com.studentapp.repository;


import android.app.Activity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.studentapp.App;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.model.base.DataWrapper;
import com.studentapp.model.verification.ModelVerification;
import com.studentapp.utils.LogUtils;
import com.studentapp.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class VerificationRepository {
    private static VerificationRepository verificationRepository;
    private MutableLiveData<DataWrapper<ModelVerification>> liveData;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ModelUser modelUser;
    private Activity activity;


    public synchronized static VerificationRepository getInstance() {
        if (verificationRepository == null) {
            verificationRepository = new VerificationRepository();
        }
        return verificationRepository;
    }

    public LiveData<DataWrapper<ModelVerification>> getVerificationObservable() {
        liveData = new MutableLiveData<>();
        return liveData;
    }


    public void sendOTP(Activity activity) {
        this.activity = activity;
        DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
        boolean isError = false;
        if (!Utils.IsInternetOn()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_internet));
            isError = true;
        }
        if (isError) {
            dataWrapper.setState(Constants.STATE_VALIDATION_ERROR);
            liveData.setValue(dataWrapper);
            return;
        }
        dataWrapper.setState(Constants.STATE_LOADING);
        liveData.setValue(dataWrapper);

        setUpFireBase();
    }

    public void reSendOTP(Activity activity) {
        this.activity = activity;
        DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
        boolean isError = false;
        if (!Utils.IsInternetOn()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_internet));
            isError = true;
        }
        if (isError) {
            dataWrapper.setState(Constants.STATE_VALIDATION_ERROR);
            liveData.setValue(dataWrapper);
            return;
        }
        dataWrapper.setState(Constants.STATE_LOADING);
        liveData.setValue(dataWrapper);

        ModelUser modelUser = Utils.getUserData();
        if (modelUser != null && mResendToken != null)
            resendVerificationCode("+91" + modelUser.getMobile(), mResendToken);
        else {
            dataWrapper.setState(Constants.STATE_ERROR);
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
            liveData.setValue(dataWrapper);
        }
    }


    /**
     * setup FireBase
     */
    private void setUpFireBase() {
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                LogUtils.Print(TAG, "onVerificationCompleted: " + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                LogUtils.Print(TAG, "onVerificationFailed" + e);
                DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
                dataWrapper.setState(Constants.STATE_ERROR);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_wrong_otp));
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
                } else {
                    dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
                }
                liveData.postValue(dataWrapper);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                LogUtils.Print(TAG, "onCodeSent: " + verificationId);
                LogUtils.Print(TAG, "token: " + token);
                mVerificationId = verificationId;
                mResendToken = token;
                DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
                dataWrapper.setState(Constants.STATE_SUCCESS);
                ModelVerification modelVerification = new ModelVerification();
                modelVerification.setToken(token);
                modelVerification.setVerificationId(verificationId);
                dataWrapper.setData(modelVerification);
                liveData.postValue(dataWrapper);

            }
        };
        ModelUser modelUser = Utils.getUserData();
        if (modelUser != null)
            startPhoneNumberVerification("+91" + modelUser.getMobile());
        else {
            DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
            dataWrapper.setState(Constants.STATE_ERROR);
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
            liveData.setValue(dataWrapper);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        LogUtils.Print(TAG, "signInWithCredential:success");
                        final FirebaseUser user = task.getResult().getUser();
                        setUserVerificationSuccessfull();
                    } else {
                        LogUtils.Print(TAG, "signInWithCredential:failure " + task.getException());
                        LogUtils.Print(TAG, "Verification failed");
                        DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
                        dataWrapper.setState(Constants.STATE_ERROR);
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_wrong_otp));
                        } else {
                            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_server_error));
                        }
                        liveData.postValue(dataWrapper);
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]
        // startCounter();
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        //startCounter();
        //btnResendCode.setEnabled(false);
        //setResendButtonEnableDisable();
    }

    public void verifyPhoneNumberWithCode(String code) {
        DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
        boolean isError = false;
        if (!Utils.IsInternetOn()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_internet));
            isError = true;
        } else if (code == null || code.isEmpty()) {
            dataWrapper.setErrorMsg(App.getInstance().getString(R.string.text_pleade_enter_otp));
            isError = true;
        }
        if (isError) {
            dataWrapper.setState(Constants.STATE_VALIDATION_ERROR);
            liveData.setValue(dataWrapper);
            return;
        }
        dataWrapper.setState(Constants.STATE_LOADING);
        liveData.setValue(dataWrapper);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    /**
     * set status verified for mobile
     */
    private void setUserVerificationSuccessfull() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.verified, true);
        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(Utils.getUserData().getSchoolId())
                .collection(Constants.TBL_USERS)
                .document(Utils.getUserId())
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DataWrapper<ModelVerification> dataWrapper = new DataWrapper<>();
                        dataWrapper.setState(Constants.STATE_SUCCESS);
                        ModelVerification modelVerification = new ModelVerification();
                        modelVerification.setVerified(true);
                        dataWrapper.setData(modelVerification);
                        liveData.postValue(dataWrapper);
                    }
                });
    }


}