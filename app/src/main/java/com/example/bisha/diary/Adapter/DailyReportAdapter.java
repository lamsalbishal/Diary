package com.example.bisha.diary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bisha.diary.ADDFAvourite;
import com.example.bisha.diary.AddDailyReport;
import com.example.bisha.diary.DeleteUpdateFavouritePage;
import com.example.bisha.diary.FavouritePage;
import com.example.bisha.diary.Helper.DailyReportList;
import com.example.bisha.diary.Helper.FavouritePageList;
import com.example.bisha.diary.R;
import com.example.bisha.diary.SingleViewDailyReport;

import java.util.ArrayList;

public class DailyReportAdapter extends ArrayAdapter<DailyReportList> {

    Context context;
    public DailyReportAdapter(@NonNull Context context, ArrayList<DailyReportList> lists) {
        super(context, 0,lists);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = LayoutInflater.from(context).inflate(R.layout.daily_report_style,null);
        TextView dailyReportTitleView = view.findViewById(R.id.dailyReportTitleView);
        TextView dailyReportDateView = view.findViewById(R.id.dailyReportDateView);


        ImageView dailyReportImageView = view.findViewById(R.id.dailyReportImageView);
        final DailyReportList info = getItem(position);

        dailyReportTitleView.setText(info.title);
        dailyReportDateView.setText(info.date);

        if(info.image != null)
        {
            dailyReportImageView.setImageBitmap(AddDailyReport.getBitmap(info.image));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,SingleViewDailyReport.class);
                i.putExtra("id",Integer.parseInt(info.id));
                context.startActivity(i);

            }
        });

        return view;


    }
}
