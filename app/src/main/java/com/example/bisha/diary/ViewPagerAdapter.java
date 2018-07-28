package com.example.bisha.diary;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdapter extends FragmentPagerAdapter{

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ImageRotate imageRotate = new ImageRotate();
                return  imageRotate;

            case 1:
                PopularNews popularNews = new PopularNews();
                return popularNews;

            case 2:
                Favourite favourite = new Favourite();
                return  favourite;

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Image";

            case 1:
                return "Report";

            case 2:
                return "Favourite";

                default:
                    return null;
        }
    }
}
