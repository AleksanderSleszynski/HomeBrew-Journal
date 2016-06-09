package com.example.julian.homebrewjournal.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Beer {

    public String uid;
    public String username;
    public String name;
    public String style;
    public double FG;
    public double OG;
    public double beerVolume;
    public double boilVolume;

//    private String hops;
//    private String malts;
//    private String other;
//    private String mashing;
//    private String boiling;
//    private String fermenting;
//    private String botteling;

    // Constructors

    public Beer() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Beer(String uid, String username, String mName, String mStyle,
                double FG, double OG, double beerVolume, double boilVolume) {
        this.uid = uid;
        this.username = username;
        this.name = mName;
        this.style = mStyle;
        this.FG = FG;
        this.OG = OG;
        this.beerVolume = beerVolume;
        this.boilVolume = boilVolume;
    }

    // Beer to map
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("name", name);
        result.put("style", style);
        result.put("fg", FG);
        result.put("og", OG);
        result.put("beerVolume", beerVolume);
        result.put("boilVolume", boilVolume);

        return result;
    }

}
