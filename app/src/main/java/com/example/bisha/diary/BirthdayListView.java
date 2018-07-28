package com.example.bisha.diary;

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

import com.example.bisha.diary.Helper.BirthdayList;
import com.example.bisha.diary.Helper.FavouriteList;

import java.util.ArrayList;

public class BirthdayListView extends ArrayAdapter<BirthdayList> {

    Context context;

    public BirthdayListView(@NonNull Context context, ArrayList<BirthdayList> list) {
        super(context, 0,list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = LayoutInflater.from(context).inflate(R.layout.activity_birthday_list_style,null);
        TextView frnNameView  = view.findViewById(R.id.frnNameView);
        TextView frnBirthdayView = view.findViewById(R.id.frnBirthdayView);
        ImageView frnImageView = view.findViewById(R.id.frnImageView);

        final BirthdayList info = getItem(position);

        frnNameView.setText(info.frnname);
        frnBirthdayView.setText(info.frnbirthday);

        if(info.frnimage != null)
        {
            frnImageView.setImageBitmap(AddBirthday.getBitmap(info.frnimage));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BirthdayListView.this.context,SingleBirthDayPage.class);
                i.putExtra("id",Integer.parseInt(info.id));
                context.startActivity(i);
            }
        });

        return view;
    }
}
