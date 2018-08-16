package com.example.ready.studytimemanagement.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.model.Data;
import com.triggertrap.seekarc.SeekArc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentTimer extends Fragment{
    private TextView targetView, totalView;
    private  Button startBtn, plusHourBtn, plusMinBtn, plusSecBtn, minusHourBtn, minusMinBtn, minusSecBtn;
    private long targetTime = 0;
    private BasicTimer bt;
    private boolean timerOn;
    private Data tempData;
    private SeekArc seekBar;

    //Using the Accelometer & Gyroscoper
    private SensorManager mSensorManager = null;

    //Using the Gyroscope
    private SensorEventListener mGyroLis;
    private Sensor mGgyroSensor = null;
    private Vibrator vibrator;
    public MainActivity mainActivity;

    public FragmentTimer(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_timer, container,false);
        targetView = (TextView)rootView.findViewById(R.id.TargetTimeText);
        totalView = (TextView)rootView.findViewById(R.id.TotalTimeText);
        startBtn = (Button)rootView.findViewById(R.id.StartBtn);
        seekBar = rootView.findViewById(R.id.seekArc);


        bt = new BasicTimer(targetTime, targetView, totalView);
        //Using the Gyroscope & Accelometer
        mSensorManager = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);

        //Using the Accelometer
        mGgyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mGyroLis = new GyroscopeListener(this);
        vibrator = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);

        mSensorManager.registerListener(mGyroLis, mGgyroSensor, SensorManager.SENSOR_DELAY_UI);

        /*
         * @brief timer btn listener, make the timer stop/start & load pop dialog
         * */
        startBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                if(timerOn){
                    //startBtn.setText("시작");
                    startBtn.setBackgroundResource(R.drawable.lock_icon_grey);
                    timerOn = false;
                    bt.timerStop();
                    tempData.setTarget_time(String.valueOf(bt.makeToTimeFormat(targetTime)));
                    tempData.setAmount(String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));

                    showNoticeDialog(tempData);

                    Date currentTime = new Date();
                    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
                    tempData.setDate(time.format(currentTime));

                    setTargetTime(0);
                    seekBar.setProgress(0);
                    updateTextview();

                    seekBar.setEnabled(true);
                    //Log.v("tag",tempData.getCategory());
                    //Log.v("tag",tempData.getDate());
                }else{
                    //startBtn.setText("정지");
                    startBtn.setBackgroundResource(R.drawable.lock_icon_color);
                    timerOn = true;
                    bt.timerStart();
                    seekBar.setEnabled(false);
                }
            }
        });


        seekBar.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                if(targetTime == 0){
                    targetTime = 1;
                }else{
                    Log.v("angle", String.valueOf(seekBar.getProgress()));
                    int progress = seekBar.getProgress();
                    targetTime = progress*300000;
                    bt.setTargetTime(targetTime);
                    updateTextview();
                }

                /*
                if(progress < 25){
                    targetTime = progress*60000;
                }else if(progress >= 25 && progress<=50){
                    targetTime = (progress/2)*1200000;
                }else if(progress>50 && progress<=75){
                    targetTime = (progress/2)*1800000;
                }else{
                    targetTime = (progress/2)*3200000;
                }*/
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });
        return rootView;
    }
    public long getTargetTime(){
        return this.targetTime;
    }
    public void setTargetTime(long l){
        this.targetTime = l;
    }
    private void updateTextview(){
       // Log.v("tatag", String.valueOf(targetTime));

        targetView.setText(bt.makeToTimeFormat(targetTime));
        totalView.setText(bt.makeToTimeFormat(0));
    }

    /*
     * @brief dialog message with edittext for save category
     * @param Data $d uses for save category value
     * */
    public void showNoticeDialog(final Data d) {
        // Create an instance of the dialog fragment and show it
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_category, null));

        final AlertDialog dialog = builder.create();
        dialog.show();
        Button saveBtn = dialog.findViewById(R.id.saveBtn);
        final EditText ed = dialog.findViewById(R.id.categoryText);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.setCategory(String.valueOf(ed.getText()));
                dialog.dismiss();
                String toastMsg = d.getCategory()+" "+d.getAmount()+" 저장됐습니다";
                Toast.makeText(getContext(),toastMsg,Toast.LENGTH_LONG).show();

            }
        });
    }
    private class GyroscopeListener implements SensorEventListener {
        //Roll and Pitch
        private double pitch;

        //timestamp and dt
        private double timestamp;
        private double dt;

        // for radian -> dgree
        private double RAD2DGR = 180 / Math.PI;
        private static final float NS2S = 1.0f/1000000000.0f;
        private boolean TimerOn;
        private long millisecond = 300;
        private boolean isFirst;
        private FragmentTimer fragmentTimer;

        public GyroscopeListener(FragmentTimer _fragmentTimer) {
            this.fragmentTimer = _fragmentTimer;
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            /* 각 축의 각속도 성분을 받는다. */
            double gyroY = event.values[1];

            /* 각속도를 적분하여 회전각을 추출하기 위해 적분 간격(dt)을 구한다.
             * dt : 센서가 현재 상태를 감지하는 시간 간격
             * NS2S : nano second -> second */
            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            /* 맨 센서 인식을 활성화 하여 처음 timestamp가 0일때는 dt값이 올바르지 않으므로 넘어간다. */
            if (dt - timestamp*NS2S != 0) {

                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
                pitch = pitch + gyroY*dt;
                if (Math.abs(pitch * RAD2DGR) > 150.0) {
                    //textX.setText("           [Pitch]: 뒤집힘");
                    if (!TimerOn) {
                        vibrator.vibrate(millisecond);
                        TimerOn = true;
                        isFirst = true;
                        startBtn.setBackgroundResource(R.drawable.lock_icon_color);
                        bt.timerStart();
                        seekBar.setEnabled(false);
                    }
                } else {
                    if (isFirst) {
                        TimerOn = false;
                        isFirst = false;
                        startBtn.setBackgroundResource(R.drawable.lock_icon_grey);
                        bt.timerStop();
/*
                        tempData.setTarget_time(String.valueOf(bt.makeToTimeFormat(targetTime)));
                        tempData.setAmount(String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));

                        fragmentTimer.showNoticeDialog(tempData);

                        Date currentTime = new Date();
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
                        tempData.setDate(time.format(currentTime));
                        fragmentTimer.setTargetTime(0);
                        seekBar.setProgress(0);
                        fragmentTimer.updateTextview();

                        seekBar.setEnabled(true);
                        */
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

}
