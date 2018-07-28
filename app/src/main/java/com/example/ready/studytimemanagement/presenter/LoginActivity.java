package com.example.ready.studytimemanagement.presenter;

import android.os.Bundle;
import android.view.View;

import com.example.ready.studytimemanagement.R;

public class LoginActivity extends LoginController implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Button listeners
        findViewById(R.id.google).setOnClickListener(this);
        findViewById(R.id.facebook).setOnClickListener(this);
        findViewById(R.id.kakao).setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.google) {
            gsignIn();
        }
    }
}
