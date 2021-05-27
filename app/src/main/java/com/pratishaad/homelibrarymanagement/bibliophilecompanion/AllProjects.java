package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pratishaad.homelibrarymanagement.MainActivity;
import com.pratishaad.homelibrarymanagement.R;

public class AllProjects extends AppCompatActivity {

    Button project1,project2,project3,project4;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_projects);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back);

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
    @Override
    public void onBackPressed() {
        Intent  intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}