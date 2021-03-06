package com.example.ready.studytimemanagement.presenter.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.control.NetworkTask;
import com.example.ready.studytimemanagement.model.User;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

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

        lfc = new LogfileController();
        cont = getApplicationContext();

        //check login log
        String line = lfc.ReadLogFile(cont,filename);
        try {
            if (line.equals("nofile") == false) {
                String sns = "";
                StringTokenizer tokens = new StringTokenizer(line, ",");
                User temp_user = new User();
                if(tokens.hasMoreTokens()) {
                    sns = tokens.nextToken();
                    temp_user.setId(tokens.nextToken());
                    NetworkTask networkTask = new NetworkTask("/check-user", temp_user, null);
                    networkTask.execute().get(1000, TimeUnit.MILLISECONDS);
                    if (networkTask.getUser().getisUser()) {
                        String contents =
                                sns +
                                        "," + networkTask.getUser().getId() +
                                        "," + networkTask.getUser().getNickname() +
                                        "," + networkTask.getUser().getAge() +
                                        "," + networkTask.getUser().getJob();
                        Log.e("writeLog", contents);
                        lfc.WriteLogFile(getApplicationContext(), filename, contents, 2);

                        Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        } catch(Exception e) {
            Log.e("error in loginActivity", e.toString());
        }
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
                String content = "4,";
                lfc.WriteLogFile(getApplicationContext(), filename, content, 2);
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
