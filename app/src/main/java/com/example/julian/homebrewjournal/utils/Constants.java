package com.example.julian.homebrewjournal.utils;


import com.example.julian.homebrewjournal.BuildConfig;

public class Constants {


    /**
     * Constants related to locations n Firebase, such as the name of the node
     * where active list are stored
     */
    public static final String FIREBASE_LOCATION_BEERS = "beers";

    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_BEER_NAME = "beerName";


    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_BEER_LIST = FIREBASE_URL + "/" + FIREBASE_LOCATION_BEERS;


}
