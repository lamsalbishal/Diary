package com.example.bisha.diary;

import android.content.Context;
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

import com.example.bisha.diary.Adapter.DailyReportAdapter;

public class PopularNews extends Fragment {
    Context context;
    DatabaseHelper databaseHelper;
    ListView dailyReportListView;


    DailyReportAdapter dailyReportAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_popular_news,null);

        databaseHelper = new DatabaseHelper(context);
        dailyReportListView = (ListView) view.findViewById(R.id.dailyReportListView);

        dailyReportAdapter = new DailyReportAdapter(context,databaseHelper.dailyReportSelect());
        dailyReportListView.setAdapter(dailyReportAdapter);



        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        dailyReportAdapter = new DailyReportAdapter(context,databaseHelper.dailyReportSelect());
        dailyReportListView.setAdapter(dailyReportAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
