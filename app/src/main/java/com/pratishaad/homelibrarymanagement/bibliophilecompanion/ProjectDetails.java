package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pratishaad.homelibrarymanagement.R;

public class ProjectDetails extends AppCompatActivity {

    Button surfinternet,viewhighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        surfinternet=(Button) findViewById(R.id.surfinternet);
        viewhighlights=(Button) findViewById(R.id.viewhighlights);

        Bundle extras = getIntent().getExtras();
        final String projectname= (String) extras.get("Project Name");

        surfinternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BibliophileCompanion.class);
                intent.putExtra("Project Name",projectname);
                startActivity(intent);
            }
        });

        viewhighlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewHighlights.class);
                intent.putExtra("Project Name",projectname);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent  intent=new Intent(getApplicationContext(),AllProjects.class);
        startActivity(intent);
    }
}