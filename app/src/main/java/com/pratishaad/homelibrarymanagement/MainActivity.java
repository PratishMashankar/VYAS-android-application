package com.pratishaad.homelibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.pratishaad.homelibrarymanagement.addbooks.AddBook;
import com.pratishaad.homelibrarymanagement.authentication.Login;
import com.pratishaad.homelibrarymanagement.bibliophilecompanion.AllProjects;
import com.pratishaad.homelibrarymanagement.bookrecommendation.BookRecommendation;
import com.pratishaad.homelibrarymanagement.lentbooks.ViewLentBooks;
import com.pratishaad.homelibrarymanagement.viewbooks.ViewBooks;

public class MainActivity extends AppCompatActivity {

    Button viewbooks;
    Button addbook;
    Button lendbooks;
    Button viewlentbooks;
    Button logout;
    Button allprojects;
    Button recommendation;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewbooks = (Button) findViewById(R.id.viewbooks);
        addbook = (Button) findViewById(R.id.addbook);
        lendbooks = (Button) findViewById(R.id.lendbooks);
        viewlentbooks = (Button) findViewById(R.id.viewlentbooks);
        logout = (Button) findViewById(R.id.logoutbtn);
        allprojects = (Button) findViewById(R.id.allprojects) ;
        recommendation = (Button) findViewById(R.id.bookrecommendation) ;
        fAuth = FirebaseAuth.getInstance();

        viewbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewBooks.class));
            }
        });

        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddBook.class);
                startActivity(i);
            }
        });

        lendbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewBooks.class));
            }
        });

        viewlentbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewLentBooks.class));
            }
        });

        allprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AllProjects.class));
            }
        });

        recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BookRecommendation.class));
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}