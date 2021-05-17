package com.pratishaad.homelibrarymanagement;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class BibliophileCompanion extends AppCompatActivity {
    WebView mWebView;
    Button exitBtn, highlightBtn;
    ImageButton searchBtn;
    EditText enterURL;

    String highlightURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliophile_companion);

        mWebView = (WebView) findViewById(R.id.webView);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        exitBtn = (Button) findViewById(R.id.exitBtn);
        highlightBtn = (Button) findViewById(R.id.highlightBtn);
        enterURL = (EditText) findViewById(R.id.enterURL);


        //enable javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClient webViewClient = new WebViewClient();
        mWebView.setWebViewClient(webViewClient);

        //pre load google
        mWebView.loadUrl("https://www.google.com/");

        //search button
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=enterURL.getText().toString().trim();
                if(Patterns.WEB_URL.matcher(url).matches()){
                    mWebView.loadUrl(url);
                }
                else{
                    String[] words = url.split(" ");
                    url=words[0];
                    if(words.length>1){
                        for(int i=1;i < words.length;i++){
                            url=url+"+"+words[i];
                        }
                    }
                    mWebView.loadUrl("https://www.google.com/search?q="+url);
                }
                enterURL.setText(mWebView.getUrl());
            }
        });

        //opens link in same webview
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                enterURL.setText(view.getUrl());
                return false;
            }
        });

        //exit BibCom
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent();
                finish();
            }
        });

        //Highlight Text
        highlightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "highlight begin", Toast.LENGTH_SHORT).show();
                highlightURL = mWebView.getUrl();
                mWebView.evaluateJavascript("", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });

            }
        });
    }

    //remember history and go back press
    @Override
    public void onBackPressed() {
        if (mWebView.isFocused() && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
