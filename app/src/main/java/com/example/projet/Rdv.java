package com.example.projet;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.util.Date;

public class Rdv implements Parcelable {
    public int id;          // Unique identifier auto-incremented
    public String title;    // Can also be a description
    public String date;       // Date of the rdv
    public String time;       // The of the rdv
    public String person; // The contact or a person
    public String address; // The localisation of the rdv
    public String phone;   // The phone number of the person to contact
    public Boolean state;   // rdv done or not done yet
    public Rdv() {
    }

    public Rdv(int id, String title, String date, String time,String person, String phone, String address,Boolean state) {
        this.id    = id;
        this.title = title;
        this.date  = date;
        this.time  = time;
        this.person  = person;
        this.phone = phone;
        this.address = address;
        this.state = state;
    }

    public Rdv(String title, String date, String time, String person, String phone, String address,Boolean state) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.person = person;
        this.phone = phone;
        this.address = address;
        this.state = state;
    }

    public Rdv(Parcel parcel) {
        id    = parcel.readInt();
        title = parcel.readString();
        date  = parcel.readString();
        time = parcel.readString();
        person = parcel.readString();
        phone = parcel.readString();
        address = parcel.readString();
        state = parcel.readBoolean();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(person);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeBoolean(state);
    }

    public static final Parcelable.Creator<Rdv> CREATOR = new Parcelable.Creator<Rdv>(){
        @Override
        public Rdv createFromParcel(Parcel parcel) {
            return new Rdv(parcel);
        }
        @Override
        public Rdv[] newArray(int size) {
            return new Rdv[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public Boolean getState() {
        return state;
    }
    public String getTime() {
        return time;
    }
    public String getPerson() {
        return person;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPerson(String person) {
        this.person = person;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setState(Boolean state) {
        this.state = state;
    }
}
