package com.pratishaad.homelibrarymanagement.viewbooks;

import android.content.Intent;
import android.os.Bundle;

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
import com.pratishaad.homelibrarymanagement.Book;
import com.pratishaad.homelibrarymanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ViewBooks extends AppCompatActivity {

    RecyclerView rv;
    List<Book> articleLists;
    DatabaseReference databaseReference;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);
        fAuth=FirebaseAuth.getInstance();
        rv = findViewById(R.id.rec);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        articleLists = new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks");
        getImageData();
    }

    private void getImageData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot di : dataSnapshot.getChildren()) {
                    Book articleList = di.getValue(Book.class);
                    articleLists.add(articleList);
                }

                ArticleAdapter adapter = new ArticleAdapter(articleLists, getApplicationContext(), new ArticleAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onRecyclerViewItemClicked(int position) {
                        Intent intent = new Intent(getApplicationContext(), ViewBookDetails.class);

                        intent.putExtra("Image", articleLists.get(position).getImageFirebaseURI());
                        intent.putExtra("Title", articleLists.get(position).getBookTitle());
                        intent.putExtra("Author", articleLists.get(position).getBookAuthor());
                        intent.putExtra("ISBN", articleLists.get(position).getBookISBN());
                        intent.putExtra("Description", articleLists.get(position).getBookDescription());
                        intent.putExtra("Genre", articleLists.get(position).getBookGenre());
                        intent.putExtra("CurrentReadBool", articleLists.get(position).getCurrentlyReadingBool());
                        intent.putExtra("LendBool", articleLists.get(position).getLendBookBool());
                        intent.putExtra("Lendee", articleLists.get(position).getLendLendeeName());
                        intent.putExtra("GiveDate", articleLists.get(position).getLendGiveDate());
                        intent.putExtra("ReceiveDate", articleLists.get(position).getLendReceiveDate());
                        intent.putExtra("BookID",articleLists.get(position).getBookId());

                        startActivity(intent);
                    }
                });
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}