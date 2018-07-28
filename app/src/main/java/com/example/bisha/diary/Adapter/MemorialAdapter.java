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

import com.example.bisha.diary.AddDailyReport;
import com.example.bisha.diary.AddMemorial;
import com.example.bisha.diary.Helper.DailyReportList;
import com.example.bisha.diary.Helper.MemorialList;
import com.example.bisha.diary.R;
import com.example.bisha.diary.SingleMemorialPage;

import java.util.ArrayList;

public class MemorialAdapter extends ArrayAdapter<MemorialList> {

    Context context;
    public MemorialAdapter(@NonNull Context context, ArrayList<MemorialList> lists) {
        super(context, 0,lists);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = LayoutInflater.from(context).inflate(R.layout.memorial_style,null);
        TextView memorialTitleView = view.findViewById(R.id.memorialTitleView);
        TextView memorialDateView = view.findViewById(R.id.memorialDateView);
        TextView memorialDesView = view.findViewById(R.id.memorialDesView);


        ImageView memorialImageView = view.findViewById(R.id.memorialImageView);
        final MemorialList info = getItem(position);

        memorialTitleView.setText(info.title);
        memorialDateView.setText(info.date);
        memorialDesView.setText(info.description);

        if(info.image != null)
        {
            memorialImageView.setImageBitmap(AddMemorial.getBitmap(info.image));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MemorialAdapter.this.context, SingleMemorialPage.class);
                i.putExtra("id",Integer.parseInt(info.id));
                context.startActivity(i);

            }
        });
        return view;


    }
}
