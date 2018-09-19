package com.example.ready.studytimemanagement.presenter.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Activity.MainActivity;
import com.example.ready.studytimemanagement.presenter.Adapter.AdapterSetting;
import com.example.ready.studytimemanagement.presenter.Item.ItemSetting;

public class FragmentSetting extends Fragment{
    private TextView profileName;
    public MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_setting, container,false);
        mainActivity = (MainActivity) this.getActivity();
        profileName = rootView.findViewById(R.id.profileName);
        profileName.setText(mainActivity.getNickname());
        ListView listView = (ListView) rootView.findViewById(R.id.settingList);
        AdapterSetting adapter = new AdapterSetting(mainActivity,getActivity().getApplicationContext());

        adapter.addItem(new ItemSetting("계정 관리"));
        adapter.addItem(new ItemSetting("결제 내역 보기"));
        adapter.addItem(new ItemSetting("기기 설정"));
        adapter.addItem(new ItemSetting("로그아웃"));
        listView.setAdapter(adapter);

        return rootView;
    }
}
