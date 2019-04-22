package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Tags implements Parcelable{
    private int ID;
    private String name;
    private String notes;

    public Tags(){}

    public Tags(int id, String name, String notes)
    {
        this.ID = id;
        this.name = name;
        this.notes = notes;
    }
    protected Tags(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        notes = in.readString();
    }

    public static final Creator<Tags> CREATOR = new Creator<Tags>() {
        @Override
        public Tags createFromParcel(Parcel in) {
            return new Tags(in);
        }

        @Override
        public Tags[] newArray(int size) {
            return new Tags[size];
        }
    };

    public int getID() { return ID; }

    public void setID(int id) { this.ID = id; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(notes);
    }
}

