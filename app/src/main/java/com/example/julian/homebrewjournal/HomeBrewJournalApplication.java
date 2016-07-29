package com.example.julian.homebrewjournal;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class HomeBrewJournalApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
