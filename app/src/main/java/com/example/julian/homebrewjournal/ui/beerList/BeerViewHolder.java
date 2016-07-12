package com.example.julian.homebrewjournal.ui.beerList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.Utility;
import com.example.julian.homebrewjournal.model.Beer;


public class BeerViewHolder extends RecyclerView.ViewHolder {

    TextView nameBeerTextView;
    TextView styleBeerTextView;

    ImageView beerImageView;

    public BeerViewHolder(View itemView) {
        super(itemView);

        styleBeerTextView = (TextView) itemView.findViewById(R.id.text_view_beer_style);
        nameBeerTextView = (TextView) itemView.findViewById(R.id.text_view_beer_name);

        beerImageView   = (ImageView) itemView.findViewById(R.id.logo);
    }


    public void bindToBeer(Beer beer){
        nameBeerTextView.setText(beer.name);
        styleBeerTextView.setText(beer.style);
        Utility.setBeerImage(beerImageView, beer.beerImage);
    }
}
