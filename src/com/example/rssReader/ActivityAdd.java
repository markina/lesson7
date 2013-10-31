package com.example.rssReader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;

public class ActivityAdd extends Activity {

    DB db;
    EditText etUrl;
    EditText etName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        db = new DB(this);


        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        View.OnClickListener oclBtnAdd = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                etUrl = (EditText) findViewById(R.id.etUrlAdd);
                etName = (EditText) findViewById(R.id.etNameAdd);

                final String newUrl = etUrl.getText().toString();
                final String newName = etName.getText().toString();

                db.addInRss(newUrl, newName);
                finish();
            }
        };
        btnAdd.setOnClickListener(oclBtnAdd);

    }


}
