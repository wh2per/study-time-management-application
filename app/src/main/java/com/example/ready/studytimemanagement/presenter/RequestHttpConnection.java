package com.example.ready.studytimemanagement.presenter;

import android.util.JsonReader;
import android.util.Log;

import com.example.ready.studytimemanagement.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHttpConnection {

    public static String POST(String url, User user){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", user.getId());
            jsonObject.accumulate("nickname", user.getNickname());
            jsonObject.accumulate("age", Integer.toString(user.getAge()));
            jsonObject.accumulate("job", user.getJob());

            // convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.e("post_data", json);

            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            os.close();

            is = httpCon.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(is, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseReader);
            try {
                // convert inputstream to string
                /*
                if(is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!";
                */
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                is.close();
                responseReader.close();
                jsonReader.close();
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }
        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
