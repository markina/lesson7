package com.example.rssReader;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Vector;

public class MyIntentService extends IntentService{
    public MyIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DB db = new DB(this);
        Vector<String> stringsForTitles = new Vector<String>();
        Vector<String> stringsForSummary = new Vector<String>();
        Cursor cursor = db.getFeeds();
        while(cursor.moveToNext()) {
            String string = cursor.getString(cursor.getColumnIndex("url"));
            String answer = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(string);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                answer = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            XMLParser parser = new XMLParser();
            stringsForTitles.clear();
            stringsForSummary.clear();
            try {
                parser.putAnswer(answer);
                Vector<String> t = parser.getTitles();
                stringsForTitles.addAll(t);

                t = parser.getSummary();
                stringsForSummary.addAll(t);

            } catch (Exception e) {
                e.printStackTrace();
            }
            long t = cursor.getInt(cursor.getColumnIndex("_id"));
            db.delete(t);
            db.insert(t, stringsForTitles, stringsForSummary);

        }


    }
}
