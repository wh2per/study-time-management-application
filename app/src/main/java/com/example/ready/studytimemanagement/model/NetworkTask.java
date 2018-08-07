package com.example.ready.studytimemanagement.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ready.studytimemanagement.control.RequestHttpConnection;

import java.util.HashMap;
import java.util.Iterator;

public class NetworkTask extends AsyncTask<Void, Void, String> {
    private String url;

    public NetworkTask(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
        HashMap<String, Long> test = requestHttpConnection.getClassfiedTime(url, "jorku@konkuk.ac.kr");
        Iterator<String> iterator = test.keySet().iterator();
        while(iterator.hasNext()) {
            String key = (String) iterator.next();
            Log.d("classify", "key = "+key+", value = "+ test.get(key));
        }
        return null;
    }
}
