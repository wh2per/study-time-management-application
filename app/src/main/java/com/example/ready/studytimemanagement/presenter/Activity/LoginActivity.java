package com.example.ready.studytimemanagement.presenter.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;
import com.example.ready.studytimemanagement.presenter.Controller.LoginController;

import java.io.FileOutputStream;
import java.util.StringTokenizer;

public class LoginActivity extends LoginController{
    private FrameLayout kakaoBtn, facebookBtn, googleBtn;
    private Button noMemberBtn;

    private LogfileController lfc;
    private Context cont;
    final String filename = "userlog.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lfc = new LogfileController();
        cont = getApplicationContext();

        //check login log
        if(lfc.ReadLogFile(cont,filename) != "nofile"){
            Intent intent = new Intent(cont, LoadActivity.class);
            startActivity(intent);
            finish();
        }

        googleBtn = findViewById(R.id.googleBtn);
        kakaoBtn = findViewById(R.id.kakaoBtn);
        facebookBtn = findViewById(R.id.facebookBtn);
        noMemberBtn = findViewById(R.id.noMemberBtn);

        /*
        * @brief click Listener for each sign in buttons
        * */
        googleBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsignIn();
            }
        });
        kakaoBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.kakao).performClick();
            }
        });
        facebookBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.facebook).performClick();
            }
        });

        /*
        * @brief for Non-member user
        * need develop more
        * */
        noMemberBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GoogleCreate();
        FacebookCreate();
        KakaoCreate();
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
    }
    // [END on_start_check_user]

}
