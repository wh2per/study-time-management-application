package com.example.ready.studytimemanagement;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemViewAnalysis extends LinearLayout {
    TextView titleText, subText;

    public ItemViewAnalysis(Context context) {
        super(context);
        init(context);
    }

    public ItemViewAnalysis(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_analysis, this, true);

        titleText = findViewById(R.id.analyTitleText);
        subText = findViewById(R.id.analySubText);
    }

    public void setTitleText(String s) {
        titleText.setText(s);
    }

    public void setSubText(String s) {
        subText.setText(s);
    }
}

