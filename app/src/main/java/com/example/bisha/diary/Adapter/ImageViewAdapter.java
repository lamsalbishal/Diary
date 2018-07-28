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

import com.example.bisha.diary.AddImageView;
import com.example.bisha.diary.AddMemorial;
import com.example.bisha.diary.FavouritePage;
import com.example.bisha.diary.Helper.ImageViewList;
import com.example.bisha.diary.Helper.MemorialList;
import com.example.bisha.diary.ImageViewSwap;
import com.example.bisha.diary.R;

import java.util.ArrayList;

public class ImageViewAdapter extends ArrayAdapter<ImageViewList> {

    Context context;
    public ImageViewAdapter(@NonNull Context context, ArrayList<ImageViewList> list) {
        super(context, 0,list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = LayoutInflater.from(context).inflate(R.layout.image_view_style,null);
        TextView imageViewTitleView = view.findViewById(R.id.imageviewTitleView);
        TextView imageViewDateView = view.findViewById(R.id.imageviewDateView);



        ImageView imageViewImageView = view.findViewById(R.id.imageviewImageView);

        final ImageViewList info = getItem(position);

        imageViewTitleView.setText(info.title);
        imageViewDateView.setText(info.date);

        if(info.image != null)
        {
           imageViewImageView.setImageBitmap(AddImageView.getBitmap(info.image));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ImageViewSwap.class);
                context.startActivity(i);

            }
        });

        return view;


    }
}
