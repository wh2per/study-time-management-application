package com.example.ready.studytimemanagement.control;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ready.studytimemanagement.control.RequestHttpConnection;
import com.example.ready.studytimemanagement.model.AnalysisData;
import com.example.ready.studytimemanagement.model.Data;
import com.example.ready.studytimemanagement.model.User;

import java.util.HashMap;
import java.util.Iterator;

public class NetworkTask extends AsyncTask<Void, Void, String> {
    private String url;
    private User user;
    private Data data;
    private AnalysisData analysisData;
    private RequestHttpConnection requestHttpConnection;
    private final String[] weekdays = {"월", "화", "수", "목", "금", "토", "일"};

    public NetworkTask(String url, User user, Data data) {
        this.url = url;
        this.user = user;
        this.data = data;
        this.analysisData = new AnalysisData();
    }

    @Override
    protected String doInBackground(Void... params) {
        requestHttpConnection = new RequestHttpConnection();
        HashMap<String, Long> temp;
        Iterator<String> keys;
        HashMap<String, Long> analysis;
        String result = "";
        switch(url) {
            case "/register-user":
                result = requestHttpConnection.registerUser(url, user);
                Log.d("register-user", result);
                break;
            case "/check-user":
                User verifiedUser = requestHttpConnection.getUser(url, user.getId());
                if(verifiedUser != null)
                    Log.d("check-user", "success");
                else
                    Log.d("check-user", "fail");
                break;
            case "/register-time":
                result = requestHttpConnection.registerTime(url, user.getId(), data);
                Log.d("register-time", result);
                break;
            case "/classify-category":
                analysisData.setAnalysis_category(requestHttpConnection.getClassfiedTime(url, user.getId()));
                break;
            case "/classify-week":
                analysisData.setAnalysis_week(requestHttpConnection.getClassfiedTime(url, user.getId()));
                break;
            case "/classify-weekday":
//                temp = requestHttpConnection.getClassfiedTime(url, user.getId());
//                keys = temp.keySet().iterator();
//                analysis = new HashMap<>();
//                while(keys.hasNext()) {
//                    String key = keys.next();
//                    analysis.put(weekdays[Integer.parseInt(key)], temp.get(key));
//                }
//                analysisData.setAnalysis_weekday(analysis);
                analysisData.setAnalysis_weekday(requestHttpConnection.getClassfiedTime(url, user.getId()));
                break;
            default:
                Log.e("url error", "inappropriate url: " + url);
                break;
        }
        return result;
    }

    public AnalysisData getAnalysisData() {
        Log.e("analysis test", Integer.toString(analysisData.getAnalysis_category().size()));
        return analysisData;
    }
}
