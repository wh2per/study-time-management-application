package com.example.ready.studytimemanagement.presenter;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import com.example.ready.studytimemanagement.model.Data;

public class BasicTimer {

    private long targetTime, totalTime, startTime =0, tempTarget = 0;
    private Boolean onoff = false;
    private TextView targetView, totalView;
    final Handler handler = new Handler();

    public BasicTimer(long targetTime, TextView targetView, TextView totalView){
        this.targetTime = targetTime;
        this.totalTime = 0;
        this.targetView = targetView;
        this.totalView = totalView;
        this.targetView.setText(this.makeToTimeFormat(this.targetTime));
        this.totalView.setText(this.makeToTimeFormat(this.totalTime));
    }
    /*
     * @brief time formatter
     * convert the milli sec to the HH:MM:SS format
     * can show milli sec but unable now
     * */
    public String makeToTimeFormat(long t){
        long millis = t % 1000;
        long second = (t / 1000) % 60;
        long minute = (t / (1000 * 60)) % 60;
        long hour = (t / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d:%02d", hour, minute, second);
        return time;
    }
    public long getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(long targetTime) {
        this.targetTime = targetTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
    /*
    * @brief timer starts when start button onClicked
    * */
    public void timerStart(){
        onoff = true;
        startTime = SystemClock.uptimeMillis();
        totalTime = 0;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(onoff){
                    totalTime = SystemClock.uptimeMillis() - startTime;
                    if(targetTime>totalTime){
                        tempTarget = targetTime-totalTime;
                        targetView.setText(makeToTimeFormat(tempTarget));
                    }
                    totalView.setText(makeToTimeFormat(totalTime+1000));
                    handler.postDelayed(this,0);
                }
            }
        });
    }
    /*
    * @brief timer resets the target time when timer stopped
    * */
    public void timerStop(){
        onoff = false;
    }
}
