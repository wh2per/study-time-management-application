package com.example.ready.studytimemanagement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class AdapterAnalysis extends BaseAdapter{
    ArrayList<ItemAnalysis> items = new ArrayList<ItemAnalysis>();
    Context context;
    public AdapterAnalysis(Context c){
        this.context = c;
    }
    public void addItem(ItemAnalysis item){
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
        ItemViewAnalysis v = new ItemViewAnalysis(context);
        ItemAnalysis item = items.get(i);
        v.setTitleText(item.getTitle());
        v.setSubText(item.getSubTitle());
        return v;
    }
}
