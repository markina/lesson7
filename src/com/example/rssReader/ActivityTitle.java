package com.example.rssReader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;

public class ActivityTitle extends Activity {

    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titles);

        Bundle extras = getIntent().getExtras();
        long idRss = extras.getLong("idRsst");
        ListView lv = (ListView) findViewById(R.id.lv);
        db = new DB(this);
        Cursor cursor = db.getTitles(idRss);
        startManagingCursor(cursor);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this, R.layout.simplerow, cursor, new String[]{"title"}, new int[]{R.id.textView}, 0);

        lv.setAdapter(simpleCursorAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityTitle.this, ActivityContent.class);
                String st = db.getSummary(id);
                intent.putExtra("st", st);
                startActivity(intent);

            }
        });

    }

}
