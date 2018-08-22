package com.example.ready.studytimemanagement.presenter;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.ready.studytimemanagement.model.Data;

import java.io.Serializable;

public class BasicTimer implements Parcelable{

    private long targetTime, totalTime, startTime =0, tempTarget = 0;
    private Boolean onoff = false;
    private TextView targetView, totalView;
    final Handler handler = new Handler();

    public BasicTimer(long targetTime){
        this.targetTime = targetTime;
        this.totalTime = 0;
    }

    protected BasicTimer(Parcel in) {
        targetTime = in.readLong();
        totalTime = in.readLong();
        startTime = in.readLong();
        tempTarget = in.readLong();
        byte tmpOnoff = in.readByte();
        onoff = tmpOnoff == 0 ? null : tmpOnoff == 1;
    }

    public static final Creator<BasicTimer> CREATOR = new Creator<BasicTimer>() {
        @Override
        public BasicTimer createFromParcel(Parcel in) {
            return new BasicTimer(in);
        }

        @Override
        public BasicTimer[] newArray(int size) {
            return new BasicTimer[size];
        }
    };

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

    public long getTempTarget() {
        return tempTarget;
    }

    public void setTempTarget(long tempTarget) {
        this.tempTarget = tempTarget;
    }

    public Boolean getOnoff() {
        return onoff;
    }

    public void setOnoff(Boolean onoff) {
        this.onoff = onoff;
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
                        //targetView.setText(makeToTimeFormat(tempTarget));
                    }
                    //totalView.setText(makeToTimeFormat(totalTime+1000));
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
        handler.removeMessages(0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(targetTime);
        parcel.writeLong(totalTime);
        parcel.writeLong(startTime);
        parcel.writeLong(tempTarget);
        parcel.writeByte((byte) (onoff == null ? 0 : onoff ? 1 : 2));
    }
}
