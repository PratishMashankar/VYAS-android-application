package com.pratishaad.homelibrarymanagement;

public class Book {
    String bookId;
    String bookTitle;
    String bookAuthor;
    String bookISBN;
    String bookGenre;
    String bookDescription;
    String imageFirebaseURI;
    String currentlyReadingBool;
    String lendBookBool;
    String lendLendeeName;
    String lendGiveDate;
    String lendReceiveDate;



    public Book(){

    }

    public Book(String bookId, String bookTitle, String bookAuthor,
                String bookISBN, String bookDescription, String bookGenre,
                String imageFirebaseURI, String currentlyReadingBool, String lendBookBool,
                String lendLendeeName, String lendGiveDate, String lendReceiveDate){
        this.bookId=bookId;
        this.bookTitle=bookTitle;
        this.bookAuthor=bookAuthor;
        this.bookISBN=bookISBN;
        this.bookGenre=bookGenre;
        this.bookDescription=bookDescription;
        this.imageFirebaseURI=imageFirebaseURI;
        this.currentlyReadingBool=currentlyReadingBool;
        this.lendBookBool=lendBookBool;
        this.lendLendeeName=lendLendeeName;
        this.lendGiveDate=lendGiveDate;
        this.lendReceiveDate=lendReceiveDate;

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

    public String getImageFirebaseURI() {
        return imageFirebaseURI;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public void setImageFirebaseURI(String imageFirebaseURI) {
        this.imageFirebaseURI = imageFirebaseURI;
    }

    public String getCurrentlyReadingBool() {
        return currentlyReadingBool;
    }

    public void setCurrentlyReadingBool(String currentlyReadingBool) {
        this.currentlyReadingBool = currentlyReadingBool;
    }

    public String getLendBookBool() {
        return lendBookBool;
    }

    public void setLendBookBool(String lendBookBool) {
        this.lendBookBool = lendBookBool;
    }

    public String getLendLendeeName() {
        return lendLendeeName;
    }

    public void setLendLendeeName(String lendLendeeName) {
        this.lendLendeeName = lendLendeeName;
    }

    public String getLendGiveDate() {
        return lendGiveDate;
    }

    public void setLendGiveDate(String lendGiveDate) {
        this.lendGiveDate = lendGiveDate;
    }

    public String getLendReceiveDate() {
        return lendReceiveDate;
    }

    public void setLendReceiveDate(String lendReceiveDate) {
        this.lendReceiveDate = lendReceiveDate;
    }
}
