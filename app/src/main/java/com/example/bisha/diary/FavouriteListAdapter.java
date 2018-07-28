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

import com.example.bisha.diary.Helper.FavouriteList;

import java.util.ArrayList;

public class FavouriteListAdapter extends ArrayAdapter<FavouriteList> {

    Context context;
    public FavouriteListAdapter(@NonNull Context context, ArrayList<FavouriteList> list) {
        super(context, 0,list);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = LayoutInflater.from(context).inflate(R.layout.activity_favourite_list_style,null);
        TextView  tittleFavoutiteStyle = view.findViewById(R.id.tittleFavouriteStyle);
        TextView desFavouriteStyle = view.findViewById(R.id.desFavoutiteStyle);
        ImageView imageFavouriteStyle = view.findViewById(R.id.imageFavoutiteStyle);
        final FavouriteList info = getItem(position);

        tittleFavoutiteStyle.setText(info.tittle);
        desFavouriteStyle.setText(info.description);

        if(info.image != null)
        {
            imageFavouriteStyle.setImageBitmap(ADDFAvourite.getBitmap(info.image));
        }

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(context,FavouritePage.class);
               i.putExtra("postid",Integer.parseInt(info.id));
               context.startActivity(i);

           }
       });
        return view;


    }
}
