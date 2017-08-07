package com.example.android.absolutmixr.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Danny on 7/31/2017.
 */

public class IngredientsDBHelper extends SQLiteOpenHelper {
    //updated database to add done, then updated again to add category
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "items.db";
    private static final String TAG = "dbhelper";

    public IngredientsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + IngredientsContract.TABLE_INGREDIENTS.TABLE_NAME + " ("+
                IngredientsContract.TABLE_INGREDIENTS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_TYPE + " TEXT NOT NULL, " +
                IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_ID + " TEXT NOT NULL, " +
                IngredientsContract.TABLE_INGREDIENTS.COLUMN_NAME_NAME + " TEXT NOT NULL " +
                 "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + Contract.TABLE_TODO.TABLE_NAME + "");
        //onCreate(db);
    }
}
