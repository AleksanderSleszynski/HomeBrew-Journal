package com.example.julian.homebrewjournal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.julian.homebrewjournal.data.BeerContract.BeerEntry;
import com.example.julian.homebrewjournal.model.Beer;

import java.util.ArrayList;
import java.util.List;

public class BeerDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "beer";
    private static BeerDbHelper sInstance;

    private static final String SQL_CREATE_BEER_TABLE = "CREATE TABLE " + BeerEntry.TABLE_NAME + " ( " +
            BeerEntry.COLUMN_BEER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BeerEntry.COLUMN_BEER_NAME + " TEXT, " +
            BeerEntry.COLUMN_BEER_STYLE + " TEXT, " +
            BeerEntry.COLUMN_BEER_OG + " INTEGER " +
//            BeerEntry.COLUMN_BEER_FG + " INTEGER, " +
//            BeerEntry.COLUMN_BEER_VOLUME + " INTEGER, " +
//            BeerEntry.COLUMN_BOIL_VOLUME + " INTEGER, " +
//            BeerEntry.COLUMN_BEER_ICON + " INTEGER, " +
//            BeerEntry.COLUMN_HOPS + " TEXT, " +
//            BeerEntry.COLUMN_MALTS + " TEXT, " +
//            BeerEntry.COLUMN_OTHER + " TEXT, " +
//            BeerEntry.COLUMN_MASHING + " TEXT, " +
//            BeerEntry.COLUMN_BOILING + " TEXT, " +
//            BeerEntry.COLUMN_FERMENTING + " TEXT, " +
//            BeerEntry.COLUMN_BOTTELING + " TEXT " +
            " );";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + BeerEntry.TABLE_NAME;

    public BeerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_BEER_TABLE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Adding new Beer
    public void addBeer(Beer beer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BeerEntry.COLUMN_BEER_NAME, beer.getName());
        values.put(BeerEntry.COLUMN_BEER_STYLE, beer.getStyle());

        db.insert(BeerEntry.TABLE_NAME, null, values);
        db.close();
    }

    // Getting single beer
    public Beer getBeer(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(BeerEntry.TABLE_NAME,
                new String[]{ BeerEntry.COLUMN_BEER_ID, BeerEntry.COLUMN_BEER_NAME, BeerEntry.COLUMN_BEER_STYLE},
                BeerEntry.COLUMN_BEER_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        Beer beer = new Beer(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        cursor.close();
        return beer;
    }

    // Getting All Beers
    public List<Beer> getAllBeers(){
        List<Beer> beerList = new ArrayList<Beer>();
        //Select All Query
        String selectQuery = "SELECT * FROM " + BeerEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Beer beer = new Beer();
                beer.setId(Integer.parseInt(cursor.getString(0)));
                beer.setName(cursor.getString(1));
                beer.setStyle(cursor.getString(2));
                // Adding beer to list
                beerList.add(beer);
            } while (cursor.moveToNext());
        }
        return beerList;
    }

    // Getting beers Count
    public int getBeerCount(){
        String beerQuery = "SELECT * FROM " + BeerEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(beerQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single beer
    public int updateBeer(Beer beer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BeerEntry.COLUMN_BEER_NAME, beer.getName());
        values.put(BeerEntry.COLUMN_BEER_STYLE, beer.getStyle());

        return db.update(BeerEntry.TABLE_NAME, values, BeerEntry.COLUMN_BEER_ID + " = ? ",
                new String[] {String.valueOf(beer.getId())});
    }

    // Deleting single beer
    public void deleteBeer(Beer beer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BeerEntry.TABLE_NAME, BeerEntry.COLUMN_BEER_ID + " = ?",
                new String[]{String.valueOf(beer.getId())});
        db.close();
    }

    public static synchronized BeerDbHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new BeerDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }
}
