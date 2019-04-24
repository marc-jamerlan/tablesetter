package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Game implements Parcelable {
    private int ID;
    private String name;
    private int gameImage;
    private ArrayList<Tags> tagArray; // change this to an array of tag IDs perhaps?
    private String notes;

    public Game(){
        this.ID = -1;
        this.gameImage = -1;
        this.name = "";
        this.tagArray = new ArrayList<>();
        this.notes = "";
    }

    public Game(int id, int a, String b, String notes){
        this.ID = id;
        this.gameImage = a;
        this.name = b;
        this.tagArray = new ArrayList<>();
        this.notes = notes;
    }

    protected Game(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        gameImage = in.readInt();
        notes = in.readString();
        in.readTypedList(tagArray,Tags.CREATOR);
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int id) { this.ID = id; }

    public int getGameImage() {
        return gameImage;
    }

    public void setGameImage(int gameImage) { this.gameImage = gameImage; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTagArray(Tags tag){this.tagArray.add(tag);}

    public void setTagArray(ArrayList<Tags> taglist){
        this.tagArray = taglist;
    }

    // not done here, revisit
    public ArrayList<Tags> getTagArray()
    {
        return tagArray;
    }

    public void removeTag(Tags tag){
        this.tagArray.remove(tag);
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
        dest.writeInt(gameImage);
        dest.writeString(notes);
        dest.writeTypedList(tagArray);
    }
}
