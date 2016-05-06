package com.example.julian.homebrewjournal;

import com.firebase.client.Firebase;

/**
 * Created by julain on 02.05.16.
 */
public class HomeBrewApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
