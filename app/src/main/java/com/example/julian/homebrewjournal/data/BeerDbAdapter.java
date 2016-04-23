package com.example.julian.homebrewjournal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.julian.homebrewjournal.data.BeerContract.BeerEntry;

public class BeerDbAdapter {
    Context context;

    SQLiteDatabase db;
    BeerDbHelper helper;


    public BeerDbAdapter(Context context) {
        this.context = context;
        helper = new BeerDbHelper(context);
    }

    public BeerDbAdapter openDB(){
        try{
            db = helper.getWritableDatabase();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return this;
    }

    public void closeDB(){
        try{
            helper.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public long add(String name, String style){

        try{
            ContentValues cv = new ContentValues();
            cv.put(BeerContract.BeerEntry.COLUMN_BEER_NAME, name);
            cv.put(BeerContract.BeerEntry.COLUMN_BEER_STYLE, style);

            return db.insert(BeerEntry.TABLE_NAME, BeerEntry.COLUMN_BEER_ID, cv);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    public Cursor getBeerList(){
        String[] columns = {BeerEntry.COLUMN_BEER_ID, BeerEntry.COLUMN_BEER_NAME,
                BeerEntry.COLUMN_BEER_STYLE};

        return db.query(BeerEntry.TABLE_NAME, columns, null, null, null, null, null);
    }

    public long update(int id, String name, String style){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(BeerEntry.COLUMN_BEER_NAME, name);
            contentValues.put(BeerEntry.COLUMN_BEER_STYLE, style);

            return db.update(BeerEntry.TABLE_NAME, contentValues, BeerEntry.COLUMN_BEER_ID + " =?",
                    new String[]{String.valueOf(id)});
        } catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public long delete (int id){
        try{
            return db.delete(BeerEntry.TABLE_NAME, BeerEntry.COLUMN_BEER_ID + " =?",
                    new String[]{String.valueOf(id)});
        } catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

}
