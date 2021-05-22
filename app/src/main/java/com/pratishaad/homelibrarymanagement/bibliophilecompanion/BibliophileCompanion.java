package com.pratishaad.homelibrarymanagement.bibliophilecompanion;
import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pratishaad.homelibrarymanagement.R;

public class BibliophileCompanion extends AppCompatActivity {
    WebView mWebView;
    View loadingView;
    Button exitBtn, highlightBtn;
    ImageButton searchBtn;
    EditText enterURL;

    String highlightURL;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

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

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getUid());

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

        Bundle extras = getIntent().getExtras();
        final String projectname= (String) extras.get("Project Name");
        //Highlight Text
        highlightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "highlight begin", Toast.LENGTH_SHORT).show();
                highlightURL = mWebView.getUrl();
                //Toast.makeText(getApplicationContext(), highlightURL, Toast.LENGTH_SHORT).show();
                mWebView.evaluateJavascript("(function(){return window.getSelection().toString()})()",
                        new ValueCallback<String>()
                        {
                            @Override
                            public void onReceiveValue(String value)
                            {
                                try {
                                    Highlights highlights = new Highlights(highlightURL, value);

                                    String highlightID=databaseReference.child("Projects").child(projectname).push().getKey();

                                    databaseReference.child("Projects").child(projectname).child(highlightID).setValue(highlights);

                                    Toast.makeText(getApplicationContext(), "Highlight added", Toast.LENGTH_SHORT).show();

                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }
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
            //enterURL.setText(mWebView.getUrl());

        } else {
            super.onBackPressed();
        }
    }
}