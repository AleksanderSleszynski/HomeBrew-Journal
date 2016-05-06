package com.example.julian.homebrewjournal.data;


import android.provider.BaseColumns;

public class BeerContract {

    public BeerContract(){}

    public static abstract class BeerEntry implements BaseColumns{
        public static final String TABLE_NAME = "beer";
        public static final String COLUMN_BEER_ID = "id";
        public static final String COLUMN_BEER_NAME = "name";
        public static final String COLUMN_BEER_STYLE = "style";
        public static final String COLUMN_BEER_OG = "og";
        public static final String COLUMN_BEER_FG = "fg";
        public static final String COLUMN_BEER_VOLUME = "beervolume";
        public static final String COLUMN_BOIL_VOLUME = "boilvolume";
        public static final String COLUMN_BEER_ICON = "boilvolume";
        public static final String COLUMN_HOPS = "hop";
        public static final String COLUMN_MALTS = "malt";
        public static final String COLUMN_OTHER = "other";
        public static final String COLUMN_MASHING = "mash";
        public static final String COLUMN_BOILING = "boil";
        public static final String COLUMN_FERMENTING = "ferment";
        public static final String COLUMN_BOTTELING = "bottle";
    }
}
