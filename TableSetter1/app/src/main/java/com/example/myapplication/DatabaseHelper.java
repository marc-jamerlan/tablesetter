package com.example.myapplication;

import android.content.Context;
import android.content.ContentValues;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "TableSetterDatabase";

    public static final String TABLE_NAME_1 = "GameTable";
    public static final String TABLE_NAME_2 = "TagTable";

    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "IMAGE";
    public static final String COL4 = "TAGLIST";
    public static final String COL5 = "NOTES";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 7);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE1 = "CREATE TABLE " + TABLE_NAME_1 + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT," + COL5 + " TEXT);";
        db.execSQL(CREATE_TABLE1);

        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME_2 + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT," + COL5 + " TEXT);";
        db.execSQL(CREATE_TABLE2);


        addPredefinedTags(db, new Tags( "Family Fun", "Fun for family and friends of all ages."));
        addPredefinedTags(db, new Tags( "Competitive", "For those who feel the need to win."));
        addPredefinedTags(db, new Tags( "Military", "Command and conquer, soldier."));
        addPredefinedTags(db, new Tags( "Fantasy", "An adventure from time immemorial."));
    }


    /***GAME TABLE***/


    public void addGameData(Game game)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, game.getName());
        values.put(COL3, game.getGameImage());
        String tagArrayString = game.getTagArrayString();
        values.put(COL4, tagArrayString);
        values.put(COL5, game.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_1, null, values);
        db.close();
    }

    public String loadGameData()
    {
        String result = "";
        String query = "Select*FROM " + TABLE_NAME_1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            //int result0 = cursor.getInt(0);
            String result1 = cursor.getString(1);
            result += result1 + ";";
        }

        cursor.close();
        db.close();
        return result;
    }

    public void clearGameData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME_1);

        db.close();
    }

    public boolean updateGameData(Game game)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, game.getName());
        values.put(COL3, game.getGameImage());
        String tagArrayString = game.getTagArrayString();
        values.put(COL4, tagArrayString);
        values.put(COL5, game.getNotes());

        return db.update(TABLE_NAME_1, values, COL1 + "=" + game.getID(), null) > 0;
    }

    public Game fetchGameData(String gameName)
    {
        String query = "SELECT*FROM " + TABLE_NAME_1 + " WHERE " + COL2 + " = " + "?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{gameName});
        Game game = new Game();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();

            game.setID(cursor.getInt(0));
            game.setName(cursor.getString(1));
            game.setGameImage(cursor.getString(2));
            String tagArrayString = cursor.getString(3);
            ArrayList<Integer> tagIDList = new ArrayList<>();
            if(tagArrayString != null)
            {
                String[] tagArray = tagArrayString.split(";");

                for (int i = 0; i < tagArray.length; i++)
                {
                    try
                    {
                        tagIDList.add(Integer.parseInt(tagArray[i]));
                    } catch (NumberFormatException e)
                    {
                        // nothing to be done
                    }
                }
            }
            game.setTagArray(tagIDList);
            game.setNotes(cursor.getString(4));

            cursor.close();
        }

        else
        {
            game = null;
        }

        db.close();
        return game;
    }

    public boolean deleteGameData(int id)
    {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_NAME_1 + " WHERE " + COL1 + "= '" + String.valueOf(id) + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Game game = new Game();

        if (cursor.moveToFirst())
        {
            game.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME_1, COL1 + "=?",
                    new String[]{String.valueOf(game.getID())});
            cursor.close();
            result = true;
        }

        cursor.close();
        db.close();

        return result;
    }


    /***TAG TABLE***/

    private void addPredefinedTags(SQLiteDatabase db, Tags tag)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, tag.getName());
        values.put(COL5, tag.getNotes());

        db.insert(TABLE_NAME_2, null, values);
    }

    public void addTagData(Tags tag)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, tag.getName());
        values.put(COL5, tag.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_2, null, values);
        db.close();
    }

    public String loadTagData()
    {
        String result = "";
        String query = "Select*FROM " + TABLE_NAME_2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            int result0 = cursor.getInt(0);
            //String result1 = cursor.getString(1);
            result += result0 + ";";
        }

        cursor.close();
        db.close();
        return result;
    }

    public void clearTagData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME_2);

        db.close();
    }

    public boolean updateTagData(int id, String name, String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, name);
        values.put(COL5, notes);

        return db.update(TABLE_NAME_2, values, COL1 + "=" + id, null) > 0;
    }

    // TODO - perhaps change parameter from tagname to id?
    public Tags fetchTagData(String id)
    {
        String query = "SELECT*FROM " + TABLE_NAME_2 + " WHERE " + COL1 + " = " + "?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{id});
        Tags tag = new Tags();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();

            tag.setID(cursor.getInt(0));
            tag.setName(cursor.getString(1));
            tag.setNotes(cursor.getString(2));

            cursor.close();
        }

        else
        {
            tag = null;
        }

        db.close();
        return tag;
    }

    public boolean deleteTagData(int id)
    {
        boolean result = false;
        String query = "SELECT*FROM " + TABLE_NAME_2 + " WHERE " + COL1 + "= '" + String.valueOf(id) + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Tags tag = new Tags();

        if (cursor.moveToFirst())
        {
            tag.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME_2, COL1 + "=?",
                    new String[]{String.valueOf(tag.getID())});
            cursor.close();
            result = true;
        }

        db.close();

        return result;
    }

}
