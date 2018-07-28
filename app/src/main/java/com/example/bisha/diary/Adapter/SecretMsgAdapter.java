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
import com.example.bisha.diary.AddSecretMessage;
import com.example.bisha.diary.Helper.DailyReportList;
import com.example.bisha.diary.Helper.SecretList;
import com.example.bisha.diary.R;
import com.example.bisha.diary.SingleSecretMessage;

import java.util.ArrayList;

public class SecretMsgAdapter extends ArrayAdapter<SecretList> {

    Context context;
    public SecretMsgAdapter(@NonNull Context context, ArrayList<SecretList> lists) {
        super(context, 0,lists);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = LayoutInflater.from(context).inflate(R.layout.secret_message_list,null);
        TextView secretMessageView = view.findViewById(R.id.secretMessageView);


        ImageView secretImageView = view.findViewById(R.id.secretImageView);
        final SecretList info = getItem(position);

        secretMessageView.setText(info.message);


        if(info.image != null)
        {
           secretImageView.setImageBitmap(AddSecretMessage.getBitmap(info.image));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SecretMsgAdapter.this.context, SingleSecretMessage.class);
                i.putExtra("id",Integer.parseInt(info.id));
                context.startActivity(i);

            }
        });

        return view;


    }
}
