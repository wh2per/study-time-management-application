package com.example.ready.studytimemanagement.presenter.Activity;

import android.app.Activity;
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

public class AppLockActivity extends AppLockController {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);
        Log.d("lock","다시 실행됨");

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
                Intent intent = new Intent(getApplicationContext(),AppLockService.class); // 이동할 컴포넌트
                //intent.putExtra("AppLock",AppLock);
                startService(intent); // 서비스 시작
                Intent mintent = new Intent(getApplicationContext(),MainActivity.class);
                mintent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mintent);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
