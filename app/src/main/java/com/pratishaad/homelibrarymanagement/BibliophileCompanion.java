package com.pratishaad.homelibrarymanagement;
import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BibliophileCompanion extends AppCompatActivity {
    WebView mWebView;
    View loadingView;
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

        final String highlightscript = " <script language=\"javascript\">" +

                "function highlightSelection(){" +
                "var userSelection = window.getSelection();" +
                "for(var i = 0; i < userSelection.rangeCount; i++)"
                + "  highlightRange(userSelection.getRangeAt(i));" +
                "}" +
                "function highlightRange(range){"+
                "span = document.createElement(\"span\");"+
                "span.appendChild(range.extractContents());"+
                "span.setAttribute(\"style\",\"display:block;background:#ffc570;\");"+
                "range.insertNode(span);}"+
                "</script> ";

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
                Toast.makeText(getApplicationContext(), highlightURL, Toast.LENGTH_SHORT).show();

                //mWebView.loadDataWithBaseURL(null,highlightscript,"text/html","utf-8",null);*/
                /*mWebView.evaluateJavascript("", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });*/
                mWebView.addJavascriptInterface(new JsObject(mWebView, loadingView), "CallToAnAndroidFunction");

            }
        });

    }
    public void onPageFinished(WebView view, String url) {

        view.loadUrl("javascript:"
                + "var FunctionOne = function () {"
                + "  var r = $.Deferred();"
                + "  try{document.getElementsByClassName('header')[0].style.display='none';}catch(e){}"
                + "  try{document.getElementById('section_0').style.display='none';}catch(e){}"
                + "  try{document.getElementById('page-actions').style.display='none';}catch(e){}"
                + "  try{document.getElementsByClassName('languageSelector')[0].style.display='none';}catch(e){}"
                + "  try{document.getElementById('mw-mf-last-modified').style.display='none';}catch(e){}"
                + "  try{document.getElementById('footer').style.display='none';}catch(e){}"
                + "  setTimeout(function () {"
                + "    r.resolve();"
                + "  }, 2500);"
                + "  return r;"
                + "};"
                + "var FunctionTwo = function () {"
                + "  window.CallToAnAndroidFunction.setVisible();"
                + "};"
                + "FunctionOne().done(FunctionTwo);");

        loadingView.setVisibility(View.INVISIBLE);
        view.setVisibility(View.VISIBLE);
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
 class JsObject {
    private View loadingView;
    private View view;
    JsObject(View view, View loadingView){this.view = view;this.loadingView = loadingView;}
    Activity activity;
    public void MyAndroidThread(Activity activity)
    {
        this.activity = activity;
    }
    @JavascriptInterface


        public void setVisible(){
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    view.setVisibility(View.VISIBLE);
                    loadingView.setVisibility(View.INVISIBLE);
                }
            });
        }
 }
