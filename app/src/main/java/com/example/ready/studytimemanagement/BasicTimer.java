package com.example.ready.studytimemanagement;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

public class BasicTimer {
    private long targetTime, totalTime, startTime =0, tempTotal = 0, tempTarget = 0;
    private Boolean onoff = false;
    private TextView targetView, totalView;
    private CountDownTimer targetTimer, totalTimer;

    final Handler handler = new Handler();

    public BasicTimer(long targetTime, TextView targetView, TextView totalView){
        this.targetTime = targetTime;
        this.totalTime = 0;
        this.targetView = targetView;
        this.totalView = totalView;
        this.targetView.setText(this.makeToTimeFormat(this.targetTime));
        this.totalView.setText(this.makeToTimeFormat(this.totalTime));
    }
    public String makeToTimeFormat(long t){
        long millis = t % 1000;
        long second = (t / 1000) % 60;
        long minute = (t / (1000 * 60)) % 60;
        long hour = (t / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d:%02d", hour, minute, second);
        return time;
    }
    public void hourUp(){
        targetTime = targetTime+3600000;
        targetView.setText(makeToTimeFormat(targetTime));
    }
    public void minUp(){
        targetTime = targetTime+600000;
        targetView.setText(makeToTimeFormat(targetTime));
    }
    public void secUp(){
        targetTime = targetTime+30000;
        targetView.setText(makeToTimeFormat(targetTime));
    }
    public void hourDown(){
        if(targetTime-3600000>0){
            targetTime = targetTime-3600000;
        }else{
            targetTime = 0;
        }
        targetView.setText(makeToTimeFormat(targetTime));
    }
    public void minDown(){
        if(targetTime-600000>0){
            targetTime = targetTime-600000;
        }else{
            targetTime = 0;
        }
        targetView.setText(makeToTimeFormat(targetTime));
    }
    public void secDown(){
        if(targetTime-30000>0){
            targetTime = targetTime-30000;
        }else{
            targetTime = 0;
        }
        targetView.setText(makeToTimeFormat(targetTime));
    }
    public void timerStart(){
        onoff = true;
        startTime = SystemClock.uptimeMillis();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(onoff){
                    tempTotal = SystemClock.uptimeMillis() - startTime;
                    tempTotal = tempTotal+totalTime;

                    if(targetTime>tempTotal){
                        tempTarget = targetTime-tempTotal;
                        targetView.setText(makeToTimeFormat(tempTarget));
                    }
                    totalView.setText(makeToTimeFormat(tempTotal+1000));
                    handler.postDelayed(this,0);
                }

            }
        });
    }
    public void timerStop(){
        onoff = false;
        totalTime = tempTotal;
    }
}
