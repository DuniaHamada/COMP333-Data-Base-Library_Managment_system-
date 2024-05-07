package com.example.librarymanagmentsystem;


import java.util.Date;

public class Return {
    int ReturnID;
    int BorrowingID;
    Date return_date ;
    int  fees ;
    public Return(int returnID, int borrowingID, Date return_date, int fees) {
        super();
        ReturnID = returnID;
        BorrowingID = borrowingID;
        this.return_date = return_date;
        this.fees = fees;
    }
    public int getReturnID() {
        return ReturnID;
    }
    public void setReturnID(int returnID) {
        ReturnID = returnID;
    }
    public int getBorrowingID() {
        return BorrowingID;
    }
    public void setBorrowingID(int borrowingID) {
        BorrowingID = borrowingID;
    }
    public Date getReturn_date() {
        return return_date;
    }
    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }
    public int getFees() {
        return fees;
    }
    public void setFees(int fees) {
        this.fees = fees;
    }

}