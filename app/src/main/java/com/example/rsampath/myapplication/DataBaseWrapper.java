package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/6/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseWrapper extends SQLiteOpenHelper {

    public static final String ITEMS = "Items";
    public static final String ITEM_ID = "_id";
    public static final String ITEM_NAME = "_name";
    public static final String ITEM_PRICE = "_price";

    private static final String DATABASE_NAME = "Items.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "create table " + ITEMS
            + "(" + ITEM_ID + " integer primary key autoincrement, "
            + ITEM_NAME + " text not null, "
            + ITEM_PRICE + " text not null);"
            ;

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you should do some logging in here
        // ..

        db.execSQL("DROP TABLE IF EXISTS " + ITEMS);
        onCreate(db);
    }

}