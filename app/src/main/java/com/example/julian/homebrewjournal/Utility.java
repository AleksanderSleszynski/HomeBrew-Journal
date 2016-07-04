package com.example.julian.homebrewjournal;

import android.content.res.Resources;
import android.util.TypedValue;
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

    public static int convertPx(int number, Resources resources){
        return (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, number, resources.getDisplayMetrics());
    }
}
