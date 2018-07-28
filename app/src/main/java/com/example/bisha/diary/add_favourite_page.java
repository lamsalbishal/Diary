package com.example.bisha.diary;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bisha.diary.Adapter.DatePickerFragment;
import com.example.bisha.diary.Helper.FavouritePageList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class add_favourite_page extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageView favouriteImagePage,cancleBtn,cameraChoose,galleryChoose;
    EditText favouriteTitlePage,favouriteDesPage,favouriteDatePage;
    Button favouriteBtnPage;

    int id,postid;
    DatabaseHelper databaseHelper;

    int PICK_PHOTO_FOR_AVATAR = 100;
    Dialog dialog;
    final static int Result_load_image = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourite_page);

        id = getIntent().getIntExtra("id",0);
        postid = getIntent().getIntExtra("postid",0);
        databaseHelper = new DatabaseHelper(this);
        dialog = new Dialog(this);

       favouriteBtnPage = (Button)findViewById(R.id.favouriteBtnPage);
       favouriteDesPage = (EditText) findViewById(R.id.favouriteDesPage);
       favouriteTitlePage = (EditText) findViewById(R.id.favouriteTitlePage);
       favouriteImagePage = (ImageView) findViewById(R.id.favouriteImagePage);
       favouriteDatePage = (EditText) findViewById(R.id.favouriteDatePage);

        favouriteDatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"date Picker");
            }
        });

       favouriteImagePage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              gallery();
           }
       });

       favouriteBtnPage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               insert();


           }
       });

       if(id != 0)
       {
           FavouritePageList info = databaseHelper.singlefavouritePageSelect(id);
           favouriteTitlePage.setText(info.title);
           favouriteDesPage.setText(info.description);
           favouriteDatePage.setText(info.date);
           if(info.image != null)
           {
               favouriteImagePage.setImageBitmap(ADDFAvourite.getBitmap(info.image));
           }

           ((Button)findViewById(R.id.favouriteBtnPage)).setText("Update");

       }
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
            favouriteImagePage.setImageBitmap(bitmap);
        }else if(requestCode == Result_load_image && resultCode == RESULT_OK)
        {
            Uri contentURI = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
            favouriteImagePage.setImageBitmap(bitmap);

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


    public void insert()
    {
        String insfavoutiteTitlePage = favouriteTitlePage.getText().toString();
        String insfavouriteDesPage = favouriteDesPage.getText().toString();
        String insfavouriteDatePage = favouriteDatePage.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title",insfavoutiteTitlePage);
        contentValues.put("description",insfavouriteDesPage);
        contentValues.put("date",insfavouriteDatePage);
        contentValues.put("postid",postid);

        if(bitmap != null)
        {
            contentValues.put("image",getBlob(bitmap));
        }


        if(id == 0)
        {

            databaseHelper.favouritePageInsert(contentValues);
            Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show();
           //startActivity(new Intent(add_favourite_page.this,FavouritePage.class));
            finish();

        } else
        {

            databaseHelper.updateFavouritePage(id,contentValues);
            Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(add_favourite_page.this,FavouritePage.class));
            finish();
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker,  int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        favouriteDatePage.setText(currentDate);
    }
}
