package com.example.greddy.weatherapp.ui.splash;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.greddy.weatherapp.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private Button mSkip, mNextOrExplore;
    private int mSelectedPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUi();
    }

    private void loadUi() {
        setContentView(R.layout.splash_activity_content_view);
        mViewPager = (ViewPager) findViewById(R.id.splash_view_pager);
        mSkip = (Button) findViewById(R.id.skip);
        mNextOrExplore = (Button) findViewById(R.id.next_or_explore);

        mViewPager.setPageMargin((int) getResources().getDimension(R.dimen.global_default_dimen_30px));
        mViewPager.setClipToPadding(false);
        int viewPagerPadding = (int) getResources().getDimension(R.dimen.global_default_dimen_30px);
        mViewPager.setPadding(viewPagerPadding, 0, viewPagerPadding, 0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSelectedPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mSkip.setOnClickListener(this);
        mNextOrExplore.setOnClickListener(this);

        SplashAdapter splashAdapter = new SplashAdapter(this);
        mViewPager.setAdapter(splashAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                break;
            case R.id.next_or_explore:
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
