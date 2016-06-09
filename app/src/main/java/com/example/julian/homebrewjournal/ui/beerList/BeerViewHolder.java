package com.example.julian.homebrewjournal.ui.beerList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Beer;


public class BeerViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView styleTextView;

    public BeerViewHolder(View itemView) {
        super(itemView);

        styleTextView = (TextView) itemView.findViewById(R.id.text_view_beer_style);
        nameTextView = (TextView) itemView.findViewById(R.id.text_view_beer_name);
    }


    public void bindToBeer(Beer beer){
        nameTextView.setText(beer.name);
        styleTextView.setText(beer.style);
    }
}
