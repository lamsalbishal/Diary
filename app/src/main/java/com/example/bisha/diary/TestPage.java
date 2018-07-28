package com.example.bisha.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bisha.diary.Helper.AccountList;
import com.santalu.diagonalimageview.DiagonalImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestPage extends AppCompatActivity {

    LinearLayout linearLayout;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        databaseHelper = new DatabaseHelper(this);
        linearLayout = findViewById(R.id.testLinearLayou);
        //selectAccount();
    }

//    public void selectAccount()
//    {
//        ArrayList<AccountList> lists = databaseHelper.accountSelect();
//
//
//        for(AccountList info:lists)
//        {
//            View view = LayoutInflater.from(this).inflate(R.layout.activity_favourite_list_style,null);
//            TextView userproname = view.findViewById(R.id.tittleFavouriteStyle);
//            TextView userproEmail = view.findViewById(R.id.desFavoutiteStyle);
//            DiagonalImageView diagonalImageView = view.findViewById(R.id.imageFavoutiteStyle);
//            userproname.setText(info.name);
//            userproEmail.setText(info.id);
//
//            if(info.profile != null)
//            {
//                diagonalImageView.setImageBitmap(Account_setting.getBitmap(info.profile));
//            }
//
//            linearLayout.addView(view);
//
//        }
   // }







//
}
