package com.example.ready.studytimemanagement.presenter.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Adapter.MainPagerAdapter;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;
import com.example.ready.studytimemanagement.presenter.Fragment.FragmentAnalysis;
import com.example.ready.studytimemanagement.presenter.Fragment.FragmentSetting;
import com.example.ready.studytimemanagement.presenter.Fragment.FragmentTimer;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private LogfileController lfc;
    private Context cont;
    final String filename = "userlog.txt";

    private String sns = "";
    private String id = "";
    private String nickname = "";
    private int age = 0;
    private String job = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lfc = new LogfileController();
        cont = getApplicationContext();

        String line = lfc.ReadLogFile(cont, filename);
        StringTokenizer tokens = new StringTokenizer(line, ",");

        this.setSns(tokens.nextToken());
        Log.e("SNS in Login", this.getSns());
        if(this.getSns().equals("4") == false) {
            this.setId(tokens.nextToken());

            this.setNickname(tokens.nextToken());
            this.setAge(Integer.parseInt(tokens.nextToken()));
            this.setJob(tokens.nextToken());
        }
        Log.e("logfile in mainactivity", this.getNickname()+this.getJob());
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        final FragmentAnalysis fragmentAnalysis = new FragmentAnalysis();
        final FragmentTimer fragmentTimer = new FragmentTimer();

//        final FragmentTimer fragmentTimer = new FragmentTimer(itemApplocks);
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
        finishAffinity();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSns() { return sns; }

    public void setSns(String sns) { this.sns = sns; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String getJob() { return job; }

    public void setJob(String job) { this.job = job; }
}
