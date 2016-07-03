package com.example.julian.homebrewjournal;

import android.widget.ImageView;

public class Utility {
    public static void setBeerImage(ImageView imageView, int beerImage){
        switch(beerImage){
            case 1: imageView.setImageResource(R.drawable.lager);
                break;
            case 2: imageView.setImageResource(R.drawable.stout);
                break;
            case 3: imageView.setImageResource(R.drawable.pale_ale);
                break;
        }
    }
}
