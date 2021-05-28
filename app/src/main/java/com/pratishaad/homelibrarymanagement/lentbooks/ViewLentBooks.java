package com.pratishaad.homelibrarymanagement.lentbooks;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.pratishaad.homelibrarymanagement.Book;
import com.pratishaad.homelibrarymanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ViewLentBooks extends AppCompatActivity {



    RecyclerView rv;
    List<Book> bookLentLists;
    DatabaseReference databaseReference;


    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lent_books);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();


        fAuth=FirebaseAuth.getInstance();
        rv = findViewById(R.id.recLent);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        bookLentLists = new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks");
        getImageData();
    }

    private void getImageData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di : dataSnapshot.getChildren()) {
                    Book bookLentList = di.getValue(Book.class);
                    //if(!bookLentList.getLendBookBool().equals("No"))
                    bookLentLists.add(bookLentList);
                }
                LendBookAdapter adapter = new LendBookAdapter(bookLentLists, getApplicationContext());
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}