package com.example.bisha.diary;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bisha.diary.Helper.SecretList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddSecretMessage extends AppCompatActivity {

    ImageView secretImage,cancleBtn,cameraChoose,galleryChoose;
    EditText secretMessage;
    Button secretBtn;

    int PICK_PHOTO_FOR_AVATAR = 100;
    DatabaseHelper databaseHelper;
    int id;
    Dialog dialog;
    final static int Result_load_image = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_secret_message);

        id = getIntent().getIntExtra("id",0);
        databaseHelper = new DatabaseHelper(this);
        dialog = new Dialog(this);
        secretImage = (ImageView) findViewById(R.id.secretImage);
        secretMessage = (EditText) findViewById(R.id.secretMessage);
        secretBtn = (Button) findViewById(R.id.secretBtnPage);

        secretImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gallery();
            }
        });


        if(id != 0)
        {
            SecretList info = databaseHelper.singleSecretMessageSelect(id);
            secretMessage.setText(info.message);
            if(info.image != null)
            {
                secretImage.setImageBitmap(ADDFAvourite.getBitmap(info.image));
            }

            ((Button)findViewById(R.id.secretBtnPage)).setText("Update");
        }



        secretBtn.setOnClickListener(new View.OnClickListener() {
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

            }
        });

        galleryChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Result_load_image);
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
           secretImage.setImageBitmap(bitmap);
        }else if(requestCode == Result_load_image && resultCode == RESULT_OK)
        {
            Uri contentURI = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
            secretImage.setImageBitmap(bitmap);

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

        String inssecretMessage = secretMessage.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("message",inssecretMessage);
        if(bitmap != null)
        {
            contentValues.put("image",getBlob(bitmap));
        }

        if(id == 0)
        {
            databaseHelper.secretMsgInsert(contentValues);
            Toast.makeText(AddSecretMessage.this, "Insert Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }else
        {
            databaseHelper.updateSecretMessage(id,contentValues);
            Toast.makeText(this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
