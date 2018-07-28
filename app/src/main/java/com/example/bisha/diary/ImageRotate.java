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
import android.widget.GridView;

import com.example.bisha.diary.Adapter.ImageViewAdapter;

public class ImageRotate extends Fragment {

    Context context;
    GridView gridView;
    DatabaseHelper databaseHelper;
    ImageViewAdapter imageViewAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_image_rotate,null);

        databaseHelper = new DatabaseHelper(context);
        gridView = view.findViewById(R.id.gridviewImageview);

        imageViewAdapter = new ImageViewAdapter(context,databaseHelper.imageViewSelect());
        gridView.setAdapter(imageViewAdapter);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        imageViewAdapter = new ImageViewAdapter(context,databaseHelper.imageViewSelect());
        gridView.setAdapter(imageViewAdapter);
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }
}
