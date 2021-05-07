package com.pratishaad.homelibrarymanagement;

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
//        this.coverImageName=coverImageName;
        this.coverImageUrl = coverImageUrl;
    }

    public Book(String coverImageName, String coverImageUrl) {
        this.coverImageName = coverImageName;
        this.coverImageUrl = coverImageUrl;
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

//    public String getCoverImageName() {
//        return coverImageName;
//    }

    public String getCoverImageURL() {
        return coverImageUrl;
    }
    public String getImageFirebaseURI () {
         return imageFirebaseURI;
    }
}
