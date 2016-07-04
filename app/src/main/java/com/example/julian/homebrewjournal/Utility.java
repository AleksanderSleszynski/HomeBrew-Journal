package com.example.julian.homebrewjournal;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utility {
    public static void setBeerImage(ImageView imageView, int beerImage){
        switch(beerImage){
            case 1: Picasso.with(imageView.getContext()).load(R.drawable.lager).into(imageView);
                break;
            case 2: Picasso.with(imageView.getContext()).load(R.drawable.stout).into(imageView);
                break;
            case 3: Picasso.with(imageView.getContext()).load(R.drawable.pale_ale).into(imageView);
                break;
            case 4: Picasso.with(imageView.getContext()).load(R.drawable.wheat).into(imageView);
                break;
        }
    }
}
