package com.example.bisha.diary;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;

public class ADDFAvourite extends AppCompatActivity {

    EditText titleFavourite,desFavourite;
    Button  insertFavourite;
    ImageView imageFavourite,cancleBtn,cameraChoose,galleryChoose;
    int PICK_PHOTO_FOR_AVATAR = 100;
    DatabaseHelper databaseHelper;
    Dialog dialog;
    final static int Result_load_image = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfavourite);

        databaseHelper = new DatabaseHelper(this);
        dialog = new Dialog(this);

        imageFavourite = (ImageView) findViewById(R.id.imageFavourite);
        insertFavourite = (Button) findViewById(R.id.insertFavourite);
        titleFavourite = (EditText) findViewById(R.id.titleFavourite);
        desFavourite = (EditText) findViewById(R.id.desFavourite);

        insertFavourite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                insert();


            }
        });

        imageFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gallery();
            }
        });

    }

    public void gallery()
    {
        dialog.setContentView(R.layout.dialog_layout);
        cancleBtn = (ImageView) dialog.findViewById(R.id.cancleBtn);
        cameraChoose = (ImageView) dialog.findViewById(R.id.cameraChoose);
        galleryChoose = (ImageView) dialog.findViewById(R.id.galleryChoose);

        cameraChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
                dialog.dismiss();

            }
        });

        galleryChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Result_load_image);
                dialog.dismiss();
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


    Bitmap bitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageFavourite.setImageBitmap(bitmap);
        }else if(requestCode == Result_load_image && resultCode == RESULT_OK)
        {
            Uri contentURI = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageFavourite.setImageBitmap(bitmap);

        }
    }

    public static byte[] getBlob(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public static  Bitmap getBitmap(byte[] byteArray)
    {
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
    }

    public void insert(){
        String insTittleFavoutite = titleFavourite.getText().toString();
        String insDesFavourite = desFavourite.getText().toString();


        ContentValues contentValues = new ContentValues();
        contentValues.put("tittle",insTittleFavoutite);
        if(bitmap != null)
        {
            contentValues.put("image",getBlob(bitmap));
        }

        contentValues.put("description",insDesFavourite);
        databaseHelper.favouriteInsert(contentValues);
        Toast.makeText(ADDFAvourite.this, "Successfully Insert Data", Toast.LENGTH_SHORT).show();

        finish();
//        Intent i = new Intent(ADDFAvourite.this,Favourite.class);
//        startActivity(i);

    }
}
