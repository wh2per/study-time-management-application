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

import com.example.ready.studytimemanagement.presenter.Controller.AppLockController;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AppLockService extends Service {

    LogfileController lfc;
    AppLockController alc;
    checkThread th;
    private Context context = null;
    final static String sfilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ServiceLog/applock.txt";
    boolean checkFlag;
    boolean grantFlag;

    private ArrayList<AppLockList> AppLock;

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

            while(checkFlag && granted) {
                if(alc.CheckRunningApp(context,AppLock)) {
                    Intent intent = new Intent(context,LockActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    //finish();
                }
                Log.d("Thread", "" + num++);
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
        AppLock = new ArrayList<AppLockList>();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("Service : ", "서비스의 onStartCommand - "+flags+"번 서비스");

        AppLock.clear();
        String line = lfc.ReadLogFile(sfilePath);
        StringTokenizer tokens = new StringTokenizer(line);

        Log.d("tokens : ",""+tokens.countTokens());

        while(tokens.hasMoreTokens()) {
            AppLock.add(new AppLockList(tokens.nextToken(","),false));
        }

        //AppLock = (ArrayList<AppLockList>) intent.getSerializableExtra("AppLock");

        if(checkFlag==false) {
            th = new checkThread();
            th.start();
        }else {
            stopSelf();
        }
        checkFlag = !checkFlag;
        //Notification notification = new Notification(R.drawable.ic_launcher, "서비스 실행됨", System.currentTimeMillis());
        startForeground(1,new Notification());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        th = null;
        Log.d("Thread", "쓰레드 뿌셔");
        // 서비스가 종료될 때 실행
    }

}
