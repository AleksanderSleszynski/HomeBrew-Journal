package com.example.julian.homebrewjournal.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.ui.beerList.AddBeerDialogFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Link layout elements from XML and setup the toolbar
         */
        initializeScreen();
    }

    public void initializeScreen() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }


    /**
     * Create an instance of the AddBeer Dialog fragment and show it
     */
    public void showAddBeerDialog() {
        DialogFragment dialog  = AddBeerDialogFragment.newInstance();
        dialog.show(MainActivity.this.getSupportFragmentManager(), "AddBeerDialogFragment");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
