package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.pratishaad.homelibrarymanagement.MainActivity;
import com.pratishaad.homelibrarymanagement.R;

public class AllProjects extends AppCompatActivity {

    CardView project1,project2,project3,project4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_projects);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        project1 = (CardView) findViewById(R.id.project1);
        project2 = (CardView) findViewById(R.id.project2);
        project3 = (CardView) findViewById(R.id.project3);
        project4 = (CardView) findViewById(R.id.project4);

        project1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProjectDetails.class);
                intent.putExtra("Project Name","Project1");
                startActivity(intent);
            }
        });

        project2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProjectDetails.class);
                intent.putExtra("Project Name","Project2");
                startActivity(intent);
            }
        });

        project3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProjectDetails.class);
                intent.putExtra("Project Name","Project3");
                startActivity(intent);
            }
        });

        project4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProjectDetails.class);
                intent.putExtra("Project Name","Project4");
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent  intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onOptionsItemSelected(View view) {
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "vyas.contact.in@gmail.com"));
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}