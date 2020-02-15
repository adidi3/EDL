package com.example.edl;

class User {
    private String name, email, phone, uid, id, password;

    public User (){}
    public User (String name, String email, String phone, String uid, String id, String password) {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.id=id;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid=uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}
