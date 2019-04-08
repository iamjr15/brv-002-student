package com.studentapp.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.detailedPoll.DisplayDetailedPollActivity;
import com.studentapp.main.home.adapter.ViewPagerAdapter;
import com.studentapp.main.home.fragments.AccountFragment;
import com.studentapp.main.home.fragments.DetailedPollFragment;
import com.studentapp.main.home.fragments.HomeFragment;
import com.studentapp.main.home.fragments.MessageFragment;
import com.studentapp.main.home.fragments.PollListFragment;
import com.studentapp.main.home.interfaces.IPollSelected;
import com.studentapp.main.home.model.PollsModel;
import com.studentapp.main.signup.model.ModelUser;
import com.studentapp.model.base.DataWrapper;
import com.studentapp.utils.LogUtils;
import com.studentapp.utils.Utils;
import com.studentapp.viewmodel.HomeViewModel;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener,
        IPollSelected {
    private static final String TAG = "HomeActivity";
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
  /*  @BindView(R.id.cpb)
    ProgressBar cpb;*/

    //Tab adapter
    private ViewPagerAdapter adapter;


    private HomeFragment homeFragment;
    private PollListFragment pollListFragment;
    private AccountFragment accountFragment;

    private MenuItem prevMenuItem;

    private HomeViewModel homeViewModel;
    private String studentId, schoolId;
    private ModelUser modelUser1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        //visibleHideProgressBar(true);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        vp.addOnPageChangeListener(this);

        studentId = Utils.getString(Constants.STUDENT_ID);
        schoolId = Utils.getString(Constants.SCHOOL_ID);

        Log.d("waste","UserId: "+ Utils.getString(Constants.STUDENT_ID)+"SchoolId: "+Utils.getString(Constants.SCHOOL_ID));
        initializeViewModel(homeViewModel);
    }

  /*  private void visibleHideProgressBar(boolean isVisible) {
        if (isVisible) {
            cpb.setVisibility(View.VISIBLE);
        } else {
            cpb.setVisibility(View.GONE);
        }
    }*/

    private void initializeViewModel(HomeViewModel viewModel){
        viewModel.getUserLiveData(schoolId,studentId).observe(this, new Observer<ModelUser>() {
            @Override
            public void onChanged(ModelUser modelUser) {
                modelUser1 = modelUser;
                setupViewPager(vp, modelUser);
                Log.d("waste","modelUser: "+ modelUser.getStudentClass()+"---"+modelUser.getSection());

            }
        });
    }

    private void setupViewPager(ViewPager viewPager, ModelUser modelUser) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        pollListFragment = PollListFragment.getInstance(modelUser);

        homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle = new Bundle();
        bundle.putInt(Constants.flag, Constants.FLAG_HOME);
        homeFragment.setArguments(bundle);

        accountFragment = new AccountFragment();

        adapter.addFragment(pollListFragment);

        adapter.addFragment(homeFragment);
        adapter.addFragment(accountFragment);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(1);
        //visibleHideProgressBar(false);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_message:
                vp.setCurrentItem(0);
                break;
            case R.id.action_home:
                vp.setCurrentItem(1);
                break;
            case R.id.action_account:
                vp.setCurrentItem(2);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("waste","position: "+position);
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(1).setChecked(false);
        }

            bottomNavigationView.getMenu().getItem(position).setChecked(true);

            prevMenuItem = bottomNavigationView.getMenu().getItem(position);

        LogUtils.Print(TAG, "onPageSelected:- " + position);
//        setHeaderTitle(position, getBackStackCount());

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + vp.getId() + ":" + vp.getCurrentItem());
        if (fragment != null) {
            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            int count = childFragmentManager.getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
            } else {
                childFragmentManager.popBackStack();
//                setHeaderTitle(viewPager.getCurrentItem(), childFragmentManager.getBackStackEntryCount() - 1);
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void pollSelected(PollsModel data, int position) {
        //DetailPage, Poll through Intent
        Log.d("WASTE","HomeActivity pollSelected");
        Intent intent = new Intent(this, DisplayDetailedPollActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("position",position);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PollsModel pollsModel = (PollsModel) data.getSerializableExtra("answer");
        int position = data.getIntExtra("position",-1);
        //setupViewPager(vp,modelUser1);

        pollListFragment.optionClickedSaved(pollsModel,position);
    }
}
