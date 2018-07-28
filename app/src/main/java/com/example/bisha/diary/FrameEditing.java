package com.example.bisha.diary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FrameEditing extends AppCompatActivity {

    final  static int Permission = 1;

    final static int Result_load_image = 0;
    ImageView flowerfilter,roseFilter,frameFilter;
    Button b_save,b_share,b_load;
    ImageView imageSet,imageFilter;
    String CurrentImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_editing);

        if(ContextCompat.checkSelfPermission(FrameEditing.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(FrameEditing.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(FrameEditing.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Permission);
            }else
            {
                ActivityCompat.requestPermissions(FrameEditing.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Permission);
            }

        }else
        {
            //do nothing
        }

        imageFilter = (ImageView) findViewById(R.id.editingImageFilter);
        imageSet = (ImageView) findViewById(R.id.editingImageView);

        b_load = (Button) findViewById(R.id.b_load);
        b_share = (Button) findViewById(R.id.b_share);
        b_save = (Button) findViewById(R.id.b_save);
        flowerfilter = (ImageView) findViewById(R.id.flowerFilter);
        frameFilter = (ImageView) findViewById(R.id.frameFilter);
        roseFilter = (ImageView) findViewById(R.id.roseFilter);


        b_save.setEnabled(false);
        b_share.setEnabled(false);
        flowerfilter.setEnabled(false);
        frameFilter.setEnabled(false);
        roseFilter.setEnabled(false);

        b_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Result_load_image);
            }
        });

        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View content = findViewById(R.id.lay);
                Bitmap bitmap = getScreenShoot(content);
                CurrentImage = "image" + System.currentTimeMillis() + ".png";
                store(bitmap,CurrentImage);
                b_share.setEnabled(true);
            }
        });

        flowerfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageFilter.setImageResource(R.drawable.flower);
                b_save.setEnabled(true);
            }
        });

        roseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageFilter.setImageResource(R.drawable.rose);
                b_save.setEnabled(true);
            }
        });

        frameFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageFilter.setImageResource(R.drawable.frame);
                b_save.setEnabled(true);
            }
        });

        b_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(CurrentImage);
            }
        });
    }


    private static Bitmap getScreenShoot(View view)
    {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private void store(Bitmap bm, String fileName)
    {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FilterImage";
        File dir = new File(dirPath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        File file = new File(dirPath,fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareImage(String filename)
    {
        String dirpath = Environment.getDownloadCacheDirectory().getAbsolutePath() + "/FilterImage";
        Uri uri = Uri.fromFile(new File(dirpath,filename));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_SUBJECT,"");
        intent.putExtra(Intent.EXTRA_TEXT,"");
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        try {
            startActivity(Intent.createChooser(intent,"Share File"));
        }catch (Exception e)
        {
            Toast.makeText(this, "No Sharing App", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Result_load_image && resultCode == RESULT_OK) {
            Uri selectImage = data.getData();
            String[] filePathColoumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectImage,filePathColoumn,null,null,null);
            cursor.moveToFirst();
            int coloumIndex = cursor.getColumnIndex(filePathColoumn[0]);
            String PicturePath = cursor.getString(coloumIndex);
            cursor.close();
            imageSet.setImageBitmap(BitmapFactory.decodeFile(PicturePath));
            flowerfilter.setEnabled(true);
            roseFilter.setEnabled(true);
            frameFilter.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case Permission:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(FrameEditing.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED){

                    }

                } else {
                    Toast.makeText(this, "no Permission", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

        }
    }
}
