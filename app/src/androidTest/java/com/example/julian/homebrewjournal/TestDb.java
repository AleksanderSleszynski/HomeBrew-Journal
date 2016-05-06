package com.example.julian.homebrewjournal;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import com.example.julian.homebrewjournal.data.BeerContract;
import com.example.julian.homebrewjournal.data.BeerDbHelper;

import java.util.HashSet;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestDb extends ApplicationTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    void deleteTheDatabase(){
        mContext.deleteDatabase(BeerDbHelper.DATABASE_NAME);
    }

    public void setUp(){
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable{
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(BeerContract.BeerEntry.TABLE_NAME);

        mContext.deleteDatabase(BeerDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new BeerDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not beed created correctly",
                c.moveToFirst());

        // verify that the tables have been created
//        tableNameHashSet.remove(c.getString(0));
//        assertTrue("Error: Your database was created without entry table", tableNameHashSet.isEmpty());


        c = db.rawQuery("PRAGMA table_info(" + BeerContract.BeerEntry.TABLE_NAME + ")", null);
        assertTrue("Error: this means that we were unable to query the database for thable information.",
                c.moveToFirst());

        final HashSet<String> beerColumnHasSet = new HashSet<String>();
        beerColumnHasSet.add(BeerContract.BeerEntry.COLUMN_BEER_ID);
        beerColumnHasSet.add(BeerContract.BeerEntry.COLUMN_BEER_NAME);
        beerColumnHasSet.add(BeerContract.BeerEntry.COLUMN_BEER_STYLE);

        int columnNameIndex = c.getColumnIndex("name");
        do{
            String columnName = c.getString(columnNameIndex);
            beerColumnHasSet.remove(columnName);
        } while(c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required beer entry columns",
                beerColumnHasSet.isEmpty());
        db.close();
    }


    public TestDb() {
        super(Application.class);
    }
}