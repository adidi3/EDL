package com.example.edl;

public class Uteachers {
    private String name, email, phone, uid, id, password, money;
    private boolean student;
    public Uteachers (String name, String email, String phone, String uid, String id, String password, Boolean student, String money){
        this.money=money;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.id=id;
        this.password=password;
        this.student=student;

    }
    public Uteachers(){
        this.name = "";
        this.email = "";
        this.uid = "";
        this.phone="";
        this.id="";
        this.money="";
        this.password="";
        this.student=false;
    }

    public void copyUser(Uteachers user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.id = user.getId();
        this.money = user.getMoney();
        this.password = user.getPassword();
        this.student = user.getStudent();
        this.uid = user.getUid();}


    public Boolean getStudent() {
        return student;
    }

    public void setStudent(Boolean student) {this.student=student;}



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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money=phone;
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
