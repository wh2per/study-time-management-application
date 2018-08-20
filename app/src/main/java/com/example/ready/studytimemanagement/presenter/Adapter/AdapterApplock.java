package com.example.ready.studytimemanagement.presenter.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.example.ready.studytimemanagement.presenter.Item.ItemApplock;
import com.example.ready.studytimemanagement.presenter.Itemview.ItemViewApplock;

import java.util.ArrayList;

public class AdapterApplock extends BaseAdapter{
    private ArrayList<ItemApplock> items = new ArrayList<ItemApplock>();
    private Context context;

    public AdapterApplock(Context c){
        this.context = c;
    }
    public void addItem(ItemApplock item){
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
        ItemViewApplock v = new ItemViewApplock(context);
        final ItemApplock item = items.get(i);
        v.setAppName(item.getTitle());
        v.setAppIcon(item.getAppIcon());
        v.setAppSwitch(item.getAppSwitch());
        v.getAppSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setAppSwitch(b);
            }
        });

        return v;
    }
}
