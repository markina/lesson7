package com.example.rssReader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;

public class ActivityEdit extends Activity {

    DB db;
    EditText etUrl;
    EditText etName;
    long spinnerPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);



        db = new DB(this);
        Cursor cursor = db.getFeeds();
        startManagingCursor(cursor);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.simplerow, cursor, new String[]{"name"}, new int[]{R.id.textView}, 0);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerEdit);
        spinner.setAdapter(simpleCursorAdapter);
        spinner.setPrompt("RSS");
        etUrl = (EditText) findViewById(R.id.etUrltEsit);
        etName = (EditText) findViewById(R.id.etNameEdit);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                spinnerPosition = id;
                etUrl.setText(db.getFromRssUrlById(spinnerPosition));
                etName.setText(db.getFromRssNameById(spinnerPosition));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });





        Button btnSave = (Button) findViewById(R.id.btnSave);
        View.OnClickListener oclBtnSave = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newUrl = etUrl.getText().toString();
                String newName = etName.getText().toString();
                db.deleteInRssById(spinnerPosition);
                db.addInRss(newUrl, newName);
                setResult(0);
                finish();
            }
        };
        btnSave.setOnClickListener(oclBtnSave);

    }


}
