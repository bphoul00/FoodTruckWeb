package ca.uqam.projet.resources;

import java.sql.Date;


public class Dates {

    private int id;
    private int activitesID;
    private String dates;
    private int day;
    private int month;
    private int year;

    

    public Dates() {
    }

    public Dates(Dates date) {
        this.id = date.getId();
        this.dates = date.getDates();
        this.activitesID = date.getActivitesID();
        year = Integer.parseInt(dates.substring(0, 4));
        month = Integer.parseInt(dates.substring(5, 7));
        day = Integer.parseInt(dates.substring(8, 10));
    }

    public Dates(int id, int activitesID, String dates) {
        this.id = id;
        this.dates = dates;
        this.activitesID = activitesID;
        year = Integer.parseInt(dates.substring(0, 4));
        month = Integer.parseInt(dates.substring(5, 7));
        day = Integer.parseInt(dates.substring(8, 10));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivitesID() {
        return activitesID;
    }

    public void setActivitesID(int activitesID) {
        this.activitesID = activitesID;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
        this.year = Integer.parseInt(dates.substring(0, 4));
        this.month = Integer.parseInt(dates.substring(5, 7));
        this.day = Integer.parseInt(dates.substring(8, 10));
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    

}
