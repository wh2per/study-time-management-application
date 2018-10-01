package com.example.ready.studytimemanagement.presenter.Itemview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ready.studytimemanagement.R;

public class ItemViewSetting extends LinearLayout{
    TextView menuText;

    public ItemViewSetting(Context context) {
        super(context);
        init(context);
    }

    public ItemViewSetting(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_setting, this, true);

        menuText = findViewById(R.id.settingText);
    }

    public void setMenuText(String s) {
        menuText.setText(s);
    }

}
