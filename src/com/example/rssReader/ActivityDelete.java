package com.example.rssReader;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

/**
 * Date: 31.10.13
 *
 * @author Margarita Markina
 */
public class ActivityDelete extends Activity {

    String stWriteInSpinner = "";
    long spinnerPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);

        final DB db = new DB(this);
        Cursor cursor = db.getFeeds();
        startManagingCursor(cursor);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this, R.layout.simplerow, cursor, new String[]{"name"}, new int[]{R.id.textView}, 0);

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerDel);
        spinner.setAdapter(simpleCursorAdapter);
        spinner.setPrompt("RSS");

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

        Button btnDel = (Button) findViewById(R.id.btnDelDel);
        View.OnClickListener oclBtnDel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteInRssById(spinnerPosition);
                Cursor c = db.getFeeds();
                SimpleCursorAdapter s = new SimpleCursorAdapter(
                        getBaseContext(), R.layout.simplerow, c, new String[]{"name"}, new int[]{R.id.textView}, 0);

                spinner.setAdapter(s);
                setResult(0);


            }
        };
        btnDel.setOnClickListener(oclBtnDel);

    }

}
