package com.example.bisha.diary;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bisha.diary.Adapter.DatePickerFragment;
import com.example.bisha.diary.Helper.DailyReportList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class AddDailyReport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    EditText dailyReportTitle,dailyReportDes,dailyReportDate;
    ImageView dailyReportImage;
    Button dailyReportBtn;

    Dialog dialog;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;

    ImageView cancleBtn,cameraChoose,galleryChoose;
    Button cancle;

    DatabaseHelper databaseHelper;

    int PICK_PHOTO_FOR_AVATAR = 100;
    final static int Result_load_image = 0;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_report);

        databaseHelper = new DatabaseHelper(this);
        dialog = new Dialog(this);

        id = getIntent().getIntExtra("id",0);
        dailyReportBtn= (Button) findViewById(R.id.dailyReportBtn);
        dailyReportDes= (EditText) findViewById(R.id.dailyReportDes);
        dailyReportTitle = (EditText) findViewById(R.id.dailyReportTitle);
        dailyReportImage = (ImageView) findViewById(R.id.dailyReportImage);
        dailyReportDate = (EditText) findViewById(R.id.dailyReportDate);


        dailyReportDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"date Picker");
            }
        });


        if(id != 0) {
            DailyReportList info = databaseHelper.singledailyReportSelect(id);
            dailyReportTitle.setText(info.title);
            dailyReportDes.setText(info.description);
            if (info.image != null) {
                dailyReportImage.setImageBitmap(AddDailyReport.getBitmap(info.image));
            }
            dailyReportDate.setText(info.date);
            ((Button) findViewById(R.id.dailyReportBtn)).setText("Update");
        }


        relativeLayout = (RelativeLayout) findViewById(R.id.relative);

        dailyReportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
            }
        });

        dailyReportBtn.setOnClickListener(new View.OnClickListener() {
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
            dailyReportImage.setImageBitmap(bitmap);
        }else if(requestCode == Result_load_image && resultCode == RESULT_OK)
        {
            Uri contentURI = data.getData();
            try {
              bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dailyReportImage.setImageBitmap(bitmap);



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

    // for the date



    public void insert()
    {
        String insDailyReportTitle = dailyReportTitle.getText().toString();
        String insDailyReportDes = dailyReportDes.getText().toString();
        String insDailyReportDate = dailyReportDate.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", insDailyReportTitle);
        contentValues.put("description",insDailyReportDes);
        if(bitmap != null)
        {
            contentValues.put("image",getBlob(bitmap));
        }
        contentValues.put("date",insDailyReportDate);
        if(id == 0)
        {
            databaseHelper.dailyReportInsert(contentValues);
            Toast.makeText(AddDailyReport.this, "Insert Successfuly", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(AddDailyReport.this,PopularNews.class));
            finish();

        }else
        {
            databaseHelper.updateDailyReport(id,contentValues);
            Toast.makeText(this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        dailyReportDate.setText(currentDate);
    }
}
