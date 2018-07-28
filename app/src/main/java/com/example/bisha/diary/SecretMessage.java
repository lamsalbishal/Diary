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
import android.widget.ListView;

import com.example.bisha.diary.Adapter.FavouritePageAdapter;
import com.example.bisha.diary.Adapter.SecretMsgAdapter;

public class SecretMessage extends AppCompatActivity {

    ListView secretMessageListview;
    SecretMsgAdapter secretMsgAdapter;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        secretMessageListview = (ListView) findViewById(R.id.secretMessageListview);
        databaseHelper = new DatabaseHelper(this);

        secretMsgAdapter = new SecretMsgAdapter(this,databaseHelper.secretMessageSelect());
        secretMessageListview.setAdapter(secretMsgAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        secretMsgAdapter = new SecretMsgAdapter(this,databaseHelper.secretMessageSelect());
        secretMessageListview.setAdapter(secretMsgAdapter);
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
            startActivity(new Intent(SecretMessage.this,AddSecretMessage.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
