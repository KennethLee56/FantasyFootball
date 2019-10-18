package com.example.fantasyfootball;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "player.db";

    private static final String TABLE_PLAYER_LIST = "playerlist";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_NAME = "name";
    private static final String COLUMN_LIST_POSITION = "position";
    private static final String COLUMN_LIST_TEAM = "team";

    public DBHandler (Context context, SQLiteDatabase.CursorFactory cursorFactory){
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_PLAYER_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_POSITION + " TEXT, " +
                COLUMN_LIST_TEAM + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_LIST);
        onCreate(sqLiteDatabase);
    }

    public  void addPlayerList(String name, String position, String team){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_POSITION, position);
        values.put(COLUMN_LIST_TEAM, team);

        db.insert(TABLE_PLAYER_LIST, null, values);
        db.close();
    }

    public Cursor getPlayerLists(){
        SQLiteDatabase db = getWritableDatabase();

        //select all data from shoppinglist table and return it as a cursor
        return db.rawQuery("SELECT * FROM " + TABLE_PLAYER_LIST, null);
    }
}
