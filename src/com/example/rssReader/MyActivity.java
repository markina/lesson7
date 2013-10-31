package com.example.rssReader;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Vector;

public class MyActivity extends Activity {

    String stWriteInSpinner = "";
    DB db;

    long spinnerPosition = 0;

    private void startAlarm(int millis){
        Intent intent = new Intent(this, MyIntentService.class);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if(pendingIntent == null){
            pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + millis, millis, pendingIntent);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startAlarm(1000000);
        db = new DB(this);
        final Cursor cursor = db.getFeeds();
        startManagingCursor(cursor);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.simplerow, cursor, new String[]{"name"}, new int[]{R.id.textView}, 0);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(simpleCursorAdapter);
        spinner.setPrompt("RSS");
//                        string = "http://stackoverflow.com/feeds/tag/android";
//                        string = "http://habrahabr.ru/rss/hubs/";
//                        string = "http://lenta.ru/rss";
//                        string = "http://bash.im/rss/";
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                spinnerPosition = id;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        Button btnOk = (Button) findViewById(R.id.btnOk);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ActivityTitle.class);
                intent.putExtra("idRsst", spinnerPosition);
                startActivity(intent);
            }
        };
        btnOk.setOnClickListener(oclBtnOk);

        Button btnEdit = (Button) findViewById(R.id.btnEditFeeds);
        View.OnClickListener oclBtnEdit = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ActivityEdit.class);
                startActivityForResult(intent, 0);
            }
        };
        btnEdit.setOnClickListener(oclBtnEdit);

        Button btnAdd = (Button) findViewById(R.id.btnAddFeeds);
        View.OnClickListener oclBtnAdd = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ActivityAdd.class);
                startActivityForResult(intent, 0);
            }
        };
        btnAdd.setOnClickListener(oclBtnAdd);

        Button btnDel = (Button) findViewById(R.id.btnDelMain);
        View.OnClickListener oclBtnDel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ActivityDelete.class);

                startActivityForResult(intent, 0);

            }
        };
        btnDel.setOnClickListener(oclBtnDel);

        Button btnUp = (Button) findViewById(R.id.btnUpData);
        View.OnClickListener oclBtnUp = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (findViewById(R.id.btnOk)).setEnabled(false);
                Intent intent = new Intent(getBaseContext(), MyIntentService.class);
                startService(intent);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                (findViewById(R.id.btnOk)).setEnabled(true);
                Toast.makeText(getApplicationContext(), "All downloaded", Toast.LENGTH_SHORT).show();
            }
        };
        btnUp.setOnClickListener(oclBtnUp);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Cursor c = db.getFeeds();
        startManagingCursor(c);
        SimpleCursorAdapter s = new SimpleCursorAdapter(
                this, R.layout.simplerow, c, new String[]{"name"}, new int[]{R.id.textView}, 0);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(s);

    }
}