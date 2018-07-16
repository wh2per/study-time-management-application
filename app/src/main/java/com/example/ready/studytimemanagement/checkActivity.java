package com.example.ready.studytimemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class checkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_check);
        TextView nameText = (TextView)findViewById(R.id.name);
        TextView emailText = (TextView)findViewById(R.id.email);
        Intent intent = getIntent();
        nameText.setText(intent.getStringExtra("ID").toString() );
        emailText.setText(intent.getStringExtra("EMAIL").toString() );
    }
}
