package com.example.bisha.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bisha.diary.Helper.BirthdayList;

public class SingleBirthDayPage extends AppCompatActivity {

    int id;
    TextView name,address,birthday,phone;
    ImageView imageView,update,delete;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_birth_day_page);

        databaseHelper = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id",0);

        name = (TextView) findViewById(R.id.singlefrnName);
        address = (TextView) findViewById(R.id.singlefrnAddress);
        birthday = (TextView) findViewById(R.id.singlefrnBirthDay);
        phone = (TextView) findViewById(R.id.singlefrnPhone);
        imageView = (ImageView) findViewById(R.id.singleFrnImage);
        update = (ImageView) findViewById(R.id.updateBirthdayPage);
        delete = (ImageView) findViewById(R.id.deleteBirthdayPage);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SingleBirthDayPage.this,AddBirthday.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        select();

    }

    @Override
    protected void onResume() {
        super.onResume();
        select();
    }

    public void select()
    {
        BirthdayList info = databaseHelper.singlebirthdaySelect(id);
        name.setText(info.frnname);
        address.setText(info.frnaddress);
        birthday.setText(info.frnbirthday);
        phone.setText(info.frnphone);
        if(info.frnimage != null)
        {
            imageView.setImageBitmap(ADDFAvourite.getBitmap(info.frnimage));
        }

    }

    public  void showDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete ");
        dialog.setMessage("Are you sure");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                databaseHelper.deleteBirthDay(id);
                finish();

            }
        });

        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
}
