package com.example.ready.studytimemanagement.presenter.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Adapter.MainPagerAdapter;
import com.example.ready.studytimemanagement.presenter.Fragment.FragmentAnalysis;
import com.example.ready.studytimemanagement.presenter.Fragment.FragmentSetting;
import com.example.ready.studytimemanagement.presenter.Fragment.FragmentTimer;
import com.example.ready.studytimemanagement.presenter.Item.ItemApplock;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        //Intent appListIntent = getIntent();
        //ArrayList <ItemApplock> itemApplocks  = appListIntent.getParcelableArrayListExtra("appList");
        //Log.v("listcheck",itemApplocks.get(1).getTitle());

        /*
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);*/
    /*
        Intent intent = new Intent(this.getIntent());
        String id = intent.getStringExtra("ID");
        String email = intent.getStringExtra("EMAIL");
*/

        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        final FragmentAnalysis fragmentAnalysis = new FragmentAnalysis();
        final FragmentTimer fragmentTimer = new FragmentTimer();
//        final FragmentTimer fragmentTimer = new FragmentTimer(itemApplocks);
        fragmentTimer.mainActivity = this;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAffinity(this);
    }
}
