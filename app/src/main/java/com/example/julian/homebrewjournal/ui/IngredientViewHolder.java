package com.example.julian.homebrewjournal.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Hop;
import com.example.julian.homebrewjournal.model.Malt;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    public TextView nameIngredientTextView;
    public TextView weightIngredientTextView;
    private String weight;

    public IngredientViewHolder(View itemView) {
        super(itemView);

        nameIngredientTextView = (TextView) itemView.findViewById(R.id.name_item_hop_text_View);
        weightIngredientTextView = (TextView) itemView.findViewById(R.id.weight_item_hop_text_View);
    }

    public void bindToHop(Hop hop){
        nameIngredientTextView.setText(hop.name);
        weight = String.valueOf(hop.weight);
        weightIngredientTextView.setText(weight);
    }

    public void bindToMalt(Malt malt){
        nameIngredientTextView.setText(malt.name);
        weight = String.valueOf(malt.weight);
        weightIngredientTextView.setText(weight);
    }

}
