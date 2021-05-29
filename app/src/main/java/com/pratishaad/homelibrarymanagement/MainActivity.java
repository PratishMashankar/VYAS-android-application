package com.pratishaad.homelibrarymanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.pratishaad.homelibrarymanagement.addbooks.AddBook;
import com.pratishaad.homelibrarymanagement.authentication.Login;
import com.pratishaad.homelibrarymanagement.bibliophilecompanion.AllProjects;
import com.pratishaad.homelibrarymanagement.bookrecommendation.BookRecommendation;
import com.pratishaad.homelibrarymanagement.lentbooks.ViewLentBooks;
import com.pratishaad.homelibrarymanagement.viewbooks.ViewBooks;

public class MainActivity extends AppCompatActivity {

    CardView viewbooks,addbook,lendbooks,viewlentbooks,allprojects,recommendation;
    Button logout;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        action bar settings
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
//        View view =getSupportActionBar().getCustomView();


        viewbooks = (CardView) findViewById(R.id.viewbooks);
        addbook = (CardView) findViewById(R.id.addbook);
        lendbooks = (CardView) findViewById(R.id.lendbooks);
        viewlentbooks = (CardView) findViewById(R.id.viewlentbooks);
        logout = (Button) findViewById(R.id.logoutbtn);
        allprojects = (CardView) findViewById(R.id.allprojects) ;
        recommendation = (CardView) findViewById(R.id.bookrecommendation) ;
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
        new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    public void onOptionsItemSelected(View view) {
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "vyas.contact.in@gmail.com"));
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(MainActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}