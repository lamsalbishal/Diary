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

import com.example.bisha.diary.Helper.DailyReportList;

public class SingleViewDailyReport extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ImageView singleReportUpdate,singleReportDelete,singleReportimageView;
    TextView singleReportTitleView,singleReportDateView,singleReportDesView;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view_daily_report);

        databaseHelper = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id",0);

        singleReportDateView = (TextView) findViewById(R.id.singleReportDateView);
        singleReportDesView = (TextView) findViewById(R.id.singleReportDesView);
        singleReportTitleView = (TextView) findViewById(R.id.singleReportTitleView);
        singleReportimageView = (ImageView) findViewById(R.id.singleReportimageView);
        singleReportDelete = (ImageView) findViewById(R.id.deleteReportPage);
        singleReportUpdate = (ImageView) findViewById(R.id.updateReportPage);

        singleReportDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        singleReportUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SingleViewDailyReport.this,AddDailyReport.class);
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

    public  void select()
    {
        DailyReportList info = databaseHelper.singledailyReportSelect(id);
        singleReportTitleView.setText(info.title);
        singleReportDateView.setText(info.date);
        singleReportDesView.setText(info.description);
        if(info.image != null) {
            singleReportimageView.setImageBitmap(AddDailyReport.getBitmap(info.image));
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

                databaseHelper.DeleteDailyReport(id);
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
