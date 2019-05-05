package com.wix.norsoftbd.takeyourtrip.model;

/**
 * Created by MNH on 10/31/2017.
 */

public class user {


    private  String userName;
    private String Name;
    private String Email;
    private  String passWord;
    private String userType;
    public user() {
    }

    public user(String name,String userName, String email, String passWord, String userType) {
        this.Name = name;
        this.Email = email;
        this.userName=userName;
        this.passWord = passWord;
        this.userType = userType;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
