package com.example.ready.studytimemanagement.presenter;

import android.util.JsonReader;
import android.util.Log;

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

    public List<User> readUserArray(JsonReader reader) throws IOException {
        List<User> users = new ArrayList<User>();

        reader.beginObject();
        while (reader.hasNext()) {
            users.add(readUser(reader));
        }
        reader.endArray();
        return users;
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
}
