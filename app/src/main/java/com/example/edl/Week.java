package com.example.edl;
/**
 * a week class. includes the days of lessons in a week.
 */
public class Week {
    /** day class builder. this function gets all of the variables in day's variable .
     * @param sunday
     * @param monday
     * @param tuesday
     * @param wednesday
     * @param thursday
     */
    private String sunday, monday, tuesday, wednesday, thursday;
    public Week() {
        this.sunday="sunday";
        this.monday="monday";
        this.tuesday="tuesday";
        this.wednesday="wednesday";
        this.thursday="thursday";

    }
    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday=sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday=monday;
    }

    public String getTuesday() { return tuesday; }

    public void setTuesday(String tuesday) {
        this.tuesday=tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday=wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday=thursday;
    }


}


