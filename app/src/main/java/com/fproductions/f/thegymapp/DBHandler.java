package com.fproductions.f.thegymapp;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo_2 on 10/2/2014.
 */
public class DBHandler extends SQLiteOpenHelper {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "GymDB";
    private static final String DATABASE_TABLE = "Members";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "(" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " VARCHAR not null," + KEY_EMAIL + " VARCHAR not null)";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        onCreate(db);
    }

    public void insertMember(String name, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        db.insert(DATABASE_TABLE, null, values);
    }
    public void deleteMember(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_EMAIL + " = ?", new String[] {String.valueOf(email)});

    }

    public List<String> getAllEmail(){
        List<String> email = new ArrayList<String>();
        String query = "SELECT email FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                email.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return email;
    }

    public boolean CheckIsEmailRegistered(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * from " + DATABASE_TABLE + " WHERE " + KEY_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()<= 0){
            return false;
        }
        return true;
    }
    public String getNameFromEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select name from " + DATABASE_TABLE + " WHERE " + KEY_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.getString(cursor.getColumnIndex("name"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        name.equals(cursor);
        return name;
    }


}
