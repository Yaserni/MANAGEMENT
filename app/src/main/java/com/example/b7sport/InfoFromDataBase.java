package com.example.b7sport;

public class InfoFromDataBase {

    private String Email,PhoneNumber,FullName;
    public InfoFromDataBase(){}
    public InfoFromDataBase(String email, String phoneNumber, String fullName) {
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
