package com.example.edl;

public class Ustudents {
    private String name, email, phone, uid, id, password, date, wteacher;
    private boolean student, manual, female;
    public Ustudents (String name, String email, String phone, String uid, String id, String password, Boolean student, Boolean manual, Boolean female, String date, String wteacher) {
        this.name=name;
        this.date=date;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.id=id;
        this.wteacher=wteacher;
        this.password=password;
        this.female=female;
        this.manual=manual;
        this.student=student;
    }
    public Boolean getFemale() {
        return female;
    }

    public void setFemale(Boolean female) {this.female=female;}

    public Boolean getStudent() {
        return student;
    }

    public void setStudent(Boolean student) {this.student=student;}

    public Boolean getManual() {
        return manual;
    }

    public void setManual(Boolean manual) {this.manual=manual;}

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

    public String getWteacher() {
        return wteacher;
    }

    public void setWteacher(String wteacher) {
        this.wteacher=wteacher;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }


}
