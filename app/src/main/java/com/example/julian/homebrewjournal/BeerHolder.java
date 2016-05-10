package com.example.julian.homebrewjournal;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BeerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nameTextView;
    public TextView styleTextView;
    private Context mContext;

    public BeerHolder (Context context, View itemView){
        super(itemView);
        nameTextView = (TextView) itemView.findViewById(R.id.item_beer_name_textView);
        styleTextView = (TextView) itemView.findViewById(R.id.item_beer_style_textView);
        mContext = context;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, "Working Great.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mContext, DetailActivity.class);
        mContext.startActivity(intent);
    }

}
