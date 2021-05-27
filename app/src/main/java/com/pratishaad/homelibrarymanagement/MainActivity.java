package com.pratishaad.homelibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pratishaad.homelibrarymanagement.addbooks.AddBook;
import com.pratishaad.homelibrarymanagement.authentication.Login;
import com.pratishaad.homelibrarymanagement.bibliophilecompanion.AllProjects;
import com.pratishaad.homelibrarymanagement.bookrecommendation.BookRecommendation;
import com.pratishaad.homelibrarymanagement.lentbooks.ViewLentBooks;
import com.pratishaad.homelibrarymanagement.viewbooks.ViewBookDetails;
import com.pratishaad.homelibrarymanagement.viewbooks.ViewBooks;

import org.jetbrains.annotations.NotNull;

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
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirm Exit")
                .setMessage("Do you really want to exit the application")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finishAffinity();
                        System.exit(0);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}