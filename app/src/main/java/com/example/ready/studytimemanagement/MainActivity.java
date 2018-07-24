package com.example.ready.studytimemanagement;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        FragmentAnalysis fragmentAnalysis = new FragmentAnalysis();
        FragmentTimer fragmentTimer = new FragmentTimer();
        FragmentSetting fragmentSetting = new FragmentSetting();

        adapter.addItem(fragmentAnalysis);
        adapter.addItem(fragmentTimer);
        adapter.addItem(fragmentSetting);

        pager.setAdapter(adapter);
        pager.setCurrentItem(1);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
