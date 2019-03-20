package com.studentapp.main.home;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.adapter.ViewPagerAdapter;
import com.studentapp.main.home.fragments.AccountFragment;
import com.studentapp.main.home.fragments.HomeFragment;
import com.studentapp.main.home.fragments.MessageFragment;
import com.studentapp.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private static final String TAG = "HomeActivity";
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    //Tab adapter
    private ViewPagerAdapter adapter;

    HomeFragment homeFragment;
    MessageFragment messageFragment;
    AccountFragment accountFragment;

    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        vp.addOnPageChangeListener(this);
        setupViewPager(vp);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        messageFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.flag, Constants.FLAG_MESSAGE);
        messageFragment.setArguments(bundle);

        homeFragment = new HomeFragment();
        bundle = new Bundle();
        bundle.putInt(Constants.flag, Constants.FLAG_HOME);
        homeFragment.setArguments(bundle);

        accountFragment = new AccountFragment();

//        propertyListFragmentCalendar.setOnHeaderChangeListener(this);
//        propertyListFragmentInvoice.setOnHeaderChangeListener(this);
//        accountFragment.setOnHeaderChangeListener(this);

        adapter.addFragment(messageFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(accountFragment);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(1);
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
}
