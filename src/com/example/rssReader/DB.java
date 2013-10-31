package com.example.rssReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class DB {
    SQLiteDatabase sqLiteDatabase;

    public DB(Context context) {
        SQL helper = new SQL(context, "myDataBase",null, 1);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public Cursor getFeeds() {
        return sqLiteDatabase.query("rss", null, null, null, null, null, "_id");
    }

    public void delete(long x) {
        sqLiteDatabase.delete("content", "idInRss="+(new Long(x)).toString(), null);
    }

    public void deleteInRssById(long id) {
        sqLiteDatabase.delete("rss", "_id="+(new Long(id)).toString(), null);
    }

    public void insert(Long idInRss, Vector<String> stringsForTitles, Vector<String> stringsForSummary) {
        for(int i = 0; i < stringsForTitles.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("idInRss", idInRss);
            contentValues.put("title", stringsForTitles.elementAt(i));
            contentValues.put("summary", stringsForSummary.elementAt(i));

            sqLiteDatabase.insert("content", null, contentValues);
        }

    }

    public void putNewFeed(String name, String url) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("url", url);

        sqLiteDatabase.insert("rss", null, contentValues);
    }

    public Cursor getTitles(Long idRss) {
        return sqLiteDatabase.query(
                "content", new String[]{"_id", "title"}, "idInRss="+idRss.toString(), null, null, null, null);
    }

    public void addInRss(String newUrl, String newName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        contentValues.put("url", newUrl);

        sqLiteDatabase.insert("rss", null, contentValues);
    }

    public String getFromRssUrlById(long spinnerPosition) {
        Cursor c = sqLiteDatabase.query(
                "rss", new String[]{"url"}, "_id="+(new Long(spinnerPosition).toString()), null, null, null, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("url"));
    }

    public String getFromRssNameById(long spinnerPosition) {
        Cursor c = sqLiteDatabase.query("rss", new String[]{"name"}, "_id="+(new Long(spinnerPosition).toString()), null, null, null, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("name"));
    }

    public String getSummary(long id) {
        Cursor c = sqLiteDatabase.query(
                "content", new String[]{"summary"}, "_id="+(new Long(id).toString()), null, null, null, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("summary"));
    }
}
