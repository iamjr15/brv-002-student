package com.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.studentapp.main.home.HomeActivity;
import com.studentapp.main.otp.Otp;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.utils.NavigatorManager;
import com.studentapp.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private static int SPLASH_WAIT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BGThread();
    }

    private void BGThread() {
        new Handler().postDelayed(() -> {
            if (Utils.getUserId().isEmpty() || Utils.getUserData() == null) {
                NavigatorManager.startNewActivity(Splash.this,
                        new Intent(Splash.this, MainActivity.class));
            } else {
                ModelUser modelUser = Utils.getUserData();
                if (modelUser.isVerified()) {
                    NavigatorManager.startNewActivity(Splash.this,
                            new Intent(Splash.this, HomeActivity.class));
                } else {
                    NavigatorManager.startNewActivity(Splash.this,
                            new Intent(Splash.this, Otp.class));
                }
            }
            finish();
        }, SPLASH_WAIT);
    }
}
