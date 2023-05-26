package com.example.myamazon;

public class Customer {
    int id;
    String userName ;
    String password;
    String email;
    String image;

    public Customer(int id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;

    }

    public Customer(String userName, String password, String email, String image) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public Customer(int id, String userName, String password, String email, String image) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public Customer(String userName, String password, String email) {

        this.userName = userName;
        this.password = password;
        this.email = email;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
