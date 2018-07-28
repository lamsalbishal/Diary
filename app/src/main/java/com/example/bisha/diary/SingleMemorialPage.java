package com.example.bisha.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bisha.diary.Helper.MemorialList;

public class SingleMemorialPage extends AppCompatActivity {

    ImageView update,delete,imageView;
    TextView date,des,title;

    int id;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_memorial_page);

        update = (ImageView) findViewById(R.id.updateMemorialPage);
        delete = (ImageView) findViewById(R.id.deleteMemorialPage);
        imageView = (ImageView) findViewById(R.id.singlememorialImageView);

        databaseHelper = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id",0);

        date = (TextView) findViewById(R.id.singlememorialDateView);
        des = (TextView) findViewById(R.id.singlememorialDesView);
        title = (TextView) findViewById(R.id.singlememorialTitleView);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SingleMemorialPage.this,AddMemorial.class);
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

        MemorialList info = databaseHelper.singlememorialSelect(id);
        date.setText(info.date);
        des.setText(info.description);
        title.setText(info.title);
        if(info.image != null)
        {
            imageView.setImageBitmap(ADDFAvourite.getBitmap(info.image));
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

                databaseHelper.deleteMemorial(id);
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
