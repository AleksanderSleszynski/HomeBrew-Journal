package com.example.julian.homebrewjournal.ui.beerList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;


public class BeerViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;

    public BeerViewHolder(View itemView) {
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.text_view_beer_name);
    }
}
