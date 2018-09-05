package com.example.ready.studytimemanagement.presenter.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ready.studytimemanagement.presenter.Item.ItemSetting;
import com.example.ready.studytimemanagement.presenter.Itemview.ItemViewSetting;

import java.util.ArrayList;

public class AdapterSetting extends BaseAdapter{
    ArrayList<ItemSetting> items = new ArrayList<ItemSetting>();
    Context context;

    public AdapterSetting(Context c){
        this.context = c;
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
                        Log.v("setting case","logout");
                        break;
                }
            }
        });
        return v;
    }
}
