package com.example.librarymanagmentsystem;

import java.util.Date;

public class Borrow {
    private int id;
    private int book_id;
    private int member_id;
    private Date due_date;
    private Date issue_date;

    public Borrow() {
    }

    public Borrow(int id, int book_id, int member_id, Date due_date, Date issue_date) {
        this.id = id;
        this.book_id = book_id;
        this.member_id = member_id;
        this.due_date = due_date;
        this.issue_date = issue_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return book_id;
    }

    public void setBookId(int bookId) {
        this.book_id = bookId;
    }

    public int getMemberId() {
        return member_id;
    }

    public void setMemberId(int memberId) {
        this.member_id = memberId;
    }

    public Date getDueDate() {
        return due_date;
    }

    public void setDueDate(Date dueDate) {
        this.due_date = dueDate;
    }

    public Date getIssueDate() {
        return issue_date;
    }

    public void setIssueDate(Date issueDate) {
        this.issue_date = issueDate;
    }
}
