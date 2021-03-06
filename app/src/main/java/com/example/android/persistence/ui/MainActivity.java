package com.example.android.persistence.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.persistence.BottomNavigationViewHelper;
import com.example.android.persistence.R;
import com.example.android.persistence.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
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
        if(getSupportActionBar()!= null){
            getSupportActionBar().setTitle(R.string.fragment_rss_list_label);
        }
    }

    private void initViewPager() {
        ViewPagerAdapter mFragmentAdapter = new ViewPagerAdapter (getSupportFragmentManager());
        mFragmentAdapter.addFragment(new RssListFragment());
        mFragmentAdapter.addFragment(new SourceLibraryFragment());
        mFragmentAdapter.addFragment(new TopicFragment());
        mFragmentAdapter.addFragment(new RssBookmarkFragment());

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
                    if(getSupportActionBar()!= null){
                        getSupportActionBar().setTitle(R.string.fragment_rss_list_label);
                    }
                    break;
                case 1:
                    mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_green);
                    if(getSupportActionBar()!= null){
                        getSupportActionBar().setTitle(R.string.rss_source_library_label);
                    }
                    break;
                case 2:
                    mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_yellow);
                    if(getSupportActionBar()!= null){
                        getSupportActionBar().setTitle(R.string.topic_label);
                    }
                    break;
                case 3:
                    mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_red);
                    if(getSupportActionBar()!= null){
                        getSupportActionBar().setTitle(R.string.read_later_label);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
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
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.bottom_navigation_yellow:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.bottom_navigation_red:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    public void viewTopicFragment(){
        mBottomNavigationView.setSelectedItemId(R.id.bottom_navigation_yellow);
    }

}
