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

import com.example.bisha.diary.Adapter.MemorialAdapter;
import com.example.bisha.diary.Adapter.SecretMsgAdapter;

public class Memorial extends AppCompatActivity {

    ListView memorialListView;
    DatabaseHelper databaseHelper;
    MemorialAdapter memorialAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        memorialListView = (ListView) findViewById(R.id.memorialListView);
        databaseHelper = new DatabaseHelper(this);

        memorialAdapter = new MemorialAdapter(this,databaseHelper.memorialSelect());
        memorialListView.setAdapter(memorialAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        memorialAdapter = new MemorialAdapter(this,databaseHelper.memorialSelect());
        memorialListView.setAdapter(memorialAdapter);
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
            startActivity(new Intent(Memorial.this,AddMemorial.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
