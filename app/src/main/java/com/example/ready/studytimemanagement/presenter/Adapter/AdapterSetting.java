package com.example.ready.studytimemanagement.presenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ready.studytimemanagement.presenter.Activity.FacebookLoginActivity;
import com.example.ready.studytimemanagement.presenter.Activity.GoogleLoginActivity;
import com.example.ready.studytimemanagement.presenter.Activity.KakaoLoginActivity;
import com.example.ready.studytimemanagement.presenter.Activity.MainActivity;
import com.example.ready.studytimemanagement.presenter.Controller.LogfileController;
import com.example.ready.studytimemanagement.presenter.Item.ItemSetting;
import com.example.ready.studytimemanagement.presenter.Itemview.ItemViewSetting;

import java.util.ArrayList;

public class AdapterSetting extends BaseAdapter{
    ArrayList<ItemSetting> items = new ArrayList<ItemSetting>();
    private MainActivity mainActivity;
    private Context context;
    private LogfileController lfc;
    final String filename = "userlog.txt";

    public AdapterSetting(MainActivity m, Context c){
        this.mainActivity = m;
        this.context = c;
        lfc = new LogfileController();
    }
    public void addItem(ItemSetting item){
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
        final int mode = i;
        ItemViewSetting v = new ItemViewSetting(context);
        ItemSetting item = items.get(i);
        v.setMenuText(item.getMenuTitle());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mode){
                    case 3:
                        Log.e("deb/logout", "in");
                        lfc.WriteLogFile(context,filename,"",2);
                        lfc.WriteLogFile(context,filename,"nofile",2);

                        if(mainActivity.getSns().equals("1")) {
                            Intent intent = new Intent(mainActivity, GoogleLoginActivity.class);
                            intent.putExtra("InOut", 2);
                            mainActivity.startActivity(intent);
                        }else if(mainActivity.getSns().equals("2")){
                            Intent intent = new Intent(mainActivity, FacebookLoginActivity.class);
                            intent.putExtra("InOut", 2);
                            mainActivity.startActivity(intent);
                        }else if(mainActivity.getSns().equals("3")){
                            Intent intent = new Intent(mainActivity, KakaoLoginActivity.class);
                            intent.putExtra("InOut", 2);
                            mainActivity.startActivity(intent);
                        }
                        break;
                }
            }
        });
        return v;
    }
}
