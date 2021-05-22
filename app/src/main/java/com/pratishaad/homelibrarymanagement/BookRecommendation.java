package com.pratishaad.homelibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class BookRecommendation extends AppCompatActivity {
    WebView mWebView;
    String genre;
    Button exitBtn;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;

    Map<String,Integer> genres = new LinkedHashMap<String, Integer>();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_recommendation);

        mWebView = (WebView) findViewById(R.id.webView);
        exitBtn = (Button) findViewById(R.id.exitBtn);
        fAuth=FirebaseAuth.getInstance();

        genres.put("Adventure",0);
        genres.put("Biography",0);
        genres.put("Crime and Mystery",0);
        genres.put("Fantasy",0);
        genres.put("Horror",0);
        genres.put("Non Fiction",0);
        genres.put("Regional",0);
        genres.put("Religion",0);
        genres.put("Religion Story",0);
        genres.put("Romance",0);
        genres.put("Sci-fi",0);
        genres.put("Spirituality",0);

        databaseReference= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks");

        //opens link in same webview
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });

        //exit BibCom
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getBookGenreRecommendation();

    }

    private void getBookGenreRecommendation()
    {
        //code to access the database to get the values of each genre
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di : dataSnapshot.getChildren()) {
                    Book articleList = di.getValue(Book.class);
                    String check= articleList.getBookGenre();
                    int count=genres.get(check);
                    genres.put(check, count +1);
                }

                Map.Entry<String, Integer> maxEntry = null;
                for (Map.Entry<String, Integer> entry : genres.entrySet()) {
                    if (maxEntry == null || entry.getValue()>maxEntry.getValue()) {
                        maxEntry = entry;
                    }
                }

                genre=maxEntry.getKey();
                mWebView.loadUrl("https://www.amazon.in/s?k="+genre.trim().replace(" ","+")+"+books"+"&ref=nb_sb_noss_1");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    //remember history and go back press
    @Override
    public void onBackPressed() {
        if (mWebView.isFocused() && mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }
}