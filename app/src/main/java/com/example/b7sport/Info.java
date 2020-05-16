package com.example.b7sport;

public class Info {

    String Email,PhoneNumber,Name,Password,UserID,flag;

    public String getEmail() {
        return Email;
    }
    public String getUserID(){
        return UserID;
    }
    public void setUserID(String userID){
        UserID = userID;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public  void setFlag(String flag)
    {

        this.flag=flag;

    }

    public  String getFlag()
    {
             return this.flag;
    }

    public Info() {
    }
    public Info(String Email,String PhoneNumber,String Name,String Password,String UserID,String flag) {
        this.Email = Email;
        this.Name = Name;
        this.Password = Password;
        this.PhoneNumber = PhoneNumber;
        this.UserID = UserID;
        this.flag=flag;
    }

     public boolean  t(){return  true;}
}
