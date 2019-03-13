package com.example.erykvaughn373.newmactest;

public class Game {
    private int gameImage;
    private String name;
    private String tag1;
    private String tag2;
    private String tag3;

    public Game(int a, String b, String c, String d, String e){
        this.gameImage = a;
        this.name = b;
        this.tag1 = c;
        this.tag2 = d;
        this.tag3 = e;
    }

    public int getGameImage() {
        return gameImage;
    }

    public void setGameImage(int gameImage) {
        this.gameImage = gameImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }
}
