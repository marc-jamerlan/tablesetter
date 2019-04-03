package com.example.myapplication;

import android.content.Context;
import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

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
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE1 = "CREATE TABLE" + TABLE_NAME_1 + "(" + COL1 + "INTEGER PRIMARYKEY,"
                + COL2 + "TEXT," + COL3 + "BLOB," + COL4 + "TEXT," + COL5 + "TEXT )";
        db.execSQL(CREATE_TABLE1);

        String CREATE_TABLE2 = "CREATE TABLE" + TABLE_NAME_2 + "(" + COL1 + "INTEGER PRIMARYKEY,"
                + COL2 + "TEXT" + COL5 + "TEXT )";
        db.execSQL(CREATE_TABLE2);

    }

    public void addGameData(Game game)
    {
        ContentValues values = new ContentValues();
        values.put(COL1, game.getID());
        values.put(COL2, game.getName());
        values.put(COL3, game.getGameImage());

        //TODO - convert tag array into data that can be pushed into db

        values.put(COL5, game.getNotes());

        //TODO - place more info in Tags and Game classes, put into database here and method below

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_1, null, values);
        db.close();
    }

    public boolean updateGameData(int id, String name, int image, String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, id);
        values.put(COL2, name);
        values.put(COL3, image);

        //tag data needs updating

        values.put(COL5, notes);

        return db.update(TABLE_NAME_1, values, COL1 +  "=" + id, null) > 0;
    }

    public void addTagData(Tags tag)
    {
        ContentValues values = new ContentValues();
        values.put(COL1, tag.getID());
        values.put(COL2, tag.getName());
        values.put(COL5, tag.getNotes());

        //TODO - see above

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_2, null, values);
        db.close();
    }

    public boolean updateTagData(int id, String name, String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, id);
        values.put(COL2, name);
        values.put(COL5, notes);

        return db.update(TABLE_NAME_2, values, COL1 +  "=" + id, null) > 0;
    }

}
