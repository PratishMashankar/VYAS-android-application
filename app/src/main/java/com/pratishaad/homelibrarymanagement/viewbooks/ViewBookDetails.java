package com.pratishaad.homelibrarymanagement.viewbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

        mCoverImage=(ImageView)findViewById(R.id.viewBookDetailsImageView);

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
            lendLendeeName.setText(extras.getString("Lendee"));
            lendGiveDate.setText(extras.getString("GiveDate"));
            lendReceiveDate.setText(extras.getString("ReceiveDate"));

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
                    Toast.makeText(getApplicationContext(), "Book has already been lent to "+extras.getString("Lendee"), Toast.LENGTH_SHORT).show();
                }
            }
        });

        fAuth = FirebaseAuth.getInstance();
        databaseRef= FirebaseDatabase.getInstance().getReference().child(fAuth.getUid()).child("AllBooks").child(extras.getString("BookID"));

        receiveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaseRef.child("lendBookBool").equals("Yes")) {
                    Toast.makeText(getApplicationContext(), "Book received from: " + extras.getString("Lendee"), Toast.LENGTH_SHORT).show();
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
                else Toast.makeText(getApplicationContext(), "Book not lent to anyone", Toast.LENGTH_SHORT).show();
            }
        });


        editBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                                        /*FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                       StorageReference photoRef = firebaseStorage.getReferenceFromUrl(delImg);

                                        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // File deleted successfully
                                                Toast.makeText(getApplicationContext(), "Image has been deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Uh-oh, an error occurred!
                                                //Log.d(TAG, "onFailure: did not delete file");
                                                Toast.makeText(getApplicationContext(), "Unable to delete Image", Toast.LENGTH_SHORT).show();
                                            }
                                        });*/

                                        databaseRef.removeValue();
                                        Toast.makeText(getApplicationContext(), delTitle.trim().replace("\n", " ") + " has been deleted.", Toast.LENGTH_LONG).show();
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
}