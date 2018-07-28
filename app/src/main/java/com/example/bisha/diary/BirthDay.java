package com.example.bisha.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class BirthDay extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView birthdatListView;
    BirthdayListView birthdayListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        databaseHelper = new DatabaseHelper(this);
        birthdatListView = (ListView) findViewById(R.id.bithdayListView);

        birthdayListView = new BirthdayListView(this,databaseHelper.birthdaySelect());
        birthdatListView.setAdapter(birthdayListView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        birthdayListView = new BirthdayListView(this,databaseHelper.birthdaySelect());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.secret_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_secret_message) {
            startActivity(new Intent(BirthDay.this,AddBirthday.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
