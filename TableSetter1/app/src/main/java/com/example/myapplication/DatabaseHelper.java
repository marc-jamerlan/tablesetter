package com.example.myapplication;

import android.content.Context;
import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //TODO - add database methods for Player class

    private static final String DATABASE_NAME = "TableSetterDatabase";

    public static final String GAME_TABLE = "GameTable";
    public static final String TAG_TABLE = "TagTable";
    public static final String PLAYER_TABLE = "PlayerTable";

    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "IMAGE";
    public static final String COL4A = "GAMELIST";
    public static final String COL4B = "TAGLIST";
    public static final String COL5 = "NOTES";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 8);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE1 = "CREATE TABLE " + GAME_TABLE + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT," + COL3 + " TEXT," + COL4B + " TEXT," + COL5 + " TEXT);";
        db.execSQL(CREATE_TABLE1);

        String CREATE_TABLE2 = "CREATE TABLE " + TAG_TABLE + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT," + COL5 + " TEXT);";
        db.execSQL(CREATE_TABLE2);

        String CREATE_TABLE3 = "CREATE TABLE " + PLAYER_TABLE + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT," + COL3 + " TEXT," + COL4A + " TEXT," + COL4B + " TEXT," + COL5 + " TEXT);";
        db.execSQL(CREATE_TABLE3);

        addPredefinedTags(db, new Tag("Family Fun", "Fun for family and friends of all ages."));
        addPredefinedTags(db, new Tag("Competitive", "For those who feel the need to win."));
        addPredefinedTags(db, new Tag("Military", "Command and conquer, soldier."));
        addPredefinedTags(db, new Tag("Fantasy", "An adventure from time immemorial."));
    }


    /***GAME TABLE METHODS***/


    public void addGameData(Game game)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, game.getName());
        values.put(COL3, game.getGameImage());
        String tagArrayString = game.getTagIDListString();
        values.put(COL4B, tagArrayString);
        values.put(COL5, game.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(GAME_TABLE, null, values);
        db.close();
    }

    public String loadGameData()
    {
        String result = "";
        String query = "Select*FROM " + GAME_TABLE;
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

        db.execSQL("DELETE FROM " + GAME_TABLE);

        db.close();
    }

    public boolean updateGameData(Game game)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, game.getName());
        values.put(COL3, game.getGameImage());
        String tagArrayString = game.getTagIDListString();
        values.put(COL4B, tagArrayString);
        values.put(COL5, game.getNotes());

        return db.update(GAME_TABLE, values, COL1 + "=" + game.getID(), null) > 0;
    }

    public Game fetchGameData(String gameName)
    {
        String query = "SELECT*FROM " + GAME_TABLE + " WHERE " + COL2 + " = " + "?";
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
            if (tagArrayString != null)
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
            game.setTagIDList(tagIDList);
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
        String query = "SELECT * FROM " + GAME_TABLE + " WHERE " + COL1 + "= '" + String.valueOf(id) + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Game game = new Game();

        if (cursor.moveToFirst())
        {
            game.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(GAME_TABLE, COL1 + "=?",
                    new String[]{String.valueOf(game.getID())});
            cursor.close();
            result = true;
        }

        cursor.close();
        db.close();

        return result;
    }


    /***TAG TABLE METHODS***/


    private void addPredefinedTags(SQLiteDatabase db, Tag tag)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, tag.getName());
        values.put(COL5, tag.getNotes());

        db.insert(TAG_TABLE, null, values);
    }

    public void addTagData(Tag tag)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, tag.getName());
        values.put(COL5, tag.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TAG_TABLE, null, values);
        db.close();
    }

    public String loadTagData()
    {
        String result = "";
        String query = "Select*FROM " + TAG_TABLE;
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

        db.execSQL("DELETE FROM " + TAG_TABLE);

        db.close();
    }

    public boolean updateTagData(int id, String name, String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, name);
        values.put(COL5, notes);

        return db.update(TAG_TABLE, values, COL1 + "=" + id, null) > 0;
    }

    public Tag fetchTagData(String id)
    {
        String query = "SELECT*FROM " + TAG_TABLE + " WHERE " + COL1 + " = " + "?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{id});
        Tag tag = new Tag();

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
        String query = "SELECT*FROM " + TAG_TABLE + " WHERE " + COL1 + "= '" + String.valueOf(id) + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Tag tag = new Tag();

        if (cursor.moveToFirst())
        {
            tag.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TAG_TABLE, COL1 + "=?",
                    new String[]{String.valueOf(tag.getID())});
            cursor.close();
            result = true;
        }

        db.close();

        return result;
    }


    /***PLAYER TABLE METHODS***/


    public void addPlayer(Player player)
    {
        ContentValues values = new ContentValues();
        values.put(COL2, player.getName());
        values.put(COL3, player.getPlayerImage());
        String listString = player.getGameNameListString();
        values.put(COL4B, listString);
        listString = player.getTagIDListString();
        values.put(COL4B, listString);
        values.put(COL5, player.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(PLAYER_TABLE, null, values);
        db.close();
    }

    public String loadPlayerData()
    {
        String result = "";
        String query = "Select*FROM " + PLAYER_TABLE;
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

    public void clearPlayerData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + PLAYER_TABLE);

        db.close();
    }

    public boolean updatePlayerData(Player player)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, player.getName());
        values.put(COL3, player.getPlayerImage());
        String listString = player.getGameNameListString();
        values.put(COL4B, listString);
        listString = player.getTagIDListString();
        values.put(COL4B, listString);
        values.put(COL5, player.getNotes());

        return db.update(PLAYER_TABLE, values, COL1 + "=" + player.getID(), null) > 0;
    }

    public Player fetchPlayerData(String id)
    {
        String query = "SELECT*FROM " + PLAYER_TABLE + " WHERE " + COL1 + " = " + "?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{id});
        Player player = new Player();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();

            player.setID(cursor.getInt(0));
            player.setName(cursor.getString(1));
            player.setPlayerImage(cursor.getString(2));

            String gameNameListString = cursor.getString(3);
            ArrayList<String> gameNameList = new ArrayList<>();

            if(gameNameListString != null)
            {
                String[] gameNameArray = gameNameListString.split(";");
                List<String> newList = Arrays.asList(gameNameArray);
                gameNameList.addAll(newList);
            }
            player.setGameNameList(gameNameList);

            String tagListString = cursor.getString(4);
            ArrayList<Integer> tagIDList = new ArrayList<>();
            if (tagListString != null)
            {
                String[] tagArray = tagListString.split(";");

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
            player.setTagIDList(tagIDList);
            player.setNotes(cursor.getString(5));

            cursor.close();
        }

        else
        {
            player = null;
        }

        db.close();
        return player;
    }

    public boolean deletePlayerData(int id)
    {
        boolean result = false;
        String query = "SELECT * FROM " + PLAYER_TABLE + " WHERE " + COL1 + "= '" + String.valueOf(id) + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Player player = new Player();

        if (cursor.moveToFirst())
        {
            player.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(PLAYER_TABLE, COL1 + "=?",
                    new String[]{String.valueOf(player.getID())});
            cursor.close();
            result = true;
        }

        cursor.close();
        db.close();

        return result;
    }
}