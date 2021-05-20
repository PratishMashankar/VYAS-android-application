package com.pratishaad.homelibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewBookDetails extends AppCompatActivity {

    Button lendBook, receiveBook;
    ImageView mCoverImage;
    TextView bookTitle, bookAuthor,bookISBN,bookGenre,bookDescription,currentlyReadingBool,lendBookBool,lendLendeeName,lendGiveDate, lendReceiveDate;
    FirebaseAuth fAuth;
    DatabaseReference databaseRef;


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
                Toast.makeText(getApplicationContext(), "Book received from: "+extras.getString("Lendee"), Toast.LENGTH_SHORT).show();
                databaseRef.child("lendBookBool").setValue("No");
                databaseRef.child("lendGiveDate").setValue("N/A");
                databaseRef.child("lendReceiveDate").setValue("N/A");
                databaseRef.child("lendLendeeName").setValue("N/A");
                lendBookBool.setText("No");
                lendGiveDate.setText("N/A");
                lendLendeeName.setText("N/A");
                lendReceiveDate.setText("N/A");
                Intent intent= new Intent(getApplicationContext(),ViewBooks.class);
                startActivity(intent);
            }
        });
    }
}