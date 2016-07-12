package com.example.julian.homebrewjournal.model;

public class Hop {

    public String uid;
    public String name;
    public double weight;

    public Hop(){

    }

    public Hop(String uid, String name, double weight){
        this.uid = uid;
        this.name = name;
        this.weight = weight;
    }
}
