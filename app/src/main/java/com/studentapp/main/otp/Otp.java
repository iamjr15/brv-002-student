package com.studentapp.main.otp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studentapp.MainActivity;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.HomeActivity;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.utils.NavigatorManager;
import com.studentapp.utils.PinView;
import com.studentapp.utils.Utils;
import com.studentapp.viewmodel.VerificationViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Otp extends AppCompatActivity {
    private final String TAG = "Otp";
    @BindView(R.id.otp_view)
    PinView otp_view;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.cpb)
    ProgressBar cpb;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.btn_resend)
    Button btn_resend;
    private ModelUser modelUser;

    private VerificationViewModel verificationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        modelUser = Utils.getUserData();
        if (modelUser != null) {
            tv_number.setText(String.format("+91 %s", modelUser.getMobile()));
        }
        initViewModel();
        verificationViewModel.sendOTP(Otp.this);
    }

    /**
     * initialize viewModel
     */
    private void initViewModel() {
        verificationViewModel = ViewModelProviders.of(this).get(VerificationViewModel.class);
        subscribeDataStreams(verificationViewModel);
    }

    /**
     * observe for OTP verification response
     *
     * @param viewModel
     */
    private void subscribeDataStreams(VerificationViewModel viewModel) {
        viewModel.getVerificationObservable().observe(this, response -> {
            if (response != null) {
                switch (response.getState()) {
                    case Constants.STATE_LOADING:
                        visibleHideProgressBar(true);
                        break;
                    case Constants.STATE_VALIDATION_ERROR:
                        Utils.makeToast(this, response.getErrorMsg());
                        break;
                    case Constants.STATE_SUCCESS:
                        visibleHideProgressBar(false);
                        if (response.getData() != null) {
                            boolean isVerified = response.getData().isVerified();
                            if (isVerified) {
                                ModelUser modelUser = Utils.getUserData();
                                if (modelUser != null)
                                    modelUser.setVerified(true);
                                Utils.saveUserData(Utils.getUserId(), modelUser);
                                navigateToHomeActivity();
                            } else {
                                Utils.makeToast(Otp.this, getString(R.string.text_otp_check_in_sms));
                            }
                        } else
                            Utils.makeToast(this, getString(R.string.text_server_error));
                        break;
                    case Constants.STATE_ERROR:
                        visibleHideProgressBar(false);
                        Utils.makeToast(this, response.getErrorMsg());
                        break;
                }
            } else {
                visibleHideProgressBar(true);
            }
        });
    }

    /**
     * visible progressBar or hide if visible
     *
     * @param isVisible true if need to visible else false
     */
    private void visibleHideProgressBar(boolean isVisible) {
        if (isVisible) {
            cpb.setVisibility(View.VISIBLE);
            btn_next.setEnabled(false);
            btn_next.setAlpha(0.5f);
            btn_resend.setEnabled(false);
            btn_resend.setAlpha(0.5f);
        } else {
            cpb.setVisibility(View.GONE);
            btn_next.setEnabled(true);
            btn_next.setAlpha(1f);
            btn_resend.setEnabled(true);
            btn_resend.setAlpha(1f);
        }
    }


    /**
     * launch home activity
     */
    private void navigateToHomeActivity() {
        Intent intent = new Intent(Otp.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @OnClick({R.id.btn_next, R.id.btn_resend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                verificationViewModel.verifyOTP(otp_view.getText().toString().trim());
                break;
            case R.id.btn_resend:
                verificationViewModel.resendOTP(Otp.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Otp.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NavigatorManager.startNewActivity(Otp.this, intent);
    }
}
