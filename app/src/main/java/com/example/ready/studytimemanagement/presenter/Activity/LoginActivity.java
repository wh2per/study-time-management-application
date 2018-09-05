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

public class LoginActivity extends BaseActivity{
    private FrameLayout kakaoBtn, facebookBtn, googleBtn;
    private Button noMemberBtn;

    private LogfileController lfc;
    private Context cont;
    final String filename = "userlog.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("LoginActivity : ","켜졌다!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        lfc = new LogfileController();
        cont = getApplicationContext();


        //check login log
        if(lfc.ReadLogFile(cont,filename).equals("nofile")==false){
            Log.d("LoginActivity : ","파일이 있음");
            Intent intent = new Intent(cont, LoadActivity.class);
            startActivity(intent);
            Log.d("LoginActivity : ","찍히면 안댐");
        }
        Log.d("LoginActivity : ","파일이 없음");
        googleBtn = findViewById(R.id.googleBtn);
        kakaoBtn = findViewById(R.id.kakaoBtn);
        facebookBtn = findViewById(R.id.facebookBtn);
        noMemberBtn = findViewById(R.id.noMemberBtn);

        /**
        * @brief click Listener for each sign in buttons
        **/
        googleBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cont,GoogleLoginActivity.class);
                intent.putExtra("InOut",1);
                startActivity(intent);
            }
        });

        kakaoBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cont,KakaoLoginActivity.class);
                intent.putExtra("InOut",1);
                startActivity(intent);
            }
        });

        facebookBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cont,FacebookLoginActivity.class);
                intent.putExtra("InOut",1);
                startActivity(intent);
            }
        });

        /**
        * @brief for Non-member user
        **/
        noMemberBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cont, LoadActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
    }
    // [END on_start_check_user]

}
