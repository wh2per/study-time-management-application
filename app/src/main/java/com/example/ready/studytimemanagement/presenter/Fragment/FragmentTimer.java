package com.example.ready.studytimemanagement.presenter.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.ready.studytimemanagement.model.User;
import com.example.ready.studytimemanagement.presenter.Activity.AppLockActivity;
import com.example.ready.studytimemanagement.presenter.Activity.MainActivity;
import com.example.ready.studytimemanagement.presenter.Item.BasicTimer;
import com.example.ready.studytimemanagement.presenter.Item.ItemApplock;
import com.example.ready.studytimemanagement.presenter.Service.TimerService;
import com.example.ready.studytimemanagement.control.NetworkTask;
import com.triggertrap.seekarc.SeekArc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FragmentTimer extends Fragment{
    private TextView targetView, totalView;
    private Button appListBtn;
    private Button startBtn, plusMinBtn, minusMinBtn;
    private long targetTime = 0;
    private double achievement;
    public static BasicTimer bt;
    private boolean timerOn;
    private Data tempData;
    private SeekArc seekBar;
    private boolean receiverRegied;
    private ArrayList<ItemApplock> applocks;
    private Intent lockIntent;

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

    @SuppressLint("ValidFragment")
    public FragmentTimer(ArrayList<ItemApplock> applocks){
        this.applocks = applocks;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // View Set up
        final ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_timer, container,false);
        targetView = (TextView)rootView.findViewById(R.id.TargetTimeText);
        totalView = (TextView)rootView.findViewById(R.id.TotalTimeText);
        startBtn = rootView.findViewById(R.id.StartBtn);
        seekBar = rootView.findViewById(R.id.seekArc);
        appListBtn = rootView.findViewById(R.id.appListBtn);
        plusMinBtn = rootView.findViewById(R.id.plusMinBtn);
        minusMinBtn = rootView.findViewById(R.id.minusMinBtn);

        // timer set up
        bt = new BasicTimer(targetTime);
        targetView.setText(bt.makeToTimeFormat(this.targetTime));
        totalView.setText(bt.makeToTimeFormat(0));
        timerOn = false;

        tempData = new Data();
        mainActivity = (MainActivity) this.getActivity();

        //start app lock list activity
        lockIntent = new Intent(getActivity(),AppLockActivity.class);
        lockIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(lockIntent);

        //for timer service
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("TIMER_BROAD_CAST_ACK");
        getActivity().registerReceiver(br,intentFilter);
        receiverRegied = true;
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

        /**
         * @brief timer btn listener, make the timer stop/start & load pop dialog
         * timer started by button is just for a performance to make user think timer is working
         * but real timer service is not started yet, it only starts through Gyro sensor
         **/
        startBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                if(timerOn){
                    // timer stop!!
                    mSensorManager.registerListener(mGyroLis, mGgyroSensor, SensorManager.SENSOR_DELAY_UI);
                    startBtn.setBackgroundResource(R.drawable.lock_icon_grey);
                    timerOn = false;
                    bt.timerStop();
                    /*
                    //send broadcast msg
                    getActivity().sendBroadcast(sendIntent);

                    Intent timerService = new Intent(mainActivity,TimerService.class);
                    mainActivity.stopService(timerService);
*/

                    // need delay to get broadcast msg
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            achievement = (bt.getTotalTime()/bt.getTargetTime())*100;
                            tempData.setTarget_time(String.valueOf(bt.makeToTimeFormat(targetTime)));
                            tempData.setAmount(String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                            Log.v("saved",String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                            showNoticeDialog(tempData);
                        }
                    },500);

                    Date currentTime = new Date();
                    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
                    tempData.setDate(time.format(currentTime));

                    // reset the timer values
                    setTargetTime(0);
                    seekBar.setProgress(0);
                    updateTextview();
                    seekBar.setEnabled(true);
                }else{
                    /// timer start!
                    mSensorManager.unregisterListener(mGyroLis);
                    startBtn.setBackgroundResource(R.drawable.lock_icon_color);
                    timerOn = true;
                    bt.timerStart();
                    seekBar.setEnabled(false);

                    /*
                    Intent timerService = new Intent(mainActivity,TimerService.class);
                    timerService.putExtra("timer",bt);
                    mainActivity.startService(timerService);
                    */

                    //timerService.setTimer(bt);
                    //mainActivity.startService(tService);

                    //timer text change
                    final Handler timerViewHandler = new Handler();
                    timerViewHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            targetView.setText(bt.makeToTimeFormat(bt.getTempTarget()));
                            totalView.setText(bt.makeToTimeFormat(bt.getTotalTime()+1000));
                            timerViewHandler.postDelayed(this,1000);
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
                startActivity(lockIntent);

                // GET_USAGE_STATS 권한 확인
                boolean granted = false;
                AppOpsManager appOps = (AppOpsManager) mainActivity.getSystemService(Context.APP_OPS_SERVICE);
                int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,android.os.Process.myUid(), mainActivity.getPackageName());

                if (mode == AppOpsManager.MODE_DEFAULT) {
                    granted = (mainActivity.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
                } else {
                    granted = (mode == AppOpsManager.MODE_ALLOWED);
                }

                Log.d("isRooting granted = " , String.valueOf(granted));

                if (granted == false)
                {
                    // 권한이 없을 경우 권한 요구 페이지 이동
                    Intent sintent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    sintent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    mainActivity.startActivity(sintent);
                }
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

    /**
    * @brief boardcast receiver for timer service
    **/
    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            bt = intent.getParcelableExtra("timer");
            Log.v("actREC",bt.makeToTimeFormat(bt.getTotalTime()));
        }
    };

    /**
     * @brief unregister the boardcast receiver while activity on pause
     **/
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiverRegied){
            getActivity().unregisterReceiver(br);
            receiverRegied = false;
        }
    }

    public long getTargetTime(){
        return this.targetTime;
    }
    public void setTargetTime(long l){
        this.targetTime = l;
    }
    private void updateTextview(){

        targetView.setText(bt.makeToTimeFormat(targetTime));
        totalView.setText(bt.makeToTimeFormat(0));
    }

    /**
     * @brief dialog message with edit text for save category
     * @param time_data uses for save category value
     * @TODO add achievement value if need
     **/
    public void showNoticeDialog(final Data time_data) {
        // Create an instance of the dialog fragment and show it
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        // Get the layout inflater
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_category, null));

        final AlertDialog dialog = builder.create();
        dialog.show();
        Button saveBtn = dialog.findViewById(R.id.saveBtn);
        final EditText ed = dialog.findViewById(R.id.categoryText);

        TextView ctText = dialog.findViewById(R.id.completeTimeText);
        ctText.setText(time_data.getAmount());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    time_data.setCategory(String.valueOf(ed.getText()));
                    dialog.dismiss();
                    if(mainActivity.getSns().equals("4") == false) {
                        String user_id = mainActivity.getId();
                        User user = new User(user_id, null, 0, null);
                        NetworkTask networkTask = new NetworkTask("/register-time", user, time_data);
                        networkTask.execute().get(1000, TimeUnit.MILLISECONDS);
                        String toastMsg = time_data.getCategory() + " " + time_data.getAmount() + " 저장됐습니다";
                        Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();
                    } else {
                        String toastMsg = "비회원은 저장되지 않습니다";
                        Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @brief GyroSenSensor
     * for a gyrosensor timer run by service
     **/
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
                    if (!TimerOn && !timerOn) {
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
                    if (isFirst && !timerOn) {
                        TimerOn = false;
                        isFirst = false;
                        startBtn.setBackgroundResource(R.drawable.lock_icon_grey);
                        //bt.timerStop();

                        //send broadcast msg
                        Intent sendIntent = new Intent("TIMER_BROAD_CAST_REQ");
                        getActivity().sendBroadcast(sendIntent);

                        Intent timerService = new Intent(mainActivity,TimerService.class);
                        mainActivity.stopService(timerService);


                        // need delay to get broadcast msg
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                achievement = (bt.getTotalTime()/bt.getTargetTime())*100;
                                tempData.setTarget_time(String.valueOf(bt.makeToTimeFormat(targetTime)));
                                tempData.setAmount(String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                                Log.v("saved",String.valueOf(bt.makeToTimeFormat(bt.getTotalTime())));
                                showNoticeDialog(tempData);
                            }
                        },500);
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