package com.studentapp.model.verification;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import lombok.Data;

@Data
public class ModelVerification {
    private String verificationId = "";
    private PhoneAuthProvider.ForceResendingToken token = null;
    private PhoneAuthCredential phoneAuthCredential = null;
    private FirebaseException firebaseException = null;
    private boolean verified = false;
}
