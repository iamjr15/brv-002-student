package com.studentapp.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.HomeActivity;
import com.studentapp.main.login.model.Schools;
import com.studentapp.main.otp.Otp;
import com.studentapp.utils.Utils;
import com.studentapp.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    @BindView(R.id.iv_school)
    ImageView iv_school;
    @BindView(R.id.sp_school)
    Spinner sp_school;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_country_code)
    TextView tv_country_code;
    @BindView(R.id.et_contact_number)
    EditText et_contact_number;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.cpb)
    ProgressBar cpb;
    @BindView(R.id.btn_login)
    Button btn_login;
    List<Schools> list_schools = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> adpSchools;


    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViewModel();
        initSpinner();
    }

    /**
     * init spinner set adapter
     */
    private void initSpinner() {
        if (list.size() > 0)
            list.clear();
        list.add(getString(R.string.text_school));
        adpSchools = new ArrayAdapter<>(Login.this, R.layout.lv_spinner_black, list);
        sp_school.setAdapter(adpSchools);

        //get schools from FireBase
        loginViewModel.getSchools();
    }


    /**
     * initialize viewModel
     */
    private void initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        subscribeDataStreams(loginViewModel);
    }

    /**
     * observe for login response
     *
     * @param viewModel
     */
    private void subscribeDataStreams(LoginViewModel viewModel) {
        viewModel.getLoginObservable().observe(this, response -> {
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
                        if (response.getData() == null)
                            Utils.makeToast(this, getString(R.string.text_server_error));
                        else {
                            boolean isVerified = response.getData().isVerified();
                            Utils.saveUserData(response.getData().getUserId(), response.getData());
                            if (isVerified) {
                                navigateToHomeActivity();
                            } else {
                                navigateToVerifyActivity();
                            }
                        }
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


        viewModel.getSchoolsObservable().observe(this, response -> {
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
                        if (response.getData() == null)
                            Utils.makeToast(this, getString(R.string.text_server_error));
                        else {
                            setSchoolsData(response.getData());
                        }
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
            btn_login.setEnabled(false);
            btn_login.setAlpha(0.5f);
        } else {
            cpb.setVisibility(View.GONE);
            btn_login.setEnabled(true);
            btn_login.setAlpha(1f);
        }
    }


    /**
     * set schools data
     */
    private void setSchoolsData(List<Schools> data) {
        if (list.size() > 0)
            list.clear();
        if (list_schools.size() > 0)
            list_schools.clear();
        list_schools.addAll(data);
        list.add(getResources().getString(R.string.text_school));
        for (int i = 0; i < list_schools.size(); i++) {
            list.add(list_schools.get(i).getSchoolName());
        }
        adpSchools.notifyDataSetChanged();
    }

    @OnClick({R.id.btn_login})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginViewModel.login(et_contact_number.getText().toString().trim(), et_password.getText().toString().trim(), (sp_school.getSelectedItemPosition() == 0) ? "" : list_schools.get(sp_school.getSelectedItemPosition() - 1).getId());
                break;
        }
    }

    /**
     * launch verify mobile number activity
     */
    private void navigateToVerifyActivity() {
        Intent intent = new Intent(Login.this, Otp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * launch home activity
     */
    private void navigateToHomeActivity() {
        Intent intent = new Intent(Login.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
