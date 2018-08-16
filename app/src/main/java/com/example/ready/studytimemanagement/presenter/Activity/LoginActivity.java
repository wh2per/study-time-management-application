package com.example.ready.studytimemanagement.presenter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.LoginController;
import com.kakao.usermgmt.LoginButton;

public class LoginActivity extends LoginController {
    private Button kakaoBtn, facebookBtn, googleBtn, noMemberBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        //이전에 로그인한 기록이 있는지 검사
        if(isExistUserLog()){   //기록이 있음

        }else{      //기록이 없음
            GoogleCreate();
            FacebookCreate();
            KakaoCreate();
        }


    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        //startLogin();
    }
    // [END on_start_check_user]

}
