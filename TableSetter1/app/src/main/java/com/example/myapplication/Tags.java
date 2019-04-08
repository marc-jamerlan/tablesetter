package com.example.myapplication;

public class Tags {
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

    public int getID() { return ID; }

    public void setID(int id) { this.ID = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }
}

