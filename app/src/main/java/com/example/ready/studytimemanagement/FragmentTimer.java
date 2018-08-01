package com.example.ready.studytimemanagement;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentTimer extends Fragment{
    TextView targetTview, totalTview;
    Button startBtn, plusHourBtn, plusMinBtn, plusSecBtn, minusHourBtn, minusMinBtn, minusSecBtn;
    private BasicTimer bt;
    private boolean timerOn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_timer, container,false);
        targetTview = (TextView)rootView.findViewById(R.id.TargetTimeText);
        totalTview = (TextView)rootView.findViewById(R.id.TotalTimeText);
        startBtn = (Button)rootView.findViewById(R.id.StartBtn);

        plusHourBtn = (Button)rootView.findViewById(R.id.hourPlusBtn);
        plusMinBtn = (Button)rootView.findViewById(R.id.minPlusBtn);
        plusSecBtn = (Button)rootView.findViewById(R.id.secPlusBtn);

        minusHourBtn = (Button)rootView.findViewById(R.id.hourMinusBtn);
        minusMinBtn = (Button)rootView.findViewById(R.id.minMinusBtn);
        minusSecBtn = (Button)rootView.findViewById(R.id.secMinusBtn);

        bt = new BasicTimer(30000, targetTview, totalTview);
        timerOn = false;

        startBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                if(timerOn){
                    startBtn.setText("START");
                    timerOn = false;
                    bt.timerStop();
                }else{
                    startBtn.setText("STOP");
                    timerOn = true;
                    bt.timerStart();
                }
            }
        });
        plusHourBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    bt.hourUp();
                }
            }
        });
        plusMinBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    bt.minUp();
                }
            }
        });
        plusSecBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    bt.secUp();
                }
            }
        });
        minusHourBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    bt.hourDown();
                }
            }
        });
        minusMinBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    bt.minDown();
                }
            }
        });
        minusSecBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!timerOn){
                    bt.secDown();
                }
            }
        });
        return rootView;
    }
}
