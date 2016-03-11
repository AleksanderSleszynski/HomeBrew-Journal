package com.example.julian.homebrewjournal;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
//            mTextView = (TextView) v.findViewById(R.id.item_textView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            v.getContext().startActivity(intent);
        }
    }

    public MyAdapter(String[] myDatabase){
        mDataset = myDatabase;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
//        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return 14;
// return mDataset.length;
    }

}
