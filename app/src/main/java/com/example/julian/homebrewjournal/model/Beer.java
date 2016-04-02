package com.example.julian.homebrewjournal.model;

import java.util.ArrayList;

public class Beer {
    private String mName;
    private String mStyle;

//    private double FG;
//    private double OG;
//    private int id;
//    private double beerVolume;
//    private double boilVolume;
//    private String hops;
//    private String malts;
//    private String other;
//    private String mashing;
//    private String boiling;
//    private String fermenting;
//    private String botteling;


    public Beer(String name, String style) {
        mName = name;
        mStyle = style;
    }

    public Beer() {
        mName = "Name";
        mStyle = "Style";
    }

    public String getName() {
        return mName;
    }

    public String getStyle() {
        return mStyle;
    }

    // This is snippet i need to check what it will exactly do in longer term.

    private static int lastBeerId = 0;

    public static ArrayList<Beer> createBeerList(int numBeer){
        ArrayList<Beer> beers = new ArrayList<Beer>();

        for(int i = 1; i <= numBeer; i++){
            beers.add(new Beer("Beer" + ++lastBeerId, "" + i));
        }
        return beers;
    }

}
