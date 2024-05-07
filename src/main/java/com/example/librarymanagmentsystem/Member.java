package com.example.librarymanagmentsystem;

public class Member {
    private int ID;
    private String Name;
    private String Date_Birth;
    private String Email;
    private int phone;
    private String Address;
    private String Gender;
    private String Date_Subscribing;
    private String Date_Caneling;


    static Member Member;





    public Member(int ID, String name, String date_Birth, String email, int phone, String address, String gender,
                  String Date_Subscribing, String Date_Caneling) {
        super();
        this.ID = ID;
        Name = name;
        Date_Birth = date_Birth;
        Email = email;
        this.phone = phone;
        Address = address;
        Gender = gender;
        this.Date_Subscribing =Date_Subscribing;
        this.Date_Caneling= Date_Caneling;
    }





    public int getID() {
        return ID;
    }





    public void setID(int ID) {
        this.ID = ID;
    }





    public String getName() {
        return Name;
    }





    public void setName(String name) {
        Name = name;
    }





    public String getDate_Birth() {
        return Date_Birth;
    }





    public void setDate_Birth(String date_Birth) {
        Date_Birth = date_Birth;
    }





    public String getEmail() {
        return Email;
    }





    public void setEmail(String email) {
        Email = email;
    }





    public int getPhone() {
        return phone;
    }





    public void setPhone(int phone) {
        this.phone = phone;
    }





    public String getAddress() {
        return Address;
    }





    public void setAddress(String address) {
        Address = address;
    }





    public String getGender() {
        return Gender;
    }





    public void setGender(String gender) {
        Gender = gender;
    }





    public String getDate_Subscribing() {
        return Date_Subscribing;
    }





    public void setDate_Subscribing(String Date_Subscribing) {
        this.Date_Subscribing = Date_Subscribing;
    }





    public String getDate_Caneling() {
        return Date_Caneling;
    }





    public void setDate_Caneling(String Date_Caneling) {
        this.Date_Caneling =Date_Caneling;
    }





    public static Member getMember() {
        return Member;
    }





    public static void setMember(Member member) {
        Member = member;
    }




}
