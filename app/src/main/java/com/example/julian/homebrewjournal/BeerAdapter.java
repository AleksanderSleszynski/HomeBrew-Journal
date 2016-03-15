package com.example.julian.homebrewjournal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView styleTextView;
        private Context mContext;

        public ViewHolder(Context context, View itemView){
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

    private List<Beer> mBeer;

    public BeerAdapter(List<Beer> beers){
        mBeer = beers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View beerView = inflater.inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(context, beerView);

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
