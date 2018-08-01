package com.example.ready.studytimemanagement.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.ready.studytimemanagement.model.AnalysisData;
import com.example.ready.studytimemanagement.model.Data;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class to classify time data by period or category
 */
public class ClassifyData {
    AnalysisData sample;
    /**
     * @brief Constructor
     */
    public ClassifyData() {
        sample = new AnalysisData();
        sample.getAnalysis_category().add(new Data("영어", "2018-07-31", "01:03:05", "03:03:03"));
        sample.getAnalysis_category().add(new Data("영어", "2018-07-31", "01:03:05", "03:03:03"));
        sample.getAnalysis_category().add(new Data("영어", "2018-07-31", "01:03:05", "03:03:03"));
        sample.getAnalysis_category().add(new Data("국어", "2018-07-31", "01:03:05", "03:03:03"));
        sample.getAnalysis_category().add(new Data("국어", "2018-07-31", "01:03:05", "03:03:03"));
    }

    public void classifyByCategory() {
        ArrayList<Data> temp = sample.getAnalysis_category();
        HashMap<String, Long> classification = new HashMap<>();
        Iterator<String> mapIt;
        boolean hasKey = false;
        for(int i = 0; i < temp.size(); i++) {
            String category = temp.get(i).getCategory();
            long second = Time.valueOf(temp.get(i).getAmount()).getTime();
            mapIt = classification.keySet().iterator();
            while(mapIt.hasNext()) {
                if(category.equals(mapIt.next())) {
                    classification.put(category, classification.get(category) + second);
                    hasKey = true;
                    break;
                }
            }
            if(!hasKey) {
                classification.put(category, second);
            }
            hasKey = false;
        }
        mapIt = classification.keySet().iterator();
        while(mapIt.hasNext()) {
            Log.d("classify", Long.toString(classification.get(mapIt.next())));
        }
    }

}
