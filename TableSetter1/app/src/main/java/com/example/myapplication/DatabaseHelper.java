package com.example.myapplication;

import android.content.Context;
import android.content.ContentValues;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "TableSetterDatabase";

    public static final String TABLE_NAME_1 = " GameTable";
    public static final String TABLE_NAME_2 = " TagTable";

    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "IMAGE";
    public static final String COL4 = "TAGLIST";
    public static final String COL5 = "NOTES";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 5);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE1 = "CREATE TABLE " + TABLE_NAME_1 + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT," + COL3 + " BLOB," + COL4 + " TEXT," + COL5 + " TEXT);";
        db.execSQL(CREATE_TABLE1);

        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME_2 + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT," + COL5 + " TEXT);";
        db.execSQL(CREATE_TABLE2);

    }


    /***GAME TABLE***/


    public void addGameData(Game game)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, game.getName());
        values.put(COL3, game.getGameImage());

        //TODO - convert tag array into data that can be pushed into db

        values.put(COL5, game.getNotes());

        //TODO - place more info in Tags and Game classes, put into database here and method below

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

    public boolean updateGameData(int id, String name, int image, String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, name);
        values.put(COL3, image);

        //tag data needs updating

        values.put(COL5, notes);

        return db.update(TABLE_NAME_1, values, COL1 + "=" + id, null) > 0;
    }


    //TODO
    public Game fetchGameData(String gameName)
    {
        String query = "SELECT*FROM" + TABLE_NAME_1 + " WHERE " + COL2 + " = " + "?";
        /*String query = "SELECT*FROM" + TABLE_NAME_1 + " WHERE " + COL2 + " = " + "'"
            + gameName + "'";*/
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{gameName});
        Game game = new Game();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();

            game.setID(Integer.parseInt(cursor.getString(0)));
            game.setName(cursor.getString(1));

            //TODO - fetch images and taglist

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
        String query = "SELECT * FROM" + TABLE_NAME_1 + " WHERE " + COL1 + "= '" + String.valueOf(id) + "'";

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

        db.close();

        return result;
    }


    /***TAG TABLE***/


    public void addTagData(Tags tag)
    {
        ContentValues values = new ContentValues();
        values.put(COL1, tag.getID());
        values.put(COL2, tag.getName());
        values.put(COL5, tag.getNotes());

        //TODO - see addGameData() above

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_2, null, values);
        db.close();
    }

    //TODO - implement loadTagData() and clearTagData
    public String loadTagData()
    {
        return null;
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
        values.put(COL1, id);
        values.put(COL2, name);
        values.put(COL5, notes);

        return db.update(TABLE_NAME_2, values, COL1 + "=" + id, null) > 0;
    }

    // TODO - perhaps change parameter from tagname to id?
    public Tags fetchTagData(String tagName)
    {
        String query = "SELECT*FROM" + TABLE_NAME_2 + "WHERE" + COL2 + " = " + "'"
                + tagName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Tags tag = new Tags();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();

            tag.setID(Integer.parseInt(cursor.getString(0)));
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
        String query = "SELECT*FROM" + TABLE_NAME_2 + "WHERE" + COL1 + "= '" + String.valueOf(id) + "'";

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
