package com.example.librarymanagmentsystem;



public class Author {


    private int AID;
    private String AName;
    private String FamilyName ;
    private String Type ;
    private String Email ;
    private int phoneNumber ;
    private String DateOfBirth;

    public Author(int AID, String AName, String familyName, String type, String email, int phoneNumber, String dateOfBirth) {
        this.AID = AID;
        this.AName = AName;
        FamilyName = familyName;
        Type = type;
        Email = email;
        this.phoneNumber = phoneNumber;
        DateOfBirth = dateOfBirth;
    }


    public int getAID() {
        return AID;
    }
    public void setAID(int AID) {
        this.AID = AID;
    }
    public String getAName() {
        return AName;
    }
    public void setAName(String AName) {
        this.AName = AName;
    }
    public String getFamilyName() {
        return FamilyName;
    }
    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getDateOfBirth() {
        return DateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }



}