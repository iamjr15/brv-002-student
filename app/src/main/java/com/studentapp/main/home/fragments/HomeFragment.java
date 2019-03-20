package com.studentapp.main.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.utils.Utils;
import com.studentapp.viewmodel.SchoolNameViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_school_name)
    TextView tv_school_name;

    private SchoolNameViewModel schoolNameViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        initViewModel();
        setUserData();

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * initialize viewModel
     */
    private void initViewModel() {
        schoolNameViewModel = ViewModelProviders.of(this).get(SchoolNameViewModel.class);
        subscribeDataStreams(schoolNameViewModel);

    }

    /**
     * observe for login response
     *
     * @param viewModel
     */
    private void subscribeDataStreams(SchoolNameViewModel viewModel) {

        viewModel.getSchoolNameObservable().observe(this, response -> {
            if (response != null) {
                switch (response.getState()) {
                    case Constants.STATE_LOADING:
//                        visibleHideProgressBar(true);
                        break;
                    case Constants.STATE_VALIDATION_ERROR:
                        Utils.makeToast(getActivity(), response.getErrorMsg());
                        break;
                    case Constants.STATE_SUCCESS:
//                        visibleHideProgressBar(false);
                        if (response.getData() == null)
                            Utils.makeToast(getActivity(), getString(R.string.text_server_error));
                        else {
                            tv_school_name.setText(String.format("%s, %s", response.getData().getSchoolName(), response.getData().getCityName()));
                        }
                        break;
                    case Constants.STATE_ERROR:
//                        visibleHideProgressBar(false);
                        Utils.makeToast(getActivity(), response.getErrorMsg());
                        break;
                }
            }
        });

    }

    /**
     * set user data
     */
    private void setUserData() {
        ModelUser modelUser = Utils.getUserData();

        if (modelUser != null) {
            tv_user_name.setText(String.format("%s,%s %s", getString(R.string.text_hey), modelUser.getFirstName(), modelUser.getLastName()));
            schoolNameViewModel.schoolname(modelUser.getSchoolId());
        }
    }

    /* */

    /**
     * show city name
     *//*
    private void showCityName(String cityNameRef, String schoolname) {
        App.mFirestore.document(cityNameRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                Cities city = document.toObject(Cities.class);
                                if (city != null) {
//                                    Utils.makeToast(getActivity(), city.getName());
                                    tv_school_name.setText(String.format("%s, %s", schoolname, city.getName()));
                                }
                            }


                        } else {
                            LogUtils.Print(TAG, "Error getting documents.==>" + task.getException());
                        }
                    }
                });
    }*/
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
