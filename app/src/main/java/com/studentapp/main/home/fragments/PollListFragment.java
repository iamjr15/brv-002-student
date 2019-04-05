package com.studentapp.main.home.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.adapter.PollListAdapter;
import com.studentapp.main.home.interfaces.IPollSelected;
import com.studentapp.main.home.interfaces.OptionClickUpdate;
import com.studentapp.main.home.model.PollsModel;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.viewmodel.PollListFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class PollListFragment extends Fragment
implements View.OnClickListener, OptionClickUpdate {

    private PollListAdapter pollListAdapter;
    private IPollSelected iPollSelected;
    private PollListFragmentViewModel pollListFragmentViewModel;
    private  ModelUser modelUser;
    private List<PollsModel> pollsModelList;

    public static PollListFragment getInstance(ModelUser modelUser){

        PollListFragment pollListFragment = new PollListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.flag, Constants.FLAG_POLL);
        bundle.putSerializable(Constants.DATA_USERS,modelUser);
        pollListFragment.setArguments(bundle);
        return pollListFragment;
    }
    public PollListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poll_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("waste","onViewCreated: ");

        iPollSelected = (IPollSelected) getActivity();


        RecyclerView recyclerView = getView().findViewById(R.id.pollRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,
                false));
        pollListAdapter = new PollListAdapter(getContext(),new ArrayList<PollsModel>(), this);
        recyclerView.setAdapter(pollListAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        iPollSelected = (IPollSelected)activity;
        pollListFragmentViewModel = ViewModelProviders.of(this).get(PollListFragmentViewModel.class);
        Bundle bundle = getArguments();
        modelUser = (ModelUser) bundle.getSerializable(Constants.DATA_USERS);
        Log.d("waste","onAttach all Information: "+modelUser.getSchoolId()+"---"+modelUser.getStudentClass()+"---"+modelUser.getSection());

        pollListFragmentViewModel.getPollIdList(modelUser.getSchoolId(),
                modelUser.getStudentClass(),modelUser.getSection()).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                getAllPollList(list);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void getAllPollList(List<String> pollIdList){
        pollListFragmentViewModel.getPollList(pollIdList,modelUser.getSchoolId())
                .observe(this, new Observer<List<PollsModel>>() {
            @Override
            public void onChanged(List<PollsModel> pollsModels) {
                pollsModelList = pollsModels;
                pollListAdapter.updateList(pollsModels);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int position = Integer.parseInt(String.valueOf(view.getTag()));
        Log.d("WASTE","Selected: "+pollListAdapter.getItem(position));
        iPollSelected.pollSelected(pollListAdapter.getItem(position),position);
    }

    @Override
    public void optionClickedSaved(PollsModel pollsModel, int position) {
       pollsModelList.get(position).setAnswered(true);
        pollListAdapter.updateList(pollsModelList);
    }
}
