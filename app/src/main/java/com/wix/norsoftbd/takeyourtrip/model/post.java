package com.wix.norsoftbd.takeyourtrip.model;

/**
 * Created by MNH on 10/31/2017.
 */

public class post {
    String user;
    String title;
    String email;
    String phone;
    String description;
    String validity;
    String location;
    String id;

    String userIcon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public post() {
    }

    public post(String user, String title, String email, String phone, String description, String validity, String location,String id) {
        this.user = user;
        this.title = title;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.validity = validity;
        this.location = location;
        this.id=id;
    }

//    public post(String user, String title, String email, String phone, String description, String validity, String location, String userIcon
//    ,String id) {
//        this.title = title;
//        this.email = email;
//        this.phone = phone;
//        this.description = description;
//        this.validity = validity;
//        this.location = location;
//        this.user=user;
//        this.userIcon=userIcon;
//    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
