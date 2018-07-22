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
    Button startBtn;
    private BasicTimer bt;
    private boolean timerOn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_timer, container,false);
        targetTview = (TextView)rootView.findViewById(R.id.TargetTimeText);
        totalTview = (TextView)rootView.findViewById(R.id.TotalTimeText);
        startBtn = (Button)rootView.findViewById(R.id.StartBtn);

        bt = new BasicTimer(10000, targetTview, totalTview);
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

        return rootView;
    }
}
