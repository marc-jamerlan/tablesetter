package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;

public class Player {
    private int ID;
    private String name;
    private String gameImage;
    private ArrayList<String> gameList;
    private ArrayList<Integer> customTags;
    private String notes;

    public Player(String name,String notes){
        this.ID = -1;
        this.name = name;
        this.gameImage = "";
        this.gameList = new ArrayList<>();
        this.customTags = new ArrayList<>();
        this.notes = notes;
    }

    public Player(){
        this.ID = -1;
        this.name = "";
        this.gameImage = "";
        this.gameList = new ArrayList<>();
        this.customTags = new ArrayList<>();
        this.notes = "";
    }

    public void setID(int ID){ this.ID = ID; }

    public void setName(String name){
        this.name = name;
    }

    public void setGameImage(String gameImage) { this.gameImage = gameImage; }

    public void setGameList(ArrayList<String> gameList){
        this.gameList = gameList;
    }

    public void addGameList(String gameName){
        this.gameList.add(gameName);
    }

    public void setCustomTags(ArrayList<Integer> taglist){
        this.customTags = taglist;
    }

    public void addCustomTag(Integer tag){
        this.customTags.add(tag);
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public int getID(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }

    public String getGameImage() { return gameImage; }

    public ArrayList<String> getGameList() {
        return this.gameList;
    }

    public String getSingleGame(int location){
        return this.gameList.get(location);
    }

    public ArrayList<Integer> getCustomTags() {
        return this.customTags;
    }

    public int getSingleTag(int location){
        return this.customTags.get(location);
    }

    public String getNotes() {
        return this.notes;
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
