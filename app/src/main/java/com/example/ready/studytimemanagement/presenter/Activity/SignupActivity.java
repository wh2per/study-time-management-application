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
import android.widget.Toast;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;

import java.util.StringTokenizer;

public class SignupActivity extends AppCompatActivity {
    private Button signupBtn;
    private EditText nicknameText, jobText, ageText;
    private LogfileController lfc;
    private Context cont;
    final String filename = "userlog.txt";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        lfc = new LogfileController();
        nicknameText = findViewById(R.id.nicknameeditText);
        ageText = findViewById(R.id.ageeditText);
        jobText = findViewById(R.id.jobeditText);
        signupBtn = findViewById(R.id.signupBtn);

        cont = this.getApplicationContext();

        signupBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nicknameText.getText().equals("") ||
                        ageText.getText().equals("") ||
                        jobText.getText().equals("")) {
                    Toast.makeText(cont, "빈칸 없이 입력해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    String contents =
                            "," + nicknameText.getText() +
                            "," + ageText.getText() +
                            "," + jobText.getText();
                    lfc.WriteLogFile(cont, filename, contents, 1);
                    Intent intent = new Intent(getBaseContext(), LoadActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                lfc = new LogfileController();
                lfc.WriteLogFile(this,filename,"nofile",2);
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
