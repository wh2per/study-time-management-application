package com.example.ready.studytimemanagement;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        FragmentAnalysis fragmentAnalysis = new FragmentAnalysis();
        FragmentTimer fragmentTimer = new FragmentTimer();
        FragmentSetting fragmentSetting = new FragmentSetting();

        adapter.addItem(fragmentAnalysis);
        adapter.addItem(fragmentTimer);
        adapter.addItem(fragmentSetting);

        pager.setAdapter(adapter);
        pager.setCurrentItem(1);

    }
}
