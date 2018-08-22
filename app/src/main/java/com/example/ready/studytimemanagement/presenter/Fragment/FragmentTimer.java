package com.example.ready.studytimemanagement.presenter.Fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
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
import com.example.ready.studytimemanagement.presenter.Activity.AppLockActivity;
import com.example.ready.studytimemanagement.presenter.Activity.MainActivity;
import com.example.ready.studytimemanagement.presenter.BasicTimer;
import com.example.ready.studytimemanagement.presenter.Service.TimerService;
import com.triggertrap.seekarc.SeekArc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentTimer extends Fragment{
    private TextView targetView, totalView;
    private Button appListBtn;
    private Button startBtn, plusMinBtn, minusMinBtn;
    private long targetTime = 0;
    public static BasicTimer bt;
    private boolean timerOn;
    private Data tempData;
    private SeekArc seekBar;

    //private TimerService timerService;
    //private Intent tService;
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
        startBtn = rootView.findViewById(R.id.StartBtn);
        seekBar = rootView.findViewById(R.id.seekArc);
        appListBtn = rootView.findViewById(R.id.appListBtn);
        plusMinBtn = rootView.findViewById(R.id.plusMinBtn);
        minusMinBtn = rootView.findViewById(R.id.minusMinBtn);

        bt = new BasicTimer(targetTime);
        targetView.setText(bt.makeToTimeFormat(this.targetTime));
        totalView.setText(bt.makeToTimeFormat(0));
        timerOn = false;

        tempData = new Data();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("TIMER_BROAD_CAST_ACK");
        getActivity().registerReceiver(br,intentFilter);
        final Intent sendIntent = new Intent("TIMER_BROAD_CAST_REQ");
        getActivity().sendBroadcast(sendIntent);

        /*
        //send timer object to
        tService = new Intent(getActivity(), TimerService.class);
        getActivity().bindService(tService, mConnection, Context.BIND_AUTO_CREATE);
*/

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
                    // timer stop!!
                    //startBtn.setText("시작");
                    startBtn.setBackgroundResource(R.drawable.lock_icon_grey);
                    timerOn = false;
                    bt.timerStop();
                    /*
                    //send broadcast msg
                    getActivity().sendBroadcast(sendIntent);

                    Intent timerService = new Intent(mainActivity,TimerService.class);
                    mainActivity.stopService(timerService);
*/
                    showNoticeDialog(tempData);

                    // need delay to get broadcast msg
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tempData.setTarget_time(String.valueOf(bt.makeToTimeFormat(targetTime)));
                            tempData.setAmount(String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                            Log.v("saved",String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                        }
                    },1000);

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
                    /// timer start!
                    //startBtn.setText("정지");
                    startBtn.setBackgroundResource(R.drawable.lock_icon_color);
                    timerOn = true;
                    bt.timerStart();;
                    /*
                    Intent timerService = new Intent(mainActivity,TimerService.class);
                    timerService.putExtra("timer",bt);
                    mainActivity.startService(timerService);
                    */
                    seekBar.setEnabled(false);

                    //timerService.setTimer(bt);
                    //mainActivity.startService(tService);

                    //timer text change
                    final Handler timerViewHandler = new Handler();
                    timerViewHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            targetView.setText(bt.makeToTimeFormat(bt.getTempTarget()));
                            totalView.setText(bt.makeToTimeFormat(bt.getTotalTime()+1000));
                            timerViewHandler.postDelayed(this,0);

                            if(!bt.getOnoff()){
                                timerViewHandler.removeMessages(0);
                                updateTextview();
                            }
                        }
                    });
                }
            }
        });

        seekBar.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                if(targetTime == 0){
                    targetTime = 1;
                }else{
                    //Log.v("angle", String.valueOf(seekBar.getProgress()));
                    int progress = seekBar.getProgress();
                    targetTime = progress*10000;
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

        appListBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AppLockActivity.class);
                startActivity(intent);
            }
        });


        plusMinBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetTime = targetTime + 300000;
                bt.setTargetTime(targetTime);
                updateTextview();
            }
        });
        minusMinBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(targetTime-300000<=0){
                    targetTime = 0;
                }else{
                    targetTime = targetTime - 300000;
                }
                bt.setTargetTime(targetTime);
                updateTextview();
            }
        });


        return rootView;
    }
    /*
    * @brief boardcast receiver for timer service
    * */
    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            bt = intent.getParcelableExtra("timer");
            Log.v("actREC",bt.makeToTimeFormat(bt.getTotalTime()));
        }
    };
    /*
    // service connection definition
    private ServiceConnection mConnection = new ServiceConnection() {

        // Called when the connection with the service is established
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.BindServiceBinder binder = (TimerService.BindServiceBinder) service;
            timerService = binder.getService(); // get service.
            timerService.registerCallback(mCallback); // callback registration
            //Log.v("noooo","noooso");
        }
        // Called when the connection with the service disconnects unexpectedly
        @Override
        public void onServiceDisconnected(ComponentName name) {
            timerService = null;
            //Log.v("noooo","noooo");
        }
    };

    // call below callback in service. it is running in Activity.
    private TimerService.ICallback mCallback = new TimerService.ICallback() {
        @Override
        public void remoteCall() {
            Log.d("MainActivity","called by service");
        }
    };*/

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        // Get the layout inflater
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
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

    /*
     * @brief GyroSenSensor
     * for a gyrosensor timer run by service
     */
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
                        //bt.timerStart();
                        seekBar.setEnabled(false);

                        // timer service start
                        Intent timerService = new Intent(mainActivity,TimerService.class);
                        timerService.putExtra("timer",bt);
                        mainActivity.startService(timerService);
                        seekBar.setEnabled(false);
                    }
                } else {
                    if (isFirst) {
                        TimerOn = false;
                        isFirst = false;
                        startBtn.setBackgroundResource(R.drawable.lock_icon_grey);
                        //bt.timerStop();

                        //send broadcast msg
                        Intent sendIntent = new Intent("TIMER_BROAD_CAST_REQ");
                        getActivity().sendBroadcast(sendIntent);

                        Intent timerService = new Intent(mainActivity,TimerService.class);
                        mainActivity.stopService(timerService);

                        showNoticeDialog(tempData);

                        // need delay to get broadcast msg
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tempData.setTarget_time(String.valueOf(bt.makeToTimeFormat(targetTime)));
                                tempData.setAmount(String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                                Log.v("saved",String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                            }
                        },1000);
                        /*
                        tempData.setTarget_time(String.valueOf(bt.makeToTimeFormat(targetTime)));
                        tempData.setAmount(String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));

                        fragmentTimer.showNoticeDialog(tempData);
*/
                        Date currentTime = new Date();
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
                        tempData.setDate(time.format(currentTime));
                        fragmentTimer.setTargetTime(0);
                        seekBar.setProgress(0);
                        fragmentTimer.updateTextview();

                        seekBar.setEnabled(true);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

}
