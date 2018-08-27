package com.example.ready.studytimemanagement.presenter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Adapter.AdapterApplock;
import com.example.ready.studytimemanagement.presenter.AppLockService;
import com.example.ready.studytimemanagement.presenter.Controller.AppLockController;
import com.example.ready.studytimemanagement.presenter.Item.ItemApplock;

import java.util.ArrayList;

public class AppLockActivity extends AppLockController {
    private ArrayList<ItemApplock> applocks;
    private Intent mainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);

        // load applist from main activity
        applocks = LoadAppList(this);
        //Intent intent = getIntent();
        //applocks = intent.getParcelableArrayListExtra("applist");

        Toolbar mToolbar  = findViewById(R.id.appListToolbar);
        mToolbar.setTitle("앱 목록");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.appLockList);

        final AdapterApplock adapterApplock = new AdapterApplock(this.getApplicationContext(),applocks);

        listView.setAdapter(adapterApplock);
        mainIntent = new Intent(getApplicationContext(),MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(mainIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                Intent intent = new Intent(getApplicationContext(),AppLockService.class); // 이동할 컴포넌트
                //startService(intent); // 서비스 시작
                startActivity(mainIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
