package com.example.edl;

public class Ustudents {
    public String name, email, phone, uid, id, date, wteacher, count;
    public boolean student, manual, female;
    public Ustudents(){}
    public Ustudents (String name, String email, String phone, String uid, String id, Boolean student, Boolean manual, Boolean female, String date, String wteacher, String count) {
        this.name=name;
        this.date=date;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.id=id;
        this.wteacher=wteacher;
        this.female=female;
        this.manual=manual;
        this.student=student;
        this.count=count;
    }

    public void copyStudent(Ustudents student){
        this.name = student.getName();
        this.email = student.getEmail();
        this.date = student.getDate();
        this.phone = student.getPhone();
        this.id = student.getId();
        this.wteacher = student.getWteacher();
        this.female = student.getFemale();
        this.manual = student.getManual();
        this.student = student.getStudent();
        this.count = student.getCount();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

}
