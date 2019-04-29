package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;


import java.util.ArrayList;

public class Game implements Parcelable {
    private int ID;
    private String name;
    private String gameImage;
    private ArrayList<Integer> tagArray; // change this to an array of tag IDs perhaps?
    private String notes;
    private int tagsAdded;
    private int edited;

    public Game(){
        this.ID = -1;
        this.name = "";
        this.gameImage = "";
        this.tagArray = new ArrayList<>();
        this.notes = "";
        this.tagsAdded = 0;
        this.edited = 0;
    }

    public Game(int id, String a, String notes){
        this.ID = id;
        this.name = a;
        this.gameImage = "";
        this.tagArray = new ArrayList<>();
        this.notes = notes;
        this.tagsAdded = 0;
        this.edited = 0;
    }

    protected Game(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        gameImage = in.readString();
        notes = in.readString();
        tagArray = (ArrayList<Integer>) in.readSerializable();
        tagsAdded = in.readInt();
        edited = in.readInt();
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

    public String getGameImage() { return gameImage; }

    public void setGameImage(String gameImage) { this.gameImage = gameImage; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTagArray(Tags tag){this.tagArray.add(tag.getID());}

    public void setTagArray(ArrayList<Integer> taglist){ this.tagArray = taglist; }

    public ArrayList<Integer> getTagArray()
    {
        return tagArray;
    }

    public String getTagArrayString()
    {
        StringBuilder tagString = new StringBuilder();
        for(int i = 0; i < tagArray.size(); i++)
        {
            tagString.append(tagArray.get(i));
            tagString.append(";");
        }

        return tagString.toString();
    }

    public void removeTag(Tags tag){
        this.tagArray.remove(this.tagArray.indexOf(tag.getID()));
    }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public int hasTagsAdded() { return tagsAdded; }

    public void setTagsAdded(int tagsAdded) {this.tagsAdded = tagsAdded; }

    public int wasEdited() { return edited; }

    public void setEdited(int edited) {this.edited = edited; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(gameImage);
        dest.writeString(notes);
        dest.writeSerializable(tagArray);
        dest.writeInt(tagsAdded);
        dest.writeInt(edited);
    }

    public Bitmap decodeGameImage()
    {
        try
        {
            byte[] encodeByte = Base64.decode(gameImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e)
        {
            e.getMessage();
            return null;
        }
    }
}
