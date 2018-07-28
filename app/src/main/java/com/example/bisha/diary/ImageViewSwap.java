package com.example.bisha.diary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bisha.diary.Adapter.DetailImageViewFragment;
import com.example.bisha.diary.Helper.ImageViewList;

import java.util.ArrayList;

public class ImageViewSwap extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ViewPager pager;
    int[] id = {0,1,2,3,4};
    int[] idd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_swap);

        //id = getIntent().getIntExtra("id",0);
        pager = (ViewPager) findViewById(R.id.imageViewPager);
        databaseHelper = new DatabaseHelper(this);

        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        select();
    }

    public void select()
    {
        ArrayList<ImageViewList> list  = databaseHelper.imageViewSelect();

//        for (int i = 0; i < id.length; i++) {
//                idd = new int[]{ id[i]};
//            }

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DetailImageViewFragment detailImageViewFragment = new DetailImageViewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",id[position]);
            detailImageViewFragment.setArguments(bundle);

            return detailImageViewFragment;
        }

        @Override
        public int getCount() {
            return id.length;
        }
    }
}
