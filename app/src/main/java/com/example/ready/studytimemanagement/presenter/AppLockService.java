package com.example.ready.studytimemanagement.presenter;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ready.studytimemanagement.presenter.Activity.LockActivity;
import com.example.ready.studytimemanagement.presenter.Controller.AppLockController;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;
import com.example.ready.studytimemanagement.presenter.Item.ItemApplock;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AppLockService extends Service {

    LogfileController lfc;
    AppLockController alc;
    checkThread th;
    private Context context = null;
    final static String sfilename= "applock.txt";
    boolean checkFlag;
    boolean grantFlag;

    private ArrayList<String> AppLock;

    private class checkThread extends Thread{
        public void run() {
            int num = 1;

            // GET_USAGE_STATS 권한 확인
            boolean granted = false;
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,android.os.Process.myUid(), context.getPackageName());

            if (mode == AppOpsManager.MODE_DEFAULT) {
                granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
            } else {
                granted = (mode == AppOpsManager.MODE_ALLOWED);
            }

            while(granted && checkFlag) {
                if(alc.CheckRunningApp(context,AppLock)) {
                    Intent intent = new Intent(context,LockActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    //finish();
                }
                Log.d("Thread", this.getId()+" : " + num++);
                try {
                    Thread.sleep(1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("Service : ", "서비스의 onCreate");

        alc = new AppLockController();
        lfc = new LogfileController();
        checkFlag = false;
        grantFlag = false;
        context = getApplicationContext();
        AppLock = new ArrayList<String>();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("Service : ", "서비스의 onStartCommand - "+flags+"번 서비스");
        checkFlag = intent.getBooleanExtra("OnOff",false);
        AppLock.clear();
        String line = lfc.ReadLogFile(context,sfilename);
        StringTokenizer tokens = new StringTokenizer(line);

        Log.d("tokens : ",""+tokens.countTokens());

        while(tokens.hasMoreTokens()) {
            AppLock.add((tokens.nextToken(",")));
        }

        if(checkFlag == false) {
            th = new checkThread();
            th.start();
            Log.d("쓰레드를 새로만들꺼야",""+th.getId());
        }
        if(checkFlag == true){
            //Log.d("쓰레드가 이미 있어",""+th.getId());
            stopSelf();
        }
        checkFlag =!checkFlag;
        //Notification notification = new Notification(R.drawable.ic_launcher, "서비스 실행됨", System.currentTimeMillis());
        startForeground(1,new Notification());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
    }

}