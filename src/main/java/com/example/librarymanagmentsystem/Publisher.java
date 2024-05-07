package com.example.librarymanagmentsystem;

public class Publisher {
    private int publisherId;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Publisher()
    {

    }
    public Publisher(int publisherId, String name, String address, String email, String phone) {
        this.publisherId = publisherId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public int getPublisherId() {
        return publisherId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
