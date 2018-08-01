package com.example.ready.studytimemanagement.presenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ready.studytimemanagement.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);


        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        final FragmentAnalysis fragmentAnalysis = new FragmentAnalysis();
        final FragmentTimer fragmentTimer = new FragmentTimer();
        final FragmentSetting fragmentSetting = new FragmentSetting();

        adapter.addItem(fragmentAnalysis);
        adapter.addItem(fragmentTimer);
        adapter.addItem(fragmentSetting);

        pager.setAdapter(adapter);

        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).setIcon(R.drawable.tab_analy).setText("");
        tabs.getTabAt(1).setIcon(R.drawable.tab_timer_select).setText("");
        tabs.getTabAt(2).setIcon(R.drawable.tab_admin).setText("");

        pager.setCurrentItem(1);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos == 0){
                    tab.setIcon(R.drawable.tab_analy_select);
                }else if(pos == 1){
                    tab.setIcon(R.drawable.tab_timer_select);
                }else if(pos == 2){
                    tab.setIcon(R.drawable.tab_admin_select);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos == 0){
                    tab.setIcon(R.drawable.tab_analy);
                }else if(pos == 1){
                    tab.setIcon(R.drawable.tab_timer);
                }else if(pos == 2){
                    tab.setIcon(R.drawable.tab_admin);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
