package com.example.julian.homebrewjournal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView styleTextView;

        public ViewHolder(View itemView){
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.item_beer_name_textView);
            styleTextView = (TextView) itemView.findViewById(R.id.item_beer_style_textView);
        }
    }

    private List<Beer> mBeer;


    public BeerAdapter(List<Beer> beers){
        mBeer = beers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View beerView = inflater.inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(beerView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Beer beer = mBeer.get(position);

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(beer.getName());


        TextView setTextView = holder.nameTextView;
        setTextView.setText(beer.getStyle());

    }

    @Override
    public int getItemCount() {
        return mBeer.size();
    }

}