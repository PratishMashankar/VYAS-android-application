package com.pratishaad.homelibrarymanagement.viewbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.pratishaad.homelibrarymanagement.Book;
import com.pratishaad.homelibrarymanagement.lentbooks.LendBookDetails;
import com.pratishaad.homelibrarymanagement.R;

import org.jetbrains.annotations.NotNull;

public class ViewBookDetails extends AppCompatActivity {

    Button lendBook, receiveBook, deleteBook, editBook;
    ImageView mCoverImage;
    TextView bookTitle, bookAuthor,bookISBN,bookGenre,bookDescription,currentlyReadingBool,lendBookBool,lendLendeeName,lendGiveDate, lendReceiveDate;
    FirebaseAuth fAuth;
    DatabaseReference databaseRef;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_details);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);



        mCoverImage=(ImageView)findViewById(R.id.editBookImageView);

        bookTitle = (TextView)findViewById(R.id.book_title);
        bookAuthor = (TextView)findViewById(R.id.book_author);
        bookISBN = (TextView)findViewById(R.id.book_ISBN);
        bookGenre = (TextView)findViewById(R.id.book_genre);
        bookDescription = (TextView)findViewById(R.id.book_description);
        currentlyReadingBool = (TextView)findViewById(R.id.book_currently_reading);
        lendBookBool = (TextView)findViewById(R.id.book_lendBookBool);
        lendLendeeName = (TextView)findViewById(R.id.book_lendLendeeName);
        lendGiveDate = (TextView)findViewById(R.id.book_lendGiveDate);
        lendReceiveDate = (TextView)findViewById(R.id.book_lendReceiveDate);

        lendBook=(Button)findViewById(R.id.lendBook);
        receiveBook=(Button)findViewById(R.id.receiveBook);
        deleteBook=(Button)findViewById(R.id.deleteBook);
        editBook=(Button) findViewById(R.id.editBook);



        final Bundle extras = getIntent().getExtras();
        if(extras!=null){

            Glide.with(getApplicationContext()).load(extras.getString("Image")).into(mCoverImage);

            bookTitle.setText(extras.getString("Title"));
            bookAuthor.setText(extras.getString("Author"));
            bookDescription.setText(extras.getString("Description"));
            bookGenre.setText(extras.getString("Genre"));
            bookISBN.setText(extras.getString("ISBN"));
            currentlyReadingBool.setText(extras.getString("CurrentReadBool"));
            lendBookBool.setText(extras.getString("LendBool"));
            if(extras.getString("LendBool").equals("No"))
            {
                findViewById(R.id.lentOn).setVisibility(View.GONE);
                findViewById(R.id.lendTo).setVisibility(View.GONE);
                findViewById(R.id.receivedDate).setVisibility(View.GONE);
                findViewById(R.id.receiveBook).setVisibility(View.GONE);
            }
            else
            {
                lendLendeeName.setText(extras.getString("Lendee"));
                lendGiveDate.setText(extras.getString("GiveDate"));
                lendReceiveDate.setText(extras.getString("ReceiveDate"));
                findViewById(R.id.lendBook).setVisibility(View.GONE);
            }


        }

        lendBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lendBookBool.getText().equals("Yes")) {
                    Intent intent = new Intent(getApplicationContext(), LendBookDetails.class);
                    intent.putExtra("ImageLend", extras.getString("Image"));
                    intent.putExtra("TitleLend", extras.getString("Title"));
                    intent.putExtra("AuthorLend", extras.getString("Author"));
                    intent.putExtra("LendBookID", extras.getString("BookID"));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Book has already been lent to "+
                            extras.getString("Lendee"), Toast.LENGTH_SHORT).show();
                }
            }
        });

        fAuth = FirebaseAuth.getInstance();
        databaseRef= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks").child(extras.getString("BookID"));

        receiveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(extras.getString("LendBool").equals("Yes")) {
                    Toast.makeText(getApplicationContext(), "Book received from: " +
                            extras.getString("Lendee"), Toast.LENGTH_SHORT).show();
                    databaseRef.child("lendBookBool").setValue("No");
                    databaseRef.child("lendGiveDate").setValue("N/A");
                    databaseRef.child("lendReceiveDate").setValue("N/A");
                    databaseRef.child("lendLendeeName").setValue("N/A");
                    lendBookBool.setText("No");
                    lendGiveDate.setText("N/A");
                    lendLendeeName.setText("N/A");
                    lendReceiveDate.setText("N/A");
                    Intent intent = new Intent(getApplicationContext(), ViewBooks.class);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),
                        "Book not lent to anyone", Toast.LENGTH_SHORT).show();
            }
        });


        editBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditBook.class);
                intent.putExtra("Image",extras.getString("Image"));
                intent.putExtra("Title",extras.getString("Title"));
                intent.putExtra("Author", extras.getString("Author"));
                intent.putExtra("ISBN", extras.getString("ISBN"));
                intent.putExtra("Description",extras.getString("Description"));
                intent.putExtra("Genre", extras.getString("Genre"));
                intent.putExtra("CurrentReadBool", extras.getString("CurrentReadBool"));
                intent.putExtra("LendBool", extras.getString("LendBool"));
                intent.putExtra("Lendee", extras.getString("Lendee"));
                intent.putExtra("GiveDate", extras.getString("GiveDate"));
                intent.putExtra("ReceiveDate",extras.getString("ReceiveDate"));
                intent.putExtra("BookID",extras.getString("BookID"));
                startActivity(intent);
            }
        });



        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookDetails.this);
                    builder.setTitle("Confirm Deletion")
                            .setMessage("Do you really want to delete this book? This change cannot be reverted.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Book b = dataSnapshot.getValue(Book.class);
                                        final String delTitle = b.getBookTitle();
                                        final String delImg = b.getImageFirebaseURI();

                                        databaseRef.removeValue();
                                        Toast.makeText(getApplicationContext(),
                                                delTitle.trim().replace("\n", " ") +
                                                        " has been deleted.", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),ViewBooks.class));
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
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ViewBooks.class);
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