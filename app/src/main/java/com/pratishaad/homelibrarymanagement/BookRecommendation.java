package com.pratishaad.homelibrarymanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookRecommendation extends AppCompatActivity {
    WebView mWebView;
    Button exitBtn, highlightBtn;
    ImageButton searchBtn;
    EditText enterURL;
    String genre;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;
    List<Book> articleLists;

    String highlightURL;

    Map<String,Integer> genres = new HashMap<String, Integer>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable Object o) {
            return false;
        }

        @Nullable
        @Override
        public Integer get(@Nullable Object o) {
            return null;
        }

        @Nullable
        @Override
        public Integer put(String s, Integer integer) {
            return null;
        }

        @Nullable
        @Override
        public Integer remove(@Nullable Object o) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends Integer> map) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<Integer> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, Integer>> entrySet() {
            return null;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliophile_companion);

        mWebView = (WebView) findViewById(R.id.webView);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        exitBtn = (Button) findViewById(R.id.exitBtn);
        highlightBtn = (Button) findViewById(R.id.highlightBtn);
        enterURL = (EditText) findViewById(R.id.enterURL);




        databaseReference= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks");
//        genre= getBookGenres(); //genre which has the maximum book count




        //enable javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClient webViewClient = new WebViewClient();
        mWebView.setWebViewClient(webViewClient);

        //pre load google
        mWebView.loadUrl("https://www.amazon.in/s?k="+"genre.trim().replace(' '',"+")"+"&ref=nb_sb_noss_2");




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

    }

    private String getBookGenres()
    {
        //code to get all the book genres and their count
        final int[] count = {0};
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


        //code to access the database to get the values of each genre
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot di : dataSnapshot.getChildren()) {
                    Book articleList = di.getValue(Book.class);
                    String check= articleList.getBookGenre();
                    if(genres.containsKey(check))
                    {
                        count[0] =genres.get(check);
                        genres.put(check, count[0] +1);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Map.Entry<String,Integer> entryWithMaxValue = null;

        for(Map.Entry x: genres.entrySet())
        {
            if (
                // If this is the first entry, set result as this
                    entryWithMaxValue == null

                            // If this entry's value is more than the max value
                            // Set this entry as the max
                            || x.getValue().toString()
                            .compareTo(String.valueOf(entryWithMaxValue.getValue()))
                            > 0) {

                entryWithMaxValue = x;
            }
        }

        return entryWithMaxValue.getKey();
    }


            //remember history and go back press
    @Override
    public void onBackPressed() {
        if (mWebView.isFocused() && mWebView.canGoBack()) {
            mWebView.goBack();
            //enterURL.setText(mWebView.getUrl());

        }
    }
}