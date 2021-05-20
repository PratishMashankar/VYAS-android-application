package com.pratishaad.homelibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewBookDetails extends AppCompatActivity {

    Button lendBook;
    ImageView mCoverImage;
    TextView bookTitle, bookAuthor,bookISBN,bookGenre,bookDescription,currentlyReadingBool,lendBookBool,lendLendeeName,lendGiveDate, lendReceiveDate;


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

        Bundle extras = getIntent().getExtras();
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
                Intent intent = new Intent(getApplicationContext(),LendBookDetails.class);
                startActivity(intent);

            }
        });
    }
}