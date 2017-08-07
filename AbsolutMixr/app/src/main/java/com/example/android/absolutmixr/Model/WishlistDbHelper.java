package com.example.android.absolutmixr.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by melaniekwon on 8/6/17.
 */

public class WishlistDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "wishlist.db";
    static final int DATABASE_VERSION = 2;

    public WishlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " +
                WishlistContract.WishlistEntry.TABLE_NAME + " (" +
                WishlistContract.WishlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WishlistContract.WishlistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                WishlistContract.WishlistEntry.COLUMN_DESCRIPTION + " TEXT," +
                WishlistContract.WishlistEntry.COLUMN_COLOR + " TEXT," +
                WishlistContract.WishlistEntry.COLUMN_SKILL + " TEXT," +
                WishlistContract.WishlistEntry.COLUMN_RATING + " TEXT," +
                WishlistContract.WishlistEntry.COLUMN_PICTURE_URL + " TEXT" +
                ");";

        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WishlistContract.WishlistEntry.TABLE_NAME);
        onCreate(db);
    }
}
