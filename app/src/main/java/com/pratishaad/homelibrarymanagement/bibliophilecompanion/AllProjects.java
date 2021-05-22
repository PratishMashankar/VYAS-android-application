package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pratishaad.homelibrarymanagement.R;

public class AllProjects extends AppCompatActivity {

    Button project1,project2,project3,project4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_projects);

        project1 = (Button) findViewById(R.id.project1);
        project2 = (Button) findViewById(R.id.project2);
        project3 = (Button) findViewById(R.id.project3);
        project4 = (Button) findViewById(R.id.project4);

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
}