package com.example.ready.studytimemanagement.presenter.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ready.studytimemanagement.presenter.Item.ItemAnalysis;
import com.example.ready.studytimemanagement.presenter.Itemview.ItemViewAnalysis;

import java.util.ArrayList;

public class AdapterAnalysis extends BaseAdapter{
    private ArrayList<ItemAnalysis> items = new ArrayList<ItemAnalysis>();
    private Context context;

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
        v.setCombinedChart();

        //v.setBarChart();
        //v.setLineChart();
        return v;
    }
}
