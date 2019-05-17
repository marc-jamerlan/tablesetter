package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.util.ArrayList;

public class Player implements Parcelable {
    private int ID;
    private String name;
    private String playerImage;
    private ArrayList<String> gameNameList;
    private ArrayList<Integer> tagIDList;
    private String notes;

    public Player(String name,String notes){
        this.ID = -1;
        this.name = name;
        this.playerImage = "";
        this.gameNameList = new ArrayList<>();
        this.tagIDList = new ArrayList<>();
        this.notes = notes;
    }

    public Player(){
        this.ID = -1;
        this.name = "";
        this.playerImage = "";
        this.gameNameList = new ArrayList<>();
        this.tagIDList = new ArrayList<>();
        this.notes = "";
    }

    protected Player(Parcel in){
        this.ID = in.readInt();
        this.name = in.readString();
        this.playerImage = in.readString();
        this.gameNameList = (ArrayList<String>) in.readSerializable();
        this.tagIDList = (ArrayList<Integer>) in.readSerializable();
        this.notes = in.readString();

    }



    public void setID(int ID){ this.ID = ID; }

    public void setName(String name){
        this.name = name;
    }

    public void setPlayerImage(String playerImage) { this.playerImage = playerImage; }

    public void setGameNameList(ArrayList<String> gameNameList){
        this.gameNameList = gameNameList;
    }

    public void addGameList(String gameName){
        this.gameNameList.add(gameName);
    }

    public void setTagIDList(ArrayList<Integer> taglist){
        this.tagIDList = taglist;
    }

    public void addCustomTag(Integer tag){
        this.tagIDList.add(tag);
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

    public String getPlayerImage() { return playerImage; }

    public ArrayList<String> getGameNameList() {
        return this.gameNameList;
    }

    public String getSingleGame(int location){
        return this.gameNameList.get(location);
    }

    public String getGameNameListString()
    {
        StringBuilder gameString = new StringBuilder();
        for(int i = 0; i < gameNameList.size(); i++)
        {
            gameString.append(gameNameList.get(i));
            gameString.append(";");
        }

        return gameString.toString();
    }

    public ArrayList<Integer> getTagIDList() {
        return this.tagIDList;
    }

    public int getSingleTag(int location){
        return this.tagIDList.get(location);
    }

    public String getTagIDListString()
    {
        StringBuilder tagString = new StringBuilder();
        for(int i = 0; i < tagIDList.size(); i++)
        {
            tagString.append(tagIDList.get(i));
            tagString.append(";");
        }

        return tagString.toString();
    }

    public String getNotes() {
        return this.notes;
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(playerImage);
        dest.writeString(notes);
        dest.writeSerializable(gameNameList);
        dest.writeSerializable(tagIDList);
        //dest.writeInt(tagsAdded);
        //dest.writeInt(edited);
    }


    public Bitmap decodeGameImage()
    {
        try
        {
            byte[] encodeByte = Base64.decode(playerImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e)
        {
            e.getMessage();
            return null;
        }
    }
}
