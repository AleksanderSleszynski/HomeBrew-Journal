package com.example.julian.homebrewjournal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.ui.beerList.AddBeerDialogFragment;
import com.example.julian.homebrewjournal.ui.beerList.BeerListActivityFragment;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBeerDialog();
            }
        });
        BeerListActivityFragment.newInstance();
    }


    /**
     * Create an instance of the AddBeer Dialog fragment and show it
     */
    public void showAddBeerDialog(/*View view*/) {
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
