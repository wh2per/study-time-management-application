package com.example.ready.studytimemanagement.presenter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ready.studytimemanagement.R;

public class FragmentAnalysis extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_analysis, container,false);
        ListView listView = (ListView) rootView.findViewById(R.id.analysisList);

        AdapterAnalysis adapter = new AdapterAnalysis(getActivity().getApplicationContext());
        adapter.addItem(new ItemAnalysis("일일 집중 분석", "하루 동안의 집중력을 알려줍니다"));
        adapter.addItem(new ItemAnalysis("주간 집중 분석", "일주일 동안의 집중력을 알려줍니다"));
        adapter.addItem(new ItemAnalysis("월간 집중 분석", "한달 동안의 집중력을 알려줍니다"));

        listView.setAdapter(adapter);
        return rootView;
    }
}
