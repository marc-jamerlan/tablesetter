package com.example.myapplication;

import java.util.ArrayList;

public class Game {
    private int ID;
    private String name;
    private int gameImage;
    private ArrayList<Tags> tagArray; // change this to an array of tag IDs perhaps?
    private String notes;

    public Game(){}

    public Game(int id, int a, String b, String notes){
        this.ID = id;
        this.gameImage = a;
        this.name = b;
        this.tagArray = new ArrayList<>();
        this.notes = notes;
    }

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

    // not done here, revisit
    public ArrayList<Tags> getTagArray()
    {
        return tagArray;
    }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }
}
