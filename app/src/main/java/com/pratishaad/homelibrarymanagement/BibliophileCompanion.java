package com.pratishaad.homelibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class BibliophileCompanion extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliophile_companion);
        WebView myWebView = new WebView(getApplicationContext());
        setContentView(myWebView);
        myWebView.loadUrl("https://www.google.com/");
    }
}