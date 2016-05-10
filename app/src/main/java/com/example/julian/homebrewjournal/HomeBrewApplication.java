package com.example.julian.homebrewjournal;

import com.firebase.client.Firebase;

public class HomeBrewApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
