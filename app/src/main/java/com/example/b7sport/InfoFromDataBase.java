package com.example.b7sport;

public class InfoFromDataBase {

    private String Email,PhoneNumber,FullName,Address;
    public InfoFromDataBase(){}
    public InfoFromDataBase(String email, String phoneNumber, String fullName,String address) {
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.FullName = fullName;
        this.Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress(){return Address;}

    public void setAddress(String address){Address = address;}

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
