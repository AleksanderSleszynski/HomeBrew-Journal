package com.example.julian.homebrewjournal.model;


public class Malt {

    public String uid;
    public String name;
    public double weight;

    public Malt(){

    }

    public Malt(String uid, String name, double weight){
        this.uid = uid;
        this.name = name;
        this.weight = weight;
    }
}
