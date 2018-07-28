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

public class Favourite extends Fragment {


    Context context;
    DatabaseHelper databaseHelper;
    ListView listView;
    FavouriteListAdapter favouriteListAdapter;
    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favourite,null);
        databaseHelper = new DatabaseHelper(context);

        listView = view.findViewById(R.id.listviewFavourite);
        favouriteListAdapter = new FavouriteListAdapter(context,databaseHelper.favouriteSelect());
        listView.setAdapter(favouriteListAdapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favouriteListAdapter = new FavouriteListAdapter(context,databaseHelper.favouriteSelect());
        listView.setAdapter(favouriteListAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
