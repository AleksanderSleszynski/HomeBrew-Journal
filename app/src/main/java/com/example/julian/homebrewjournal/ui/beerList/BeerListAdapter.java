package com.example.julian.homebrewjournal.ui.beerList;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Beer;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

public class BeerListAdapter extends FirebaseListAdapter<Beer> {

    public BeerListAdapter(Activity activity, Class<Beer> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, Beer beer, int i) {

        /* Grab the needed TextViews and strings */
        TextView textViewBeerName = (TextView) view.findViewById(R.id.text_view_beer_name);
        TextView textViewBeerStyle = (TextView) view.findViewById(R.id.text_view_beer_style);

        /* Set the list name and style */
        textViewBeerName.setText(beer.getName());
        textViewBeerStyle.setText(beer.getStyle());
    }
}
