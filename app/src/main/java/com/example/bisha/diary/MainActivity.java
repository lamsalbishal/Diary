package com.example.bisha.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bisha.diary.Helper.AccountList;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    DatabaseHelper databaseHelper;
    NavigationView navigationView;

    SharedPreferences preferences;

    LinearLayout headerContainer;
    TextView userproname,userGmail;
    CircleImageView userImageProfile;

    String  id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        preferences = getSharedPreferences("UserDetail",0);


        id = preferences.getString("id","");

        viewPager = (ViewPager) findViewById(R.id.viewcontainer);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.main_tab);
        tabLayout.setupWithViewPager(viewPager);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        headerContainer = header.findViewById(R.id.headerContainer);
        userproname = header.findViewById(R.id.userProName);
        userGmail = header.findViewById(R.id.userGmail);
        userImageProfile = header.findViewById(R.id.userProfileImage);

//        headerContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.diary1));

            final AccountList info = databaseHelper.accountInfoSelect(Integer.parseInt(id));
            userproname.setText(info.name);
            userGmail.setText(info.email);
            if(info.profile != null)
            {
                userImageProfile.setImageBitmap(Account_setting.getBitmap(info.profile));
            }

            userImageProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this,Account_setting.class);
                    if(info.id != null)
                    {
                        i.putExtra("id",Integer.parseInt(info.id));
                    }

                    startActivity(i);
                }
            });
        }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_birthday) {

            Intent i = new Intent(MainActivity.this,BirthDay.class);
            startActivity(i);
        } else if (id == R.id.nav_editing_Image) {
            startActivity(new Intent(MainActivity.this,FrameEditing.class));

        }  else if (id == R.id.nav_memorial_thing) {

            startActivity(new Intent(MainActivity.this,Memorial.class));

        } else if (id == R.id.nav_message) {

            startActivity(new Intent(MainActivity.this,SecretPassword.class));

        } else if (id == R.id.account_setting) {

            Intent i = new Intent(MainActivity.this,SecretRegister.class);
            startActivity(i);
        }
        else if (id == R.id.image_add) {

            Intent i = new Intent(MainActivity.this,AddImageView.class);
            startActivity(i);
        }
        else if (id == R.id.favourite_add) {

            Intent i = new Intent(MainActivity.this,ADDFAvourite.class);
            startActivity(i);
        }
        else if (id == R.id.daily_report) {

            Intent i = new Intent(MainActivity.this,AddDailyReport.class);
            startActivity(i);
        }
        else if (id == R.id.account_set_pattern) {

            Intent i = new Intent(MainActivity.this,SetPattern.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
