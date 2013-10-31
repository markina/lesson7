package com.example.rssReader;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

public class ActivityContent extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String st = extras.getString("st");
        webView = new WebView(ActivityContent.this);
        setContentView(webView);
        webView.loadDataWithBaseURL(null, "<html><head/><body>" + st + "</body></html>", "text/html", "UTF-8", null);
    }

}
