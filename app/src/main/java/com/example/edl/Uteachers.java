package com.example.edl;

public class Uteachers {
    private String name, email, phone, uid, id, money;
    private boolean student;
    /**
     * an empty builder. is necessary to use Firebase
     */
public Uteachers(){}
    /** User class builder. this function gets all of the variables in order to create a teacher's user.
     * @param name
     * @param email
     * @param phone
     * @param id
     * @param student
     * @param uid
     * @param money
     */
    public Uteachers (String name, String email, String phone, String uid, String id, Boolean student, String money){
        this.money=money;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.id=id;
        this.student=student;

    }




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
        this.money=money;
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

}
