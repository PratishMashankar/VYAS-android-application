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

        /*final String highlightscript = " <script language=\"javascript\">" +

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
                "</script> ";*/
       /* final String highlightscript = " <script language=\"javascript\">" +"function highlightSelection() {\n" +
                "    var userSelection = window.getSelection();\n" +
                "    for(var i = 0; i < userSelection.rangeCount; i++) {\n" +
                "        highlightRange(userSelection.getRangeAt(i));\n" +
                "    }\n" +
                "\n" +
                "}\n" +
                "\n" +
                "function highlightRange(range) {\n" +
                "    var newNode = document.createElement(\"span\");\n" +
                "    newNode.setAttribute(\n" +
                "       \"style\",\n" +
                "       \"background-color: yellow; display: inline;\"\n" +
                "    );\n" +
                "    range.surroundContents(newNode);\n" +
                "}"+
                "</script> ";*/

        final String highlightscript ="<!DOCTYPE html>\n" +
                "    <html>\n" +
                "        <head>\n" +
                "            <style type=\"text/css\">\n" +
                "                .highlight\n" +
                "                {\n" +
                "                    background-color: yellow;\n" +
                "                }\n" +
                "                #test-text::-moz-selection { /* Code for Firefox */\n" +
                "\n" +
                "                    background: yellow;\n" +
                "                }\n" +
                "\n" +
                "                #test-text::selection {\n" +
                "\n" +
                "                    background: yellow;\n" +
                "                }\n" +
                "\n" +
                "            </style>\n" +
                "        </head>\n" +
                "\n" +
                "        <body>\n" +
                "            <div id=\"div1\" style=\"border: 1px solid #000;\">\n" +
                "                <div id=\"test-text\">\n" +
                "                    <h1> Hello How are you </h1>\n" +
                "                    <p >\n" +
                "                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <br />\n" +
                "\n" +
                "        </body>\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
                "            <script type=\"text/javascript\">\n" +
                "                mouseXPosition = 0;\n" +
                "                $(document).ready(function () {\n" +
                "\n" +
                "                    $(\"#test-text\").mousedown(function (e1) {\n" +
                "                        mouseXPosition = e1.pageX;//register the mouse down position\n" +
                "                    });\n" +
                "\n" +
                "                    $(\"#test-text\").mouseup(function (e2) {\n" +
                "                        var highlighted = false;\n" +
                "                        var selection = window.getSelection();\n" +
                "                        var selectedText = selection.toString();\n" +
                "                        var startPoint = window.getSelection().getRangeAt(0).startOffset;\n" +
                "                        var endPoint = window.getSelection().getRangeAt(0).endOffset;\n" +
                "                        var anchorTag = selection.anchorNode.parentNode;\n" +
                "                        var focusTag = selection.focusNode.parentNode;\n" +
                "                        if ((e2.pageX - mouseXPosition) < 0) {\n" +
                "                            focusTag = selection.anchorNode.parentNode;\n" +
                "                            anchorTag = selection.focusNode.parentNode;\n" +
                "                        }\n" +
                "                        if (selectedText.length === (endPoint - startPoint)) {\n" +
                "                            highlighted = true;\n" +
                "\n" +
                "                            if (anchorTag.className !== \"highlight\") {\n" +
                "                                highlightSelection();\n" +
                "                            } else {\n" +
                "                                var afterText = selectedText + \"<span class = 'highlight'>\" + anchorTag.innerHTML.substr(endPoint) + \"</span>\";\n" +
                "                                anchorTag.innerHTML = anchorTag.innerHTML.substr(0, startPoint);\n" +
                "                                anchorTag.insertAdjacentHTML('afterend', afterText);\n" +
                "                            }\n" +
                "\n" +
                "                        }else{\n" +
                "                            if(anchorTag.className !== \"highlight\" && focusTag.className !== \"highlight\"){\n" +
                "                                highlightSelection();  \n" +
                "                                highlighted = true;\n" +
                "                            }\n" +
                "\n" +
                "                        }\n" +
                "\n" +
                "\n" +
                "                        if (anchorTag.className === \"highlight\" && focusTag.className === 'highlight' && !highlighted) {\n" +
                "                            highlighted = true;\n" +
                "\n" +
                "                            var afterHtml = anchorTag.innerHTML.substr(startPoint);\n" +
                "                            var outerHtml = selectedText.substr(afterHtml.length, selectedText.length - endPoint - afterHtml.length);\n" +
                "                            var anchorInnerhtml = anchorTag.innerHTML.substr(0, startPoint);\n" +
                "                            var focusInnerHtml = focusTag.innerHTML.substr(endPoint);\n" +
                "                            var focusBeforeHtml = focusTag.innerHTML.substr(0, endPoint);\n" +
                "                            selection.deleteFromDocument();\n" +
                "                            anchorTag.innerHTML = anchorInnerhtml;\n" +
                "                            focusTag.innerHTml = focusInnerHtml;\n" +
                "                            var anchorafterHtml = afterHtml + outerHtml + focusBeforeHtml;\n" +
                "                            anchorTag.insertAdjacentHTML('afterend', anchorafterHtml);\n" +
                "\n" +
                "\n" +
                "                        }\n" +
                "\n" +
                "                        if (anchorTag.className === \"highlight\" && !highlighted) {\n" +
                "                            highlighted = true;\n" +
                "                            var Innerhtml = anchorTag.innerHTML.substr(0, startPoint);\n" +
                "                            var afterHtml = anchorTag.innerHTML.substr(startPoint);\n" +
                "                            var outerHtml = selectedText.substr(afterHtml.length, selectedText.length);\n" +
                "                            selection.deleteFromDocument();\n" +
                "                            anchorTag.innerHTML = Innerhtml;\n" +
                "                            anchorTag.insertAdjacentHTML('afterend', afterHtml + outerHtml);\n" +
                "                         }\n" +
                "\n" +
                "                        if (focusTag.className === 'highlight' && !highlighted) {\n" +
                "                            highlighted = true;\n" +
                "                            var beforeHtml = focusTag.innerHTML.substr(0, endPoint);\n" +
                "                            var outerHtml = selectedText.substr(0, selectedText.length - beforeHtml.length);\n" +
                "                            selection.deleteFromDocument();\n" +
                "                            focusTag.innerHTml = focusTag.innerHTML.substr(endPoint);\n" +
                "                            outerHtml += beforeHtml;\n" +
                "                            focusTag.insertAdjacentHTML('beforebegin', outerHtml );\n" +
                "\n" +
                "\n" +
                "                        }\n" +
                "                        if (!highlighted) {\n" +
                "                            highlightSelection();\n" +
                "                        }\n" +
                "                        $('.highlight').each(function(){\n" +
                "                            if($(this).html() == ''){\n" +
                "                                $(this).remove();\n" +
                "                            }\n" +
                "                        });\n" +
                "                        selection.removeAllRanges();\n" +
                "                    });\n" +
                "                });\n" +
                "\n" +
                "                function highlightSelection() {\n" +
                "                    var selection;\n" +
                "\n" +
                "                    //Get the selected stuff\n" +
                "                    if (window.getSelection)\n" +
                "                        selection = window.getSelection();\n" +
                "                    else if (typeof document.selection != \"undefined\")\n" +
                "                        selection = document.selection;\n" +
                "\n" +
                "                    //Get a the selected content, in a range object\n" +
                "                    var range = selection.getRangeAt(0);\n" +
                "\n" +
                "                    //If the range spans some text, and inside a tag, set its css class.\n" +
                "                    if (range && !selection.isCollapsed) {\n" +
                "                        if (selection.anchorNode.parentNode == selection.focusNode.parentNode) {\n" +
                "                            var span = document.createElement('span');\n" +
                "                            span.className = 'highlight';\n" +
                "                            span.textContent = selection.toString();\n" +
                "                            selection.deleteFromDocument();\n" +
                "                            range.insertNode(span);\n" +
                "    //                        range.surroundContents(span);\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "\n" +
                "            </script>\n" +
                "    </html>";


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
                Toast.makeText(getApplicationContext(), highlightURL, Toast.LENGTH_SHORT).show();

                mWebView.loadDataWithBaseURL(null,highlightscript,"text/html","utf-8",null);
                /*mWebView.evaluateJavascript("", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });*/

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
