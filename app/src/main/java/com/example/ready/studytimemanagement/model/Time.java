package com.example.ready.studytimemanagement.model;

import java.util.ArrayList;

public class Time {
    ArrayList<Data> data_list;
    String total_time;

    public Time() {
        data_list = new ArrayList<Data>();
    }

    public void addData(Data new_data) {
        data_list.add(new_data);
    }

    public ArrayList<Data> getData_list() {
        return data_list;
    }

    public void setData_list(ArrayList<Data> data_list) {
        this.data_list = data_list;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getTotal_time() {
        return this.total_time;
    }
}
