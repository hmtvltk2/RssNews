package com.example.android.persistence.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.persistence.BottomNavigationViewHelper;
import com.example.android.persistence.R;
import com.example.android.persistence.adapter.ViewPagerAdapter;
import com.example.android.persistence.fragment.NewsListFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private BottomNavigationView mBottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initView();
        initViewPager();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViewPager() {
        ViewPagerAdapter mFragmentAdapter = new ViewPagerAdapter (getSupportFragmentManager());
        mFragmentAdapter.addFragment(new NewsListFragment());

        viewPager = findViewById(R.id.view_pager_main);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        //viewPager.setPageTransformer(true, new BottomNavigationPageTransformer());

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // If BottomNavigationView has more than 3 items, using reflection to disable shift mode
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_blue);
                    break;
                case 1:
                    mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_green);
                    break;
                case 2:
                    mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_yellow);
                    break;
                case 3:
                    mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_red);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_blue:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.bottom_navigation_green:
                    //viewPager.setCurrentItem(1);
                    return true;
                case R.id.bottom_navigation_yellow:
                    //viewPager.setCurrentItem(2);
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
