package com.studentapp.main.signup;

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
import com.studentapp.main.login.model.Schools;
import com.studentapp.main.otp.Otp;
import com.studentapp.utils.Utils;
import com.studentapp.viewmodel.LoginViewModel;
import com.studentapp.viewmodel.SignUpViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

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
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.tv_male)
    TextView tv_male;
    @BindView(R.id.tv_female)
    TextView tv_female;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_class)
    EditText et_class;
    @BindView(R.id.et_section)
    EditText et_section;
    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.cpb)
    ProgressBar cpb;
    @BindView(R.id.btn_next)
    Button btn_next;
    String gender = "";
    List<Schools> list_schools = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> adpSchools;
    private LoginViewModel loginViewModel;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initViewModel();
        initSpinner();
    }

    /**
     * initialize viewModel
     */
    private void initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        subscribeDataStreams(loginViewModel);


        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        subscribeDataStreamsSignup(signUpViewModel);
    }


    /**
     * init spinner set adapter
     */
    private void initSpinner() {
        if (list.size() > 0)
            list.clear();
        list.add(getString(R.string.text_school));
        adpSchools = new ArrayAdapter<>(SignUp.this, R.layout.lv_spinner_black, list);
        sp_school.setAdapter(adpSchools);

        //get schools from FireBase
        loginViewModel.getSchools();
    }


    /**
     * observe for signup response
     *
     * @param signUpViewModel
     */
    private void subscribeDataStreamsSignup(SignUpViewModel signUpViewModel) {
        Utils.hideKeyBoard(SignUp.this);
        signUpViewModel.getSignUpObservable().observe(this, response -> {
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
                            Utils.saveUserData(response.getData().getUserId(), response.getData());
                            navigateToVerifyActivity();
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
     * observe for login response
     *
     * @param viewModel
     */
    private void subscribeDataStreams(LoginViewModel viewModel) {

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
            btn_next.setEnabled(false);
            btn_next.setAlpha(0.5f);
        } else {
            cpb.setVisibility(View.GONE);
            btn_next.setEnabled(true);
            btn_next.setAlpha(1f);
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


    @OnClick({R.id.btn_next, R.id.tv_male, R.id.tv_female})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                signUpViewModel.signup(et_first_name.getText().toString().trim(),
                        et_last_name.getText().toString().trim(),
                        gender,
                        et_contact_number.getText().toString().trim(),
                        et_password.getText().toString().trim(),
                        (sp_school.getSelectedItemPosition() == 0) ? "" : list_schools.get(sp_school.getSelectedItemPosition() - 1).getId(),
                        et_class.getText().toString().trim(),
                        et_section.getText().toString().trim());

//                NavigatorManager.startNewActivity(SignUp.this, new Intent(SignUp.this, Otp.class));
                break;
            case R.id.tv_male:
                gender = "Male";
                tv_male.setBackground(getResources().getDrawable(R.drawable.bg_gender));
                tv_female.setBackground(getResources().getDrawable(R.drawable.bg_button));
                break;
            case R.id.tv_female:
                gender = "Female";
                tv_male.setBackground(getResources().getDrawable(R.drawable.bg_button));
                tv_female.setBackground(getResources().getDrawable(R.drawable.bg_gender));
                break;
        }

    }

   /* private void callSignUpAPI() {
        if (et_first_name.getText().toString().trim().isEmpty()) {
            et_first_name.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_enter_first_name), et_first_name);
        } else if (et_last_name.getText().toString().trim().isEmpty()) {
            et_last_name.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_enter_last_name), et_last_name);
        } else if (gender.equals("")) {
            tv_male.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_select_gender), et_last_name);
        } else if (et_contact_number.getText().toString().trim().isEmpty()) {
            et_contact_number.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_enter_contact_number), et_contact_number);
        } else if (et_password.getText().toString().trim().isEmpty()) {
            et_password.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_enter_password), et_password);
        } else if (et_password.getText().toString().trim().length() < 6) {
            et_password.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_enter_password_length), et_password);
        } else if (sp_school.getSelectedItemPosition() == 0) {
            sp_school.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_select_school), sp_school);
        } else if (et_class.getText().toString().trim().isEmpty()) {
            et_class.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_enter_class), et_class);
        } else if (et_section.getText().toString().trim().isEmpty()) {
            et_section.requestFocus();
            Utils.showSnackBar(getResources().getString(R.string.text_please_enter_section), et_section);
        } else {
            Utils.hideKeyBoard(SignUp.this);
            checkMobileNumber();
        }
    }

    private void checkMobileNumber() {
        showProgressBar(true);
        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(list_schools.get(sp_school.getSelectedItemPosition() - 1).getId())
                .collection(Constants.TBL_USERS)
                .whereEqualTo(Constants.mobile, et_contact_number.getText().toString().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (Objects.requireNonNull(task.getResult()).size() > 0) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    LogUtils.Print(TAG, document.getId() + " ====> " + document.getData());
                                }
                                showProgressBar(false);
                                Utils.makeToast(SignUp.this, getString(R.string.text_mobile_already_register));
                            } else {
//                                Utils.makeToast(SignUp.this, "Number Not Already Register");
                                registerNewUserIntoFireStore();
                            }
                        }
                    }
                });
    }

    *//**
     * add new user into firestore database
     *//*
    private void registerNewUserIntoFireStore() {
        ModelUser modelUser = new ModelUser();
        modelUser.setFirstName(et_first_name.getText().toString().trim());
        modelUser.setLastName(et_last_name.getText().toString().trim());
        modelUser.setGender(gender);
        modelUser.setMobile(et_contact_number.getText().toString().trim());
        modelUser.setPassword(et_password.getText().toString().trim());
        modelUser.setSchoolId(list_schools.get(sp_school.getSelectedItemPosition() - 1).getId());
        modelUser.setStudentClass(et_class.getText().toString().trim());
        modelUser.setSection(et_section.getText().toString().trim());
        modelUser.setVerified(false);

        // Add a new document with a generated ID
        App.mFirestore.collection(Constants.TBL_SCHOOLS)
                .document(list_schools.get(sp_school.getSelectedItemPosition() - 1).getId())
                .collection(Constants.TBL_USERS)
                .add(modelUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        showProgressBar(false);
                        LogUtils.Print(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Utils.makeToast(SignUp.this, getString(R.string.text_succesfully_register));
                        Utils.saveUserData(documentReference.getId(), modelUser);
                        navigateToVerifyActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showProgressBar(false);
                        LogUtils.Print(TAG, "Error adding document==>" + e);
                        Utils.makeToast(SignUp.this, "Error while register");
                    }
                });

    }*/

    /**
     * launch verify mobile number activity
     */
    private void navigateToVerifyActivity() {
        Intent intent = new Intent(SignUp.this, Otp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
