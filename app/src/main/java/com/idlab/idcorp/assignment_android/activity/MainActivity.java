package com.idlab.idcorp.assignment_android.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.fragment.AdvertiseFragment;
import com.idlab.idcorp.assignment_android.fragment.ContactFragment;
import com.idlab.idcorp.assignment_android.fragment.MyPageFragment;

/**
 * Created by diygame5 on 2017-03-24.
 * Project : Assignment_Android
 */

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private Toolbar mMainToolbar;
    private BottomNavigationView mBottomNavigation;

    private ViewPager mMainViewPager;
    private MainPagerAdapter mMainPagerAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_contact:
                    mMainViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_advertise:
                    mMainViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_my_page:
                    mMainViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
    private ViewPager.OnPageChangeListener mOnViewPagerItemChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //do nothing
        }

        @Override
        public void onPageSelected(int position) {
            if (mBottomNavigation != null) {
                MenuItem menu = mBottomNavigation.getMenu().getItem(position);
                menu.setChecked(true);
            }
            switch (position) {
                case 0:
                    mMainToolbar.setTitle(getString(R.string.title_contact));
                    break;
                case 1:
                    mMainToolbar.setTitle(getString(R.string.title_advertise));
                    break;
                case 2:
                    mMainToolbar.setTitle(getString(R.string.title_my_page));
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //do nothing
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.setting, false);
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mMainToolbar);

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMainViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mMainViewPager.setAdapter(mMainPagerAdapter);
        mMainViewPager.addOnPageChangeListener(mOnViewPagerItemChangeListener);
    }

    //keyboard hide
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {
        Fragment mFragmentContact;
        Fragment mFragmentAdvertise;
        Fragment mFragmentMyPage;

        MainPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentContact = new ContactFragment();
            mFragmentAdvertise = new AdvertiseFragment();
            mFragmentMyPage = new MyPageFragment();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = mFragmentContact;
                    break;
                case 1:
                    fragment = mFragmentAdvertise;
                    break;
                case 2:
                    fragment = mFragmentMyPage;
                    break;
                default:
                    fragment = new Fragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }
}
