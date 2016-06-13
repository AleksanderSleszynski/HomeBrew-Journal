package com.example.julian.homebrewjournal.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Beer {

    public String uid;
    public String username;
    public String name;
    public String style;
    public double finalGravity;
    public double originalGravity;
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
                double finalGravity, double originalGravity, double beerVolume, double boilVolume) {
        this.uid = uid;
        this.username = username;
        this.name = mName;
        this.style = mStyle;
        this.finalGravity = finalGravity;
        this.originalGravity = originalGravity;
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
        result.put("finalGravity", finalGravity);
        result.put("originalGravity", originalGravity);
        result.put("beerVolume", beerVolume);
        result.put("boilVolume", boilVolume);

        return result;
    }

}
