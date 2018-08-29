package com.example.ready.studytimemanagement.presenter;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.StringTokenizer;

public class AppLockList implements Serializable{
    private String appName;
    private boolean lockFlag;

    public AppLockList(){
    }

    public AppLockList(String appName, boolean lockFlag){
        this.appName = appName;
        this.lockFlag = lockFlag;
    }

    public String getAppName() {
        return appName;
    }

    public boolean getLockFlag(){
        return lockFlag;
    }

    public void setAppName(String appName){
        this.appName = appName;
    }

    public void setLockFlag(boolean lockFlag){
        this.lockFlag = lockFlag;
    }
}