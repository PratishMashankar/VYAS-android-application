package com.pratishaad.homelibrarymanagement;

public class Book {
    String bookId;
    String bookTitle;
    String bookAuthor;
    String bookISBN;
    String bookGenre;
    String bookDescription;
<<<<<<< HEAD
    String coverImageName;
    String coverImageUrl;
=======
    String imageFirebaseURI;
>>>>>>> 2a82732b75f3f03d99b4d9506d3c66f519abdb46


    public Book(){

    }

<<<<<<< HEAD
    public Book(String bookId, String bookTitle, String bookAuthor, String bookISBN, String bookDescription, String bookGenre,String coverImageUrl){
=======
    public Book(String bookId, String bookTitle, String bookAuthor, String bookISBN, String bookDescription, String bookGenre, String imageFirebaseURI){
>>>>>>> 2a82732b75f3f03d99b4d9506d3c66f519abdb46
        this.bookId=bookId;
        this.bookTitle=bookTitle;
        this.bookAuthor=bookAuthor;
        this.bookISBN=bookISBN;
        this.bookGenre=bookGenre;
        this.bookDescription=bookDescription;
<<<<<<< HEAD
//        this.coverImageName=coverImageName;
        this.coverImageUrl=coverImageUrl;
    }

    public Book(String coverImageName, String coverImageUrl)
    {
        this.coverImageName=coverImageName;
        this.coverImageUrl=coverImageUrl;

=======
        this.imageFirebaseURI=imageFirebaseURI;
>>>>>>> 2a82732b75f3f03d99b4d9506d3c66f519abdb46
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

<<<<<<< HEAD
//    public String getCoverImageName() {
//        return coverImageName;
//    }

    public String getCoverImageURL(){
        return coverImageUrl;
=======
    public String getImageFirebaseURI() {
        return imageFirebaseURI;
>>>>>>> 2a82732b75f3f03d99b4d9506d3c66f519abdb46
    }
}
