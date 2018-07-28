package com.example.bisha.diary;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bisha.diary.Helper.AccountList;

import java.io.ByteArrayOutputStream;
import java.time.Instant;

import de.hdodenhof.circleimageview.CircleImageView;

public class Account_setting extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText userName,userEamil;
    ImageView userProfile;
    Button accountInsert;


    int PICK_PHOTO_FOR_Profile = 100;
    int id;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        databaseHelper = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id",0);

        preferences = getSharedPreferences("UserDetail",0);


        userName = (EditText) findViewById(R.id.userName);
        userEamil = (EditText) findViewById(R.id.userEmail);
        userProfile = (ImageView) findViewById(R.id.userProfile);
        accountInsert = (Button) findViewById(R.id.accountInsert);


        if(id != 0)
        {
            AccountList info = databaseHelper.accountInfoSelect(id);
            userName.setText(info.name);
            userEamil.setText(info.email);
            if (info.profile != null) {
                userProfile.setImageBitmap(AddDailyReport.getBitmap(info.profile));
            }
            ((Button) findViewById(R.id.accountInsert)).setText("Update");
        }



        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PICK_PHOTO_FOR_Profile);
            }
        });


        accountInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();

            }
        });

    }

    Bitmap bitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_Profile && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            userProfile.setImageBitmap(bitmap);
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
        String insusername = userName.getText().toString();
        String insuseremail = userEamil.getText().toString();


        ContentValues contentValues = new ContentValues();
        contentValues.put("name",insusername);
        contentValues.put("email",insuseremail);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id","1");
        editor.putString("name",insusername);

        if(bitmap != null)
        {
            contentValues.put("profile",getBlob(bitmap));
        }

        if(id == 0)
        {
            editor.apply();
            databaseHelper.accountInsert(contentValues);
            Toast.makeText(Account_setting.this, "Successfully insert data ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Account_setting.this,TestPage.class));
            finish();
        }else {
            databaseHelper.updateProfile(id,contentValues);
            Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }



    }
}
