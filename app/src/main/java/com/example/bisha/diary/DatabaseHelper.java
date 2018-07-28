package com.example.bisha.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bisha.diary.Helper.AccountList;
import com.example.bisha.diary.Helper.BirthdayList;
import com.example.bisha.diary.Helper.DailyReportList;
import com.example.bisha.diary.Helper.FavouriteList;
import com.example.bisha.diary.Helper.FavouritePageList;
import com.example.bisha.diary.Helper.ImageViewList;
import com.example.bisha.diary.Helper.MemorialList;
import com.example.bisha.diary.Helper.SecretList;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

     static String name = "OurDb";
     static int version = 1;

     // for the favourite
    String favouritesql = "CREATE TABLE if not exists \"favourite\" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `tittle` TEXT, `image` BLOB, `description` TEXT )";

    //for the favourite Page
    String favouritePage = "CREATE TABLE if not exists `favouritepage` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `description` TEXT, `date` TEXT, `image` BLOB,`postid` INTEGER )";

    // for the account setting Page
    String account = "CREATE TABLE if not exists `account` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `email` TEXT, `profile` BLOB )";

    //for the birthday date
    String birthday = "CREATE TABLE if not exists `birthday` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `frnname` TEXT, `frnbirthday` TEXT, `frnaddress` TEXT, `frnphone` TEXT, `frnimage` BLOB )";

    //for the daily Report
    String dailyReport = "CREATE TABLE if not exists \"dailyreport\" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `description` TEXT, `image` BLOB, `date` TEXT )";

    //for the Secret Message
    String secretMessage = "CREATE TABLE if not exists `secretmsg` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `message` INTEGER, `image` BLOB )";

    //for the  memorial Thing
    String memorial = "CREATE TABLE if not exists `memorial` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `description` TEXT, `image` BLOB, `date` TEXT )";

    //for the grid image view
    String ImageView = "CREATE TABLE if not exists `imageview` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `date` TEXT, `image` BLOB )";

    public DatabaseHelper(Context context) {
        super(context, name,null, version);
        getWritableDatabase().execSQL(favouritesql);
        getWritableDatabase().execSQL(favouritePage);
        getWritableDatabase().execSQL(account);
        getWritableDatabase().execSQL(birthday);
        getWritableDatabase().execSQL(dailyReport);
        getWritableDatabase().execSQL(secretMessage);
        getWritableDatabase().execSQL(memorial);
        getWritableDatabase().execSQL(ImageView);
    }

    // for the favourite

    public void favouriteInsert(ContentValues contentValues)
    {
        getWritableDatabase().insert("favourite","",contentValues);
    }

    public ArrayList<FavouriteList> favouriteSelect()
    {
        String sql = " select * from favourite";

        ArrayList<FavouriteList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            FavouriteList info = new FavouriteList();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.tittle = cursor.getString(cursor.getColumnIndex("tittle"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));

            list.add(info);
        }
        cursor.close();
        return list;
    }

    // close for the favourite

    //open for the favoutite Page

    public void favouritePageInsert(ContentValues contentValues)
    {
        getWritableDatabase().insert("favouritepage",null,contentValues);
    }

    public  void updateFavouritePage(int id,ContentValues contentValues)
    {
        getWritableDatabase().update("favouritepage",contentValues,"id="+id,null);
    }

    public ArrayList<FavouritePageList> favouritePageSelect(int postid)
    {
        String sql = " select * from favouritepage where postid ="+postid;

        ArrayList<FavouritePageList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            FavouritePageList info = new FavouritePageList();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.postid = cursor.getString(cursor.getColumnIndex("postid"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));

            list.add(info);
        }
        cursor.close();
        return list;
    }

    public FavouritePageList singlefavouritePageSelect(int id)
    {
        String sql = " select * from favouritepage where id="+id;

        FavouritePageList info = new FavouritePageList();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {

            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.postid = cursor.getString(cursor.getColumnIndex("postid"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));


        }
        cursor.close();
        return info;
    }
    public  void  deleteFavouritePage(int id)
    {
        getWritableDatabase().delete("favouritepage","id="+id,null);

    }

    //close for the favourite page

    // open for the account Setting Page

    public void accountInsert(ContentValues contentValues)
    {
        getWritableDatabase().insert("account",null,contentValues);
    }

    public void updateProfile(int id,ContentValues contentValues)
    {
        getWritableDatabase().update("account",contentValues,"id="+id,null);
    }

    public ArrayList<AccountList> accountSelect()
    {
        String sql = " select * from account ";

        ArrayList<AccountList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            AccountList info = new AccountList();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.name = cursor.getString(cursor.getColumnIndex("name"));
            info.email = cursor.getString(cursor.getColumnIndex("email"));
            info.profile = cursor.getBlob(cursor.getColumnIndex("profile"));

            list.add(info);
        }
        cursor.close();
        return list;
    }

    public AccountList accountInfoSelect(int id)
    {
        String sql = " select * from account where id ="+ id;

        AccountList info = new AccountList();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {

            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.name = cursor.getString(cursor.getColumnIndex("name"));
            info.email = cursor.getString(cursor.getColumnIndex("email"));
            info.profile = cursor.getBlob(cursor.getColumnIndex("profile"));


        }
        cursor.close();
        return info;
    }
    //close for the account Setting Page


    // opean for the birthday date
    public void birthdayinsert(ContentValues contentValues)
    {
        getWritableDatabase().insert("birthday",null,contentValues);
    }

    public void deleteBirthDay(int id)
    {
        getWritableDatabase().delete("birthday","id="+id,null);
    }

    public void updateBithDay(int id,ContentValues contentValues)
    {
        getWritableDatabase().update("birthday",contentValues,"id="+id,null);
    }

    public ArrayList<BirthdayList> birthdaySelect()
    {
        String sql = " select * from birthday";

        ArrayList<BirthdayList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            BirthdayList info = new BirthdayList();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.frnname = cursor.getString(cursor.getColumnIndex("frnname"));
            info.frnbirthday = cursor.getString(cursor.getColumnIndex("frnbirthday"));
            info.frnaddress = cursor.getString(cursor.getColumnIndex("frnaddress"));
            info.frnphone = cursor.getString(cursor.getColumnIndex("frnphone"));
            info.frnimage = cursor.getBlob(cursor.getColumnIndex("frnimage"));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public BirthdayList singlebirthdaySelect(int id)
    {
        String sql = " select * from birthday where id="+id;

        BirthdayList info = new BirthdayList();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {

            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.frnname = cursor.getString(cursor.getColumnIndex("frnname"));
            info.frnbirthday = cursor.getString(cursor.getColumnIndex("frnbirthday"));
            info.frnaddress = cursor.getString(cursor.getColumnIndex("frnaddress"));
            info.frnphone = cursor.getString(cursor.getColumnIndex("frnphone"));
            info.frnimage = cursor.getBlob(cursor.getColumnIndex("frnimage"));

        }
        cursor.close();
        return info;
    }


    // close for the birthday date

    //open for the daily Report

    public void dailyReportInsert(ContentValues contentValues)
    {
        getWritableDatabase().insert("dailyreport",null,contentValues);
    }

    public  void DeleteDailyReport(int id)
    {
        getWritableDatabase().delete("dailyreport","id="+id,null);
    }

    public void updateDailyReport(int id,ContentValues contentValues)
    {
        getWritableDatabase().update("dailyreport",contentValues,"id="+id,null);
    }

    public ArrayList<DailyReportList> dailyReportSelect()
    {
        String sql = " select * from dailyreport";

        ArrayList<DailyReportList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            DailyReportList info = new DailyReportList();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));

            list.add(info);
        }
        cursor.close();
        return list;
    }



    public DailyReportList singledailyReportSelect(int id)
    {
        String sql = " select * from dailyreport where id="+id;

        DailyReportList info = new DailyReportList();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {

            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));


        }
        cursor.close();
        return info;
    }


    //close for the daily Report

    //for the secret message

    public void secretMsgInsert(ContentValues contentValues)
    {
        getWritableDatabase().insert("secretmsg",null,contentValues);
    }

    public void deleteSecretMessage(int id)
    {
        getWritableDatabase().delete("secretmsg","id="+id,null);

    }

    public  void updateSecretMessage(int id,ContentValues contentValues)
    {
        getWritableDatabase().update("secretmsg",contentValues,"id="+id,null);
    }

    public ArrayList<SecretList> secretMessageSelect()
    {
        String sql = " select * from secretmsg";

        ArrayList<SecretList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            SecretList info = new SecretList();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.message = cursor.getString(cursor.getColumnIndex("message"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));

            list.add(info);
        }
        cursor.close();
        return list;
    }

    public SecretList singleSecretMessageSelect(int id)
    {
        String sql = " select * from secretmsg where id="+id;

        SecretList info = new SecretList();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {

            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.message = cursor.getString(cursor.getColumnIndex("message"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));

        }
        cursor.close();
        return info;
    }
    //close for the secret message

    //open for the Memorial Thing
   public void insertMemorial(ContentValues contentValues)
   {
       getWritableDatabase().insert("memorial",null,contentValues);
   }

    public void deleteMemorial(int id)
    {
        getWritableDatabase().delete("memorial","id="+id,null);
    }

    public void updateMemorial(int id,ContentValues contentValues)
    {
        getWritableDatabase().update("memorial",contentValues,"id="+id,null);
    }

    public ArrayList<MemorialList> memorialSelect()
    {
        String sql = " select * from memorial";

        ArrayList<MemorialList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            MemorialList info = new MemorialList();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));

            list.add(info);
        }
        cursor.close();
        return list;
    }

    public MemorialList singlememorialSelect(int id)
    {
        String sql = " select * from memorial where id="+id;

        MemorialList info = new MemorialList();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {

            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));


        }
        cursor.close();
        return info;
    }

    //Close for the Memorial Thing

    //open for the imageView

    public void imageViewInsert(ContentValues contentValues)
    {
        getWritableDatabase().insert("imageview",null,contentValues);
    }

    public ArrayList<ImageViewList> imageViewSelect()
    {
        String sql = " select * from imageview";

        ArrayList<ImageViewList> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            ImageViewList info = new ImageViewList();
            info.id = cursor.getInt(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));

            list.add(info);
        }
        cursor.close();
        return list;
    }

    public ImageViewList imageinfoViewSelect(int id)
    {
        String sql = " select * from imageview where id ="+id;

        ImageViewList info = new ImageViewList();
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext())
        {

            info.id = cursor.getInt(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));


        }
        cursor.close();
        return info;
    }

    //close for the imageView

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
