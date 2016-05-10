package com.example.julian.homebrewjournal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julian.homebrewjournal.model.Beer;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerHolder> {
    Context mContext;
    private List<Beer> mBeer;


    public BeerAdapter(Context c, List<Beer> beers){
        mContext = c;
        mBeer = beers;
    }

    @Override
    public BeerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View beerView = inflater.inflate(R.layout.recycler_view_item, parent, false);
        BeerHolder beerHolder = new BeerHolder(context, beerView);

        return beerHolder;

    }

    @Override
    public void onBindViewHolder(BeerHolder holder, int position) {
//        final Beer beer = mBeer.get(position);
//
//        TextView nameTextView = holder.nameTextView;
//        TextView styleTextView = holder.styleTextView;
//
//        nameTextView.setText(beer.getName());
//        styleTextView.setText(beer.getStyle());

        holder.nameTextView.setText(mBeer.get(position).getName());
        holder.styleTextView.setText(mBeer.get(position).getStyle());


    }

    @Override
    public int getItemCount() {
        return mBeer.size();
    }

}
