package com.example.julian.homebrewjournal.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Hop;
import com.example.julian.homebrewjournal.model.Malt;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name_item_ingredient_text_view)   TextView nameIngredientTextView;
    @BindView(R.id.weight_item_ingredient_text_view) TextView weightIngredientTextView;

    private String weight;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
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
