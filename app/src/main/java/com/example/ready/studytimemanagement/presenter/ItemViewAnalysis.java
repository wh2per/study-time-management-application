package com.example.ready.studytimemanagement.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.model.Data;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ItemViewAnalysis extends LinearLayout {
    TextView titleText, subText;
    LineChart chart;


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
    /*
     * @brief set the Line Graph
     * */
    /*
    public void setLineChart(){
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        for(int i =0; i<5; i++){
            entries.add(new Entry(i,i));
        }
        LineDataSet dataSet = new LineDataSet(entries,"label");
        dataSet.setColor(Color.rgb(70,144,150));
        //dataSet.setColor(000);
        //dataSet.setValueTextColor(000);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }*/
    /*
     * @brief set the bar graph
     * @todo get the data type and make to the graph
     * */
    public void setBarChart(){
        BarChart barChart = findViewById(R.id.barChart);
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for(int i =0; i<5; i++){
            entries.add(new BarEntry(i,i*10));
        }
        BarDataSet dataSet = new BarDataSet(entries,"label");
        dataSet.setColor(Color.rgb(70,144,150));

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }
}

