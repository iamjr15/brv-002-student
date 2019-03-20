package com.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.studentapp.main.login.Login;
import com.studentapp.main.signup.SignUp;
import com.studentapp.utils.NavigatorManager;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_signup})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                NavigatorManager.startNewActivity(MainActivity.this, new Intent(MainActivity.this, Login.class));
                break;
            case R.id.tv_signup:
                NavigatorManager.startNewActivity(MainActivity.this, new Intent(MainActivity.this, SignUp.class));
                break;
        }
    }

}
