package com.example.ready.studytimemanagement.model;

import java.util.ArrayList;

/**
 * @brief Model Class with data analysis
 */
public class AnalysisData {
    ArrayList<Data> analysis_category;
    ArrayList<Data> analysis_period;
    ArrayList<Data> time_data;
    float achievement_rate;

    /**
     * @brief Constructor
     */
    public AnalysisData(){
        this.analysis_category = new ArrayList<Data>();
        this.analysis_period = new ArrayList<Data>();
        this.time_data = new ArrayList<Data>();
    }

    public ArrayList<Data> getAnalysis_category() {
        return analysis_category;
    }

    public void setAnalysis_category(ArrayList<Data> analysis_category) {
        this.analysis_category = analysis_category;
    }

    public ArrayList<Data> getAnalysis_period() {
        return analysis_period;
    }

    public void setAnalysis_period(ArrayList<Data> analysis_period) {
        this.analysis_period = analysis_period;
    }

    public ArrayList<Data> getTime_data() {
        return time_data;
    }

    public void setTime_data(ArrayList<Data> time_data) {
        this.time_data = time_data;
    }

    public void setAchievement_rate(float achievement_rate) {
        this.achievement_rate = achievement_rate;
    }

    public float getAchievement_rate() {
        return this.achievement_rate;
    }

}
