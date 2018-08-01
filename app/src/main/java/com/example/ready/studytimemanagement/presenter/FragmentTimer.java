package com.example.ready.studytimemanagement.presenter;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.model.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentTimer extends Fragment{
    TextView targetView, totalView;
    Button startBtn, plusHourBtn, plusMinBtn, plusSecBtn, minusHourBtn, minusMinBtn, minusSecBtn;
    private long targetTime = 30000;
    private BasicTimer bt;
    private boolean timerOn;
    private Data tempData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_timer, container,false);
        targetView = (TextView)rootView.findViewById(R.id.TargetTimeText);
        totalView = (TextView)rootView.findViewById(R.id.TotalTimeText);
        startBtn = (Button)rootView.findViewById(R.id.StartBtn);

        plusHourBtn = (Button)rootView.findViewById(R.id.hourPlusBtn);
        plusMinBtn = (Button)rootView.findViewById(R.id.minPlusBtn);
        plusSecBtn = (Button)rootView.findViewById(R.id.secPlusBtn);

        minusHourBtn = (Button)rootView.findViewById(R.id.hourMinusBtn);
        minusMinBtn = (Button)rootView.findViewById(R.id.minMinusBtn);
        minusSecBtn = (Button)rootView.findViewById(R.id.secMinusBtn);

        bt = new BasicTimer(targetTime, targetView, totalView);
        tempData = new Data();
        timerOn = false;

        /*
         * @brief timer btn listener, make the timer stop/start & load pop dialog
         * */
        startBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                if(timerOn){
                    startBtn.setText("시작");
                    timerOn = false;
                    bt.timerStop();
                    tempData.setTarget_time(String.valueOf(targetTime));
                    tempData.setAmount(String.valueOf(bt.getTotalTime()));

                    showNoticeDialog(tempData);

                    Date currentTime = new Date();
                    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
                    tempData.setDate(time.format(currentTime));

                    //Log.v("tag",tempData.getCategory());
                    //Log.v("tag",tempData.getDate());
                }else{
                    startBtn.setText("정지");
                    timerOn = true;
                    bt.timerStart();
                }
            }
        });
        /*
         * @brief listeners for add/subtract the timer time
         * @deprecated all listeners -> going to change some awesome ui
         * */
        plusHourBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    targetTime = targetTime+3600000;
                    bt.setTargetTime(targetTime);
                    updateTextview();
                }
            }
        });
        plusMinBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    targetTime = targetTime+600000;
                    bt.setTargetTime(targetTime);
                    updateTextview();
                }
            }
        });
        plusSecBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    targetTime = targetTime+30000;
                    bt.setTargetTime(targetTime);
                    updateTextview();
                }
            }
        });
        minusHourBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    if(targetTime-3600000>0){
                        targetTime = targetTime-3600000;
                    }else{
                        targetTime = 0;
                    }
                    bt.setTargetTime(targetTime);
                    updateTextview();
                }
            }
        });
        minusMinBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    if(targetTime-600000>0){
                        targetTime = targetTime-600000;
                    }else{
                        targetTime = 0;
                    }
                    bt.setTargetTime(targetTime);
                    updateTextview();
                }
            }
        });
        minusSecBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    if(targetTime-30000>0){
                        targetTime = targetTime-30000;
                    }else{
                        targetTime = 0;
                    }
                    bt.setTargetTime(targetTime);
                    updateTextview();
                }
            }
        });
        return rootView;
    }
    private void updateTextview(){
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
                //Log.v("tag",d.getCategory());
            }
        });
    }
}
