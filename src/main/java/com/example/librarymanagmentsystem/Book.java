package com.example.librarymanagmentsystem;

public class Book {

    private int bookId;
    private String title;
    private int ISBN;
    private int editionNo;
    private int numberOfPages;
    private int authorId;
    private int publisherId;
    private String category;
    private int availability;

    // Constructors
    public Book() {
    }

    public Book(int bookId, String title, int ISBN, int editionNo, int numberOfPages,
                int authorId, int publisherId, String category, int availability) {
        this.bookId = bookId;
        this.title = title;
        this.ISBN = ISBN;
        this.editionNo = editionNo;
        this.numberOfPages = numberOfPages;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.category = category;
        this.availability = availability;
    }

    public Book(int id, String title, String category, int numberOfPages, int editionNo, int author, int publisher,int availbity) {
        this.bookId = id;
        this.title = title;
        this.editionNo = editionNo;
        this.numberOfPages = numberOfPages;
        this.authorId = author;
        this.publisherId = publisher;
        this.category = category;
        this.availability = availbity;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getEditionNo() {
        return editionNo;
    }

    public void setEditionNo(int editionNo) {
        this.editionNo = editionNo;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
