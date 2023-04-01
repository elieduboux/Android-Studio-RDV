package com.example.projet;

import android.os.Parcelable;

import java.sql.Time;
import java.util.Date;

public class Rdv {
    public int id;          // Unique identifier auto-incremented
    public String title;    // Can also be a description
    public String date;       // Date of the rdv
//    public Time time;       // The of the rdv
//    public String person; // The contact or a person
//    public String address // The localisation of the rdv
//    public String phone   // The phone number of the person to contact
    public Boolean state;   // rdv done or not done yet

//    public Rdv(int id, String title, Date date, Time time, Boolean state) {
//        this.id = id;
//        this.title = title;
//        this.date = date;
//        this.time = time;
//        this.state = state;
//    }

    public Rdv(int id, String title, String date, Boolean state) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.state = state;
    }
}
