package com.example.bisha.diary.Adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bisha.diary.AddImageView;
import com.example.bisha.diary.DatabaseHelper;
import com.example.bisha.diary.Helper.ImageViewList;
import com.example.bisha.diary.R;

public class DetailImageViewFragment extends Fragment {

    int id ;
    TextView imageViewTitleView;
    TextView imageViewDateView;
    ImageView imageViewImageView;

    DatabaseHelper databaseHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");
        id = id+1;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

           View  view = inflater.inflate(R.layout.image_swap_style,null);
           databaseHelper = new DatabaseHelper(getActivity());

         imageViewTitleView = view.findViewById(R.id.imageviewTitleView);
         imageViewDateView = view.findViewById(R.id.imageviewDateView);
         imageViewImageView = view.findViewById(R.id.imageviewImageView);

         select();
        return view;
    }


    public void select()
    {
        ImageViewList info = databaseHelper.imageinfoViewSelect(id);
        imageViewTitleView.setText(info.title);
        imageViewDateView.setText(info.date);
        if(info.image != null)
        {
            imageViewImageView.setImageBitmap(AddImageView.getBitmap(info.image));
        }

    }


}
