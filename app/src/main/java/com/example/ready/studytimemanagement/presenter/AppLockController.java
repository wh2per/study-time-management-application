package com.example.ready.studytimemanagement.presenter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppLockController extends BaseActivity{
    ArrayList<String> AppLock;

    PackageManager pkgm;
    List<ResolveInfo> AppInfos;
    ResolveInfo info;
    String n;

    public AppLockController(){
        AppLock = new ArrayList<String>();
    }

    public void LoadAppList(Activity act){
        pkgm = act.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        AppInfos = pkgm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : AppInfos) {
            ActivityInfo ai = info.activityInfo;
            Log.d("APP TITLE", ai.loadLabel(pkgm).toString());
            //n = ai.loadLabel(pkgm).toString();
            Log.d("APP Package Name", ai.packageName);
            Log.d("APP Class Name", ai.name);
        }
    }

    public boolean CheckRunningApp(Activity act){
        ActivityManager am = (ActivityManager) act.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();

        if(AppLock.isEmpty()==false){
            for(int i=0; i < list.size(); i++){
                for(int j=0; j<AppLock.size(); j++){
                    if(AppLock.get(j).toString().equals(list.get(i).processName.toString())) {
                        Log.d("Catch ForeGr2222 : ", list.get(i).processName);
                        if(list.get(i).importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            Log.d("Catch ForeGround App : ", list.get(i).processName);
                            AppLock.remove(j);
                            return true;
                        }
                    }
                }
            }
        }else{
            Log.d("App Lock : ","잠글 앱이 없습네다~");
        }
        return false;
    }
}
