package com.example.ready.studytimemanagement.control;

import android.util.JsonReader;
import android.util.Log;

import com.example.ready.studytimemanagement.model.Data;
import com.example.ready.studytimemanagement.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @biref Class to exchange JSON data with server
 */
public class ReadJSON {

    /**
     * @biref method to call private method named readUser.
     * to be used from RequestHttpConnection Class
     * @param in Inputstream object
     * @return User object received from server
     * @throws IOException
     */
    public User readJsonUser(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readUser(reader);
        } finally {
            reader.close();
        }
    }

    /**
     * @biref receive user data of JSON type from server and parse it.
     * @param reader JsonReader Object
     * @return User received data from server
     * @throws IOException
     */
    private User readUser(JsonReader reader) throws IOException {
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

    /**
     * @brief  method to call private method named readTimeArray.
     * to be used from RequestHttpConnection Class
     * @param in InputStream Object
     * @return ArrayList<Data>
     * @throws IOException
     */
    public ArrayList<Data> readJsonTime(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            Log.d("get_time", "readJSONTime()");
            return readTimeArray(reader);
        } finally {
            reader.close();
        }

    }

    /**
     * @brief  method to call private method named readTime.
     * @param reader JsonRead Object
     * @return ArrayList<Data> data list received from server
     * @throws IOException
     */
    private ArrayList<Data> readTimeArray(JsonReader reader) throws IOException {
        ArrayList<Data> time_list = new ArrayList<Data>();
        reader.beginArray();
        while (reader.hasNext()) {
            time_list.add(readTime(reader));
        }
        reader.endArray();
        Log.d("get_time", "readTimeArray()");

        return time_list;
    }

    /**
     * @biref receive time data of JSON type from server and parse it.
     * @param reader JsonReader Object
     * @return Data data received from server
     * @throws IOException
     */
    private Data readTime(JsonReader reader) throws IOException {
        Data time = new Data();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("category")) {
                time.setCategory(reader.nextString());
            } else if (name.equals("regdate")) {
                time.setDate(reader.nextString());
            } else if (name.equals("amount")) {
                time.setAmount(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("get_time", "readTime()");
        return time;
    }
}
