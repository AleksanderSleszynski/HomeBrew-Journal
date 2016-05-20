package com.example.julian.homebrewjournal.model;

public class Beer {
    private String mName;
    private String mStyle;
    private double FG;
    private double OG;
    private double beerVolume;
    private double boilVolume;

//    private String hops;
//    private String malts;
//    private String other;
//    private String mashing;
//    private String boiling;
//    private String fermenting;
//    private String botteling;

    public Beer() {}

    public Beer(String mName) {
        this.mName = mName;
        mStyle = "IPA";
        FG = 1;
        OG = 1;
        beerVolume = 20;
        boilVolume = 25;
    }

    public Beer(String mName, String mStyle, double FG, double OG, double beerVolume, double boilVolume) {
        this.mName = mName;
        this.mStyle = mStyle;
        this.FG = FG;
        this.OG = OG;
        this.beerVolume = beerVolume;
        this.boilVolume = boilVolume;
    }

    public String getName() {
        return mName;
    }

    public String getStyle() {
        return mStyle;
    }

    public double getFG() {
        return FG;
    }

    public double getOG() {
        return OG;
    }

    public double getBeerVolume() {
        return beerVolume;
    }

    public double getBoilVolume() {
        return boilVolume;
    }
}
