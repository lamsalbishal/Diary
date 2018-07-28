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

public class FavouritePage extends AppCompatActivity {

    ListView favouritepageView;
    FavouritePageAdapter favouritePageAdapter;
    DatabaseHelper databaseHelper;
    int postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        favouritepageView = (ListView) findViewById(R.id.favouritePageView);
        postid = getIntent().getIntExtra("postid",0);

        favouritePageAdapter = new FavouritePageAdapter(this,databaseHelper.favouritePageSelect(postid));
        favouritepageView.setAdapter(favouritePageAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        favouritePageAdapter = new FavouritePageAdapter(this,databaseHelper.favouritePageSelect(postid));
        favouritepageView.setAdapter(favouritePageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favourite_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_favourite_page) {
            Intent i = new Intent(FavouritePage.this,add_favourite_page.class);
            i.putExtra("postid",postid);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
