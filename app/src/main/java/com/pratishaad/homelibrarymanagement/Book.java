package com.pratishaad.homelibrarymanagement;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Book {
    String bookId;
    String bookTitle;
    String bookAuthor;
    String bookISBN;
    String bookGenre;
    String bookDescription;
    String coverImageName;
    String coverImageUrl;
    String imageFirebaseURI;


    public Book() {

    }

    public Book(String bookId, String bookTitle, String bookAuthor, String bookISBN, String bookDescription, String bookGenre, String imageFirebaseURI) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookISBN = bookISBN;
        this.bookGenre = bookGenre;
        this.bookDescription = bookDescription;
        this.imageFirebaseURI = imageFirebaseURI;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public String getCoverImageURL() {
        return coverImageUrl;
    }

    public String getImageFirebaseURI () {
         return imageFirebaseURI;
    }

    public FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
