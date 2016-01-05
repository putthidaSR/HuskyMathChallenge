package edu.uw.team5.huskymathchallenge.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lebui on 12/5/2015.
 */
public class MyDBAlgebra extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "score.db";
    public static final String TABLE_SCORE = "scores";

    public static final String COLUMN_ID = "_score";
    public static final String COLUMN_USERNAME = "username";

    public MyDBAlgebra(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_SCORE + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    //Add a new row to the database
    public void addUser(User username){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username.getUsername());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SCORE, null, values);
        db.close();
    }

    //Print out the database as a string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +TABLE_SCORE + " WHERE 1;";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("username"))!= null){
                dbString += c.getString(c.getColumnIndex("username"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }

}
