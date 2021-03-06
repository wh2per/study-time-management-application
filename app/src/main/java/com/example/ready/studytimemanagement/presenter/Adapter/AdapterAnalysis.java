package com.example.ready.studytimemanagement.presenter.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ready.studytimemanagement.control.NetworkTask;
import com.example.ready.studytimemanagement.model.User;
import com.example.ready.studytimemanagement.presenter.Activity.MainActivity;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;
import com.example.ready.studytimemanagement.presenter.Item.ItemAnalysis;
import com.example.ready.studytimemanagement.presenter.Itemview.ItemViewAnalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class AdapterAnalysis extends BaseAdapter{
    private final int GRAPH_COUNT = 3;
    private ArrayList<ItemAnalysis> items = new ArrayList<ItemAnalysis>();
    private Context context;
    private HashMap<String, Long>[] analysisData = new HashMap[GRAPH_COUNT];
    private String[] weekdays = {"월", "화", "수", "목", "금", "토", "일"};
    private ArrayList<String>[] xaxis = new ArrayList[GRAPH_COUNT];
    private String sns;
    private User user;
    private LogfileController lfc;
    private static final String filename = "userlog.txt";

    public AdapterAnalysis(Context cont){
        this.context = cont;
        lfc = new LogfileController();

        String line = lfc.ReadLogFile(this.context, filename);
        StringTokenizer tokens = new StringTokenizer(line, ",");

        this.sns = tokens.nextToken();
        if(sns.equals("4") == false) {
            String id = tokens.nextToken();
            String nick = tokens.nextToken();
            int age = Integer.parseInt(tokens.nextToken());
            String job = tokens.nextToken();
            user = new User(id, nick, age, job);
            for (int i = 0; i < GRAPH_COUNT; i++) {
                setFormattedData(i);
            }
        }
    }
    public void addItem(ItemAnalysis item){
        items.add(item);
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemViewAnalysis v = new ItemViewAnalysis(context);
        ItemAnalysis item = items.get(i);
        v.setTitleText(item.getTitle());
        v.setSubText(item.getSubTitle());
        if(this.sns.equals("4") == false)
            v.setCombinedChart(i, analysisData[i], xaxis[i]);
        return v;
    }

    private String setFormattedData(int index) {
        xaxis[index] = new ArrayList<String>();

        NetworkTask asyncNetwork;
        Iterator<String> keys;
        HashMap<String, Long> temp = new HashMap<>();
        String key, temp_x;
        try {
            switch (index) {
                case 0:
                    asyncNetwork = new NetworkTask("/classify-category", user, null);
                    asyncNetwork.execute().get(1000, TimeUnit.MILLISECONDS);
                    analysisData[index] = asyncNetwork.getAnalysisData().getAnalysis_category();
                    Log.e("category size", Integer.toString(analysisData[index].size()));
                    keys = analysisData[index].keySet().iterator();
                    while(keys.hasNext()) {
                        xaxis[index].add(keys.next());
                    }
                    break;
                case 1:
                    asyncNetwork = new NetworkTask("/classify-weekday", user, null);
                    asyncNetwork.execute().get(1000, TimeUnit.MILLISECONDS);
                    analysisData[index] = asyncNetwork.getAnalysisData().getAnalysis_weekday();
                    Log.e("weekday size", Integer.toString(analysisData[index].size()));
                    keys = analysisData[index].keySet().iterator();
                    while(keys.hasNext()) {
                        key = keys.next();
                        Log.e("keys test", key);
                        xaxis[index].add(weekdays[Integer.parseInt(key)]);
                        temp.put(weekdays[Integer.parseInt(key)], analysisData[index].get(key));
                        Log.e("add weekday", weekdays[Integer.parseInt(key)]);
                    }
                    analysisData[index] = temp;
                    break;
                case 2:
                    asyncNetwork = new NetworkTask("/classify-week", user, null);
                    asyncNetwork.execute().get(1000, TimeUnit.MILLISECONDS);
                    analysisData[index] = asyncNetwork.getAnalysisData().getAnalysis_week();
                    Log.e("week size", Integer.toString(analysisData[index].size()));
                    List sortedKeys = new ArrayList(analysisData[index].keySet());
                    Collections.sort(sortedKeys);
                    for(int i = 0; i < sortedKeys.size(); i++) {
                        temp_x = "~"+sortedKeys.get(i);
                        Log.e("keys test", temp_x);
                        xaxis[index].add(temp_x);
                        temp.put(temp_x, analysisData[index].get(sortedKeys.get(i)));
                    }
                    analysisData[index] = temp;
                    break;
                default:
                    return "switch fail";
            }
            xaxis[index].add(0, "");
            xaxis[index].add("");

        } catch(Exception e) {
            return e.toString();
        }
        return "Success";
    }

}
