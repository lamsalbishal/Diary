package com.example.bisha.diary.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.bisha.diary.ADDFAvourite;
import com.example.bisha.diary.DatabaseHelper;
import com.example.bisha.diary.DeleteUpdateFavouritePage;
import com.example.bisha.diary.FavouritePage;
import com.example.bisha.diary.Helper.FavouriteList;
import com.example.bisha.diary.Helper.FavouritePageList;
import com.example.bisha.diary.R;

import java.util.ArrayList;

public class FavouritePageAdapter extends ArrayAdapter<FavouritePageList> {

    int id;
    Context context;
    DatabaseHelper databaseHelper;
    public FavouritePageAdapter(@NonNull Context context, ArrayList<FavouritePageList> list) {
        super(context,0,list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = LayoutInflater.from(context).inflate(R.layout.favourite_page_style,null);
        databaseHelper = new DatabaseHelper(context);
        TextView favouritePageTitleView = view.findViewById(R.id.favouritePageTitleView);
        TextView favouritePageDesView = view.findViewById(R.id.favouritePageDesView);
        TextView favouritePageDateView = view.findViewById(R.id.favouritePageDateView);


        ImageView favouritePageImageView = view.findViewById(R.id.favouritePageimageView);
        final FavouritePageList info = getItem(position);


        favouritePageTitleView.setText(info.title);
        favouritePageDesView.setText(info.description);
        favouritePageDateView.setText(info.date);

        if(info.image != null)
        {
            favouritePageImageView.setImageBitmap(ADDFAvourite.getBitmap(info.image));
        }



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,DeleteUpdateFavouritePage.class);
                i.putExtra("id",Integer.parseInt(info.id));
                context.startActivity(i);

            }
        });
        return view;


    }




}
