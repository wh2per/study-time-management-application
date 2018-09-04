package com.example.ready.studytimemanagement.presenter.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.AppLockService;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;

import java.util.StringTokenizer;

public class SignupActivity extends AppCompatActivity{

    private Button signupBtn;
    private EditText idText, pwText, nameText;
    private LogfileController lfc;
    private Context cont;
    final String filename = "userlog.txt";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        lfc = new LogfileController();
        idText = findViewById(R.id.ideditText);
        pwText = findViewById(R.id.pweditText);
        nameText = findViewById(R.id.nameeditText);
        signupBtn = findViewById(R.id.signupBtn);



        String line = lfc.ReadLogFile(cont,filename);
        StringTokenizer tokens = new StringTokenizer(line);

        String name = tokens.nextToken(",");
        String email = tokens.nextToken(",");

        idText.setText(email);
        nameText.setText(name);

        signupBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cont, LoadActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                lfc = new LogfileController();
                lfc.WriteLogFile(this,filename,"nofile",2);
                Intent intent = new Intent(cont, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
