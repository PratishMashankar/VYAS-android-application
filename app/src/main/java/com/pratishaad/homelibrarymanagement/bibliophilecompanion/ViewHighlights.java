package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import com.pratishaad.homelibrarymanagement.MainActivity;
import com.pratishaad.homelibrarymanagement.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewHighlights extends AppCompatActivity {
    FirebaseAuth fAuth;
    RecyclerView rv;
    List<Highlights> articleLists;
    DatabaseReference databaseReference;
    String projectname;

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
        setContentView(R.layout.activity_view_highlights);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back);

        fAuth=FirebaseAuth.getInstance();

        fAuth= FirebaseAuth.getInstance();
        rv = findViewById(R.id.highlights);
        rv.setHasFixedSize(true);

        Bundle extras=getIntent().getExtras();
        projectname= (String) extras.get("Project Name");


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
                HighlightAdapter adapter = new HighlightAdapter(articleLists, getApplicationContext(),
                        new HighlightAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onRecyclerViewItemClicked(int position) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleLists.get(position).getUrl()));
                        startActivity(intent);
                    }
                },
                        new HighlightAdapter.OnRecyclerViewItemLongClickListener() {
                    @Override
                    public void onRecyclerViewItemLongClicked(final int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewHighlights.this);
                        builder.setTitle("Confirm Delete")
                                .setMessage("Do you really want to delete the Highlight")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String highlightId=articleLists.get(position).getHighlightID();
                                        databaseReference.child(highlightId).removeValue();
                                        Toast.makeText(getApplicationContext(),"Highlight Deleted",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(getApplicationContext(),ViewHighlights.class);
                                        intent.putExtra("Project Name",projectname);
                                        startActivity(intent);
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
                });
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) { }
        });
    }
    @Override
    public void onBackPressed() {
        Intent  intent=new Intent(getApplicationContext(),ProjectDetails.class);
        intent.putExtra("Project Name", projectname);
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