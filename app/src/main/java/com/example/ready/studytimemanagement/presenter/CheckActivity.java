package com.example.ready.studytimemanagement.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ready.studytimemanagement.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.io.File;

import static com.example.ready.studytimemanagement.presenter.LoginController.mAuth;
import static com.example.ready.studytimemanagement.presenter.LoginController.mGoogleSignInClient;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener{

    final static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Log/userlog.txt";

    final CheckActivity self = CheckActivity.this;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_check);

        findViewById(R.id.logout).setOnClickListener(this);
        TextView nameText = (TextView)findViewById(R.id.name);
        TextView emailText = (TextView)findViewById(R.id.email);
        Intent intent = getIntent();
        nameText.setText(intent.getStringExtra("ID").toString() );
        emailText.setText(intent.getStringExtra("EMAIL").toString() );
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.logout) {

            //구글 로그아웃
            mAuth.signOut();
            mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.v("구글 완료","구글");
                        }
                    });

            //페이스북 로그아웃
            LoginManager.getInstance().logOut();

            //카카오 로그아웃
            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                    Log.v("카카오 완료","카카오");
                }
            });

            //로그파일 삭제
            File file = new File(filePath);
            file.delete();

            final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }


    }
}
