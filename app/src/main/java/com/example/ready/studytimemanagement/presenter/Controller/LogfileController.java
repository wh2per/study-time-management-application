package com.example.ready.studytimemanagement.presenter.Controller;

import android.content.Context;
import android.util.Log;

import com.example.ready.studytimemanagement.presenter.Activity.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogfileController extends BaseActivity {

    public LogfileController(){

    }
    //텍스트내용을 경로의 텍스트 파일에 쓰기
    public void WriteLogFile(Context cont,String filename, String contents){
        try{
            //파일 output stream 생성
            FileOutputStream fos = cont.openFileOutput(filename, MODE_APPEND);      //del이 true면 이어서 저장
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(contents);
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //경로의 텍스트 파일읽기
    public String ReadLogFile(Context cont,String filename){
        StringBuffer strBuffer = new StringBuffer();
        try{
            FileInputStream fis = cont.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line="";
            while((line=reader.readLine())!=null){
                strBuffer.append(line);
            }
            reader.close();
            fis.close();
            Log.d("userlog", strBuffer.toString());
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
        return strBuffer.toString();
    }
}
