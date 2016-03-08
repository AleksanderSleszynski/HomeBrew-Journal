package com.example.julian.homebrewjournal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.julian.homebrewjournal.data.BeerContract.BeerEntry;

public class BeerDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Beer.db";

    private static final String SQL_BEER_TABLE = "CREATE TABLE " + BeerEntry.TABLE_NAME + " (" +
            BeerEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            BeerEntry.COLUMN_BEER_ID + " TEXT, " +
            BeerEntry.COLUMN_BEER_NAME + " TEXT, " +
            BeerEntry.COLUMN_BEER_STYLE + "TEXT, " +
            BeerEntry.COLUMN_BEER_OG + " INTEGER, " +
            BeerEntry.COLUMN_BEER_FG + " INTEGER, " +
            BeerEntry.COLUMN_BEER_VOLUME + " INTEGER, " +
            BeerEntry.COLUMN_BOIL_VOLUME + " INTEGER, " +
            BeerEntry.COLUMN_BEER_ICON + " INTEGER, " +
            BeerEntry.COLUMN_HOPS + " TEXT, " +
            BeerEntry.COLUMN_MALTS + " TEXT, " +
            BeerEntry.COLUMN_OTHER + " TEXT, " +
            BeerEntry.COLUMN_MASHING + " TEXT, " +
            BeerEntry.COLUMN_BOILING + " TEXT, " +
            BeerEntry.COLUMN_FERMENTING + " TEXT, " +
            BeerEntry.COLUMN_BOTTELING + " TEXT, "
            + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + BeerEntry.TABLE_NAME;

    public BeerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_BEER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
