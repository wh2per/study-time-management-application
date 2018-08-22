package com.example.ready.studytimemanagement.presenter.Service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.ready.studytimemanagement.presenter.Activity.MainActivity;
import com.example.ready.studytimemanagement.presenter.BasicTimer;

public class TimerService extends Service {

    private BasicTimer bt;
    //private final IBinder mBinder = new BindServiceBinder();
    //private ICallback mCallback;
    /*
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public interface ICallback {
        public void remoteCall();
    }
    // for registration in activity
    public void registerCallback(ICallback cb){
        mCallback = cb;
    }
    // service contents
    public void myServiceFunc(){
        Log.d("BindService","called by Activity");
        // call callback in Activity
        mCallback.remoteCall();
    }
    public BasicTimer getTimer() {
        return bt;
    }

    public void setTimer(BasicTimer bt) {
        this.bt = new BasicTimer(bt.getTargetTime());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bt = new BasicTimer(0);
    }*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bt = intent.getParcelableExtra("timer");
        bt.timerStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("TIMER_BROAD_CAST_REQ");
        registerReceiver(br,intentFilter);

        Log.v("sertest", String.valueOf(bt.getTargetTime()));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bt.timerStop();
        unregisterReceiver(br);
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
       // intent.putExtra("timer", bt);
        Log.v("restest", String.valueOf(bt.getTotalTime()));
    }
    /*
    public class BindServiceBinder extends Binder {
        public TimerService getService(){
            return TimerService.this;
        }
    }*/

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent sendIntent = new Intent("TIMER_BROAD_CAST_ACK");
            sendIntent.putExtra("timer",bt);
            sendBroadcast(sendIntent);
            Log.v("serREC","done");
        }
    };
}