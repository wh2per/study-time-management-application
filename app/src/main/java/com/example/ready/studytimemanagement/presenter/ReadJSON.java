package com.example.ready.studytimemanagement.presenter;

import android.util.JsonReader;
import android.util.Log;

import com.example.ready.studytimemanagement.model.Data;
import com.example.ready.studytimemanagement.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadJSON {
    public User readJsonUser(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readUser(reader);
        } finally {
            reader.close();
        }
    }

    public User readUser(JsonReader reader) throws IOException {
        User user = new User();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                user.setId(reader.nextString());
            } else if (name.equals("nickname")) {
                user.setNickname(reader.nextString());
            } else if (name.equals("job")) {
                user.setJob(reader.nextString());
            } else if (name.equals("age")) {
                user.setAge(reader.nextInt());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("json user id", user.getId());
        return user;
    }

    public List<Data> readJsonTime(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readTimeArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<Data> readTimeArray(JsonReader reader) throws IOException {
        List<Data> time_list = new ArrayList<Data>();

        reader.beginObject();
        while (reader.hasNext()) {
            time_list.add(readTime(reader));
        }
        reader.endArray();
        return time_list;
    }

    public Data readTime(JsonReader reader) throws IOException {
        Data time = new Data();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("category")) {
                time.setCategory(reader.nextString());
            } else if (name.equals("date")) {
                time.setDate(reader.nextString());
            } else if (name.equals("amount")) {
                time.setAmount(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("json time category", time.getCategory());
        return time;
    }
}
