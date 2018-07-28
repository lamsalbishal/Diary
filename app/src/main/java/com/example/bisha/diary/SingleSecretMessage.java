package com.example.bisha.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bisha.diary.Helper.SecretList;

public class SingleSecretMessage extends AppCompatActivity {


    ImageView singlesecretImageView,updateSecretPage,deleteSecretPage;
    TextView singlesecretMessageView;
    DatabaseHelper databaseHelper;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_secret_message);

        databaseHelper = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id",0);

        updateSecretPage = (ImageView) findViewById(R.id.updateSecretPage);
        deleteSecretPage = (ImageView) findViewById(R.id.deleteSecretPage);
        singlesecretImageView = (ImageView) findViewById(R.id.singlesecretImageView);
        singlesecretMessageView = (TextView) findViewById(R.id.singlesecretMessageView);

        updateSecretPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SingleSecretMessage.this,AddSecretMessage.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        deleteSecretPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        singleSelect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        singleSelect();
    }

    public void singleSelect()
    {
        SecretList info = databaseHelper.singleSecretMessageSelect(id);
        singlesecretMessageView.setText(info.message);
        if(info.image != null)
        {
            singlesecretImageView.setImageBitmap(ADDFAvourite.getBitmap(info.image));
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

                databaseHelper.deleteSecretMessage(id);
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
