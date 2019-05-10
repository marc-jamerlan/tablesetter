package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable{
    private int ID;
    private String name;
    private String notes;

    public Tag(){}

    public Tag(String name, String notes)
    {
        this.name = name;
        this.notes = notes;
    }
    protected Tag(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        notes = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
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

