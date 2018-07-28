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
import com.example.bisha.diary.Helper.BirthdayList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBirthday extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

     EditText frnName,frnBirthday,frnAddress,frnPhone;
     ImageView frnimage,cancleBtn,cameraChoose,galleryChoose;
     Button frnBirtdate;

    int PICK_PHOTO_FOR_AVATAR = 100;
    DatabaseHelper databaseHelper;
    Dialog dialog;
    final static int Result_load_image = 0;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);

        databaseHelper = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id",0);
        dialog = new Dialog(this);

        frnName = (EditText) findViewById(R.id.frnName);
        frnBirthday = (EditText) findViewById(R.id.frnBirthDay);
        frnAddress = (EditText) findViewById(R.id.frnAddress);
        frnPhone = (EditText) findViewById(R.id.frnPhone);
        frnimage = (ImageView) findViewById(R.id.frnImage);
        frnBirtdate = (Button) findViewById(R.id.frnBirthDate);

        frnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"date Picker");
            }
        });

        if(id != 0)
        {
            BirthdayList info = databaseHelper.singlebirthdaySelect(id);
            frnName.setText(info.frnname);
            frnAddress.setText(info.frnaddress);
            frnBirthday.setText(info.frnbirthday);
            frnPhone.setText(info.frnphone);
            if(info.frnimage != null)
            {
                frnimage.setImageBitmap(ADDFAvourite.getBitmap(info.frnimage));
            }
        }



        frnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
            }
        });

        frnBirtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();

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
            frnimage.setImageBitmap(bitmap);
        }else if(requestCode == Result_load_image && resultCode == RESULT_OK)
        {
            Uri contentURI = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
            frnimage.setImageBitmap(bitmap);

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
        String insFrnName = frnName.getText().toString();
        String insFrnBirthday = frnBirthday.getText().toString();
        String insFrnAddress = frnAddress.getText().toString();
        String insFrnPhone = frnPhone.getText().toString();


        ContentValues contentValues = new ContentValues();
        contentValues.put("frnname",insFrnName);
        contentValues.put("frnbirthday",insFrnBirthday);
        contentValues.put("frnaddress",insFrnAddress);
        contentValues.put("frnphone",insFrnPhone);
        if(bitmap != null)
        {
            contentValues.put("frnimage",getBlob(bitmap));
        }

        if(id == 0)
        {
            databaseHelper.birthdayinsert(contentValues);
            Toast.makeText(AddBirthday.this, "Successfully insert", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(AddBirthday.this,BirthDay.class));
            finish();
        }else
        {
            databaseHelper.updateBithDay(id,contentValues);
            Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker,int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        frnBirthday.setText(currentDate);
    }
}
