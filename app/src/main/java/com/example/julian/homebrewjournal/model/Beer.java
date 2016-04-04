package com.example.julian.homebrewjournal.model;

public class Beer {
    private int mId;
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


    public Beer(int id, String name, String style) {
        mId = id;
        mName = name;
        mStyle = style;
    }

    public Beer() {
        mName = "Name";
        mStyle = "Style";
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getStyle() {
        return mStyle;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setStyle(String mStyle) {
        this.mStyle = mStyle;
    }

    // This is snippet i need to check what it will exactly do in longer term.

    private static int lastBeerId = 0;

//
//    public static ArrayList<Beer> createBeerList(int numBeer){
//        ArrayList<Beer> beers = new ArrayList<Beer>();
//
//        for(int i = 1; i <= numBeer; i++){
//            beers.add(new Beer("Beer" + ++lastBeerId, "" + i));
//        }
//        return beers;
//    }

}
