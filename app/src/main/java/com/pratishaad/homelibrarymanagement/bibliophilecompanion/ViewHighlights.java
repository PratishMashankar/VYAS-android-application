package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pratishaad.homelibrarymanagement.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewHighlights extends AppCompatActivity {
    FirebaseAuth fAuth;
    RecyclerView rv;
    List<Highlights> articleLists;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_highlights);

        fAuth=FirebaseAuth.getInstance();

        fAuth= FirebaseAuth.getInstance();
        rv = findViewById(R.id.highlights);
        rv.setHasFixedSize(true);

        Bundle extras=getIntent().getExtras();
        final String projectname= (String) extras.get("Project Name");

        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        articleLists = new ArrayList<>(); //list of highlights i.e article list is of the type List<Highlights>
        databaseReference= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("Projects").child(projectname);;
        getDetails();
    }

    public void getDetails()
    {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot di : snapshot.getChildren()) {
                    Highlights articleList = di.getValue(Highlights.class);
                    articleLists.add(articleList);
                }
                HighlightAdapter adapter = new HighlightAdapter(articleLists, getApplicationContext());

                rv.setAdapter(adapter);
            }
            

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}