package com.example.bisha.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bisha.diary.Helper.FavouritePageList;

public class DeleteUpdateFavouritePage extends AppCompatActivity {

    ImageView delete,update,singlefavouritePageimageView;
    int id;
    DatabaseHelper databaseHelper;
    TextView singlefavouritePageTitleView,singlefavouritePageDateView,singlefavouritePageDesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_update_favourite_page);

        databaseHelper = new DatabaseHelper(this);
        delete = (ImageView) findViewById(R.id.deleteFavouritePage);
        update = (ImageView) findViewById(R.id.updateFavouritePage);
        singlefavouritePageDateView = (TextView) findViewById(R.id.singlefavouritePageDateView);
        singlefavouritePageDesView = (TextView) findViewById(R.id.singlefavouritePageDesView);
        singlefavouritePageTitleView = (TextView) findViewById(R.id.singlefavouritePageTitleView);
        singlefavouritePageimageView = (ImageView) findViewById(R.id.singlefavouritePageimageView);

        id = getIntent().getIntExtra("id",0);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeleteUpdateFavouritePage.this,add_favourite_page.class);
                i.putExtra("id",id);
                startActivity(i);
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
        FavouritePageList info = databaseHelper.singlefavouritePageSelect(id);
        singlefavouritePageTitleView.setText(info.title);
        singlefavouritePageDesView.setText(info.description);
        singlefavouritePageDateView.setText(info.date);
        if(info.image != null)
        {
            singlefavouritePageimageView.setImageBitmap(ADDFAvourite.getBitmap(info.image));
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

                databaseHelper.deleteFavouritePage(id);
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
