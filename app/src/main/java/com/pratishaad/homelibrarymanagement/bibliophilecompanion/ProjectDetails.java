package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pratishaad.homelibrarymanagement.R;

public class ProjectDetails extends AppCompatActivity {

    Button surfinternet,viewhighlights;

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
        setContentView(R.layout.activity_project_details);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back);

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