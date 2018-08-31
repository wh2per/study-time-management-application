package com.example.ready.studytimemanagement.presenter.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ready.studytimemanagement.R;
import com.example.ready.studytimemanagement.presenter.Activity.LockActivity;
import com.example.ready.studytimemanagement.presenter.Activity.MainActivity;
import com.example.ready.studytimemanagement.presenter.Adapter.AdapterSetting;
import com.example.ready.studytimemanagement.presenter.Item.ItemSetting;

public class FragmentSetting extends Fragment{
    Button testSignin;
    private TextView textView2;
    public MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_setting, container,false);
        testSignin = rootView.findViewById(R.id.testSignin);

        testSignin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(),LoginActivity.class);
                Intent intent = new Intent(getContext(),LockActivity.class);
                startActivity(intent);
            }
        });

        textView2 = rootView.findViewById(R.id.textView2);
        textView2.setText(mainActivity.name);

        ListView listView = (ListView) rootView.findViewById(R.id.settingList);
        AdapterSetting adapter = new AdapterSetting(getActivity().getApplicationContext());

        adapter.addItem(new ItemSetting("내 계정 관리"));
        adapter.addItem(new ItemSetting("결제 내역 보기"));
        adapter.addItem(new ItemSetting("기기 설정"));
        listView.setAdapter(adapter);

        return rootView;
    }
}
