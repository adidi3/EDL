package com.example.edl;
/**
 * a day class. includes the hours of lessons in a day.
 */
public class Day {
    /** day class builder. this function gets all of the variables in day's variable .
     * @param l1
     * @param l2
     * @param l3
     * @param l9
     * @param l4
     * @param l5
     * @param l6
     * @param l7
     * @param l8
     *  @param l0
     */
    private String l1, l2, l3, l4, l5, l6, l7, l8, l9, l0;
    public Day () {
        this.l0="08:00-08:40";
        this.l1="08:45-09:25";
        this.l2="10:00-10:40";
        this.l3="10:45-11:25";
        this.l4="12:30-13:10";
        this.l5="13:15-13:55";
        this.l6="14:00-14:40";
        this.l7="14:45-15:25";
        this.l8="19:00-19:40";
        this.l9="19:45-20:25";

    }

    public String getL1() {
        return l1;
    }

    public String getL2() {
        return l2;
    }

    public String getL3() {
        return l3;
    }

    public String getL4() {
        return l4;
    }

    public String getL5() {
        return l5;
    }

    public String getL6() {
        return l6;
    }

    public String getL7() {
        return l7;
    }

    public String getL8() {
        return l8;
    }

    public String getL9() {
        return l9;
    }

    public String getL0() {
        return l0;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public void setL3(String l3) {
        this.l3 = l3;
    }

    public void setL4(String l4) {
        this.l4 = l4;
    }

    public void setL5(String l5) {
        this.l5 = l5;
    }

    public void setL6(String l6) {
        this.l6 = l6;
    }

    public void setL7(String l7) {
        this.l7 = l7;
    }

    public void setL8(String l8) {
        this.l8 = l8;
    }

    public void setL9(String l9) {
        this.l9 = l9;
    }

    public void setL0(String l0) {
        this.l0 = l0;
    }
}
