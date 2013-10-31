package com.example.rssReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL extends SQLiteOpenHelper{


    public SQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE rss(_id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT, name TEXT)");
        db.execSQL("CREATE TABLE content(_id INTEGER PRIMARY KEY AUTOINCREMENT, idInRss INTEGER, title TEXT, summary TEXT)");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "stackoverflow.com");
        contentValues.put("url", "http://stackoverflow.com/feeds/tag/android");
        db.insert("rss", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
