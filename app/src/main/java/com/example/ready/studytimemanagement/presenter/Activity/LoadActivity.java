package com.example.ready.studytimemanagement.presenter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Controller.AppLockController;
import com.example.ready.studytimemanagement.presenter.Controller.LoginController;
import com.example.ready.studytimemanagement.presenter.Item.ItemApplock;

import java.util.ArrayList;

public class LoadActivity extends AppCompatActivity{
    private ArrayList<ItemApplock> appLocks;
    private AppLockController alc;
    private LoginController lgc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        alc = new AppLockController();
        lgc = new LoginController();
        //appLocks = alc.LoadAppList(this);
        //Log.v("listcheck",appLocks.get(1).getTitle());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);

                //check login log
                if(lgc.isExistUserLog()){
                    Intent loginIntent = new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(loginIntent);
                }else{
                    Log.v("isExistedUserLog","true");
                }


                finish();
            }
        }, 2000);

    }
}
