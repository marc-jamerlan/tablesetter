package com.example.myapplication;

import android.app.Application;
import java.util.ArrayList;

public class GameNameList extends Application
{
    private ArrayList<String> nameArray;

    public GameNameList()
    {
        this.nameArray = new ArrayList<>();
    }

    public ArrayList<String> getGameNameList()
    {
        return nameArray;
    }

    public void appendList(String name)
    {
        this.nameArray.add(name);
    }

    public void removeList(String name)
    {
        for(int i = 0; i < this.nameArray.size(); i++)
        {
            if(this.nameArray.get(i).equalsIgnoreCase(name))
            {
                this.nameArray.remove(i);
                break;
            }
        }
    }
}
