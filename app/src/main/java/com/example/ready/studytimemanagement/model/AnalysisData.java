package com.example.ready.studytimemanagement.model;

import java.util.ArrayList;

public class AnalysisData {
    ArrayList<Data> analysisByCategory;
    ArrayList<Data> analysisByData;
    ArrayList<Data> timeData;
    float achievement_rate;

    public AnalysisData(){
        this.analysisByCategory = new ArrayList<Data>();
        this.analysisByData = new ArrayList<Data>();
        this.timeData = new ArrayList<Data>();
    }

    public void setAchievement_rate(float achievement_rate) {
        this.achievement_rate = achievement_rate;
    }

    public float getAchievement_rate() {
        return this.achievement_rate;
    }

}
