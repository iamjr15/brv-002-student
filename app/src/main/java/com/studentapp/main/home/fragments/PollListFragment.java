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
import com.studentapp.main.home.adapter.PollListAdapter;
import com.studentapp.main.home.interfaces.IPollSelected;
import com.studentapp.main.home.model.PollsModel;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.viewmodel.PollListFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class PollListFragment extends Fragment
implements View.OnClickListener {

    private PollListAdapter pollListAdapter;
    private IPollSelected iPollSelected;
    private PollListFragmentViewModel pollListFragmentViewModel;

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

        iPollSelected = (IPollSelected) getActivity();
        List<String> list = new ArrayList<>();
        list.add("what is your name.?");
        list.add("what is your favourite color.?");
        list.add("Have you done your home work.?");
        list.add("which subject is your favourite.?");

        //pollListFragmentViewModel.getPollIdList()

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
        ModelUser modelUser = (ModelUser) bundle.getSerializable("userDetails");
        pollListFragmentViewModel.getPollIdList(modelUser.getSchoolId(),
                modelUser.getStudentClass(),modelUser.getSection()).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                Log.d("waste","List of id: "+list.get(0));
            }
        });
    }

    @Override
    public void onClick(View view) {


        int position = Integer.parseInt(String.valueOf(view.getTag()));
        Log.d("WASTE","Selected: "+pollListAdapter.getItem(position));
        iPollSelected.pollSelected(pollListAdapter.getItem(position));
    }
}
