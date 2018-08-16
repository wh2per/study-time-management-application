package com.example.ready.studytimemanagement.presenter.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Adapter.AdapterApplock;
import com.example.ready.studytimemanagement.presenter.AppLockController;
import com.example.ready.studytimemanagement.presenter.Item.ItemApplock;

public class AppLockActivity extends AppLockController {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);

        Toolbar mToolbar  = findViewById(R.id.appListToolbar);
        mToolbar.setTitle("앱 목록");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.appLockList);

        final AdapterApplock adapterApplock = new AdapterApplock(this.getApplicationContext());

        LoadAppList(this,adapterApplock);

        listView.setAdapter(adapterApplock);
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
