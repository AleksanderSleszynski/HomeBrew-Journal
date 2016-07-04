package com.example.julian.homebrewjournal.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.julian.homebrewjournal.R;
import com.squareup.picasso.Picasso;

public class BeerImageDialogAdapter extends BaseAdapter {

    private Context mContext;

    public BeerImageDialogAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mBeerImages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(imageView.getContext()).load(mBeerImages[position]).into(imageView);
        return imageView;
    }

    // references to beerImages
    private Integer[] mBeerImages = {
            R.drawable.lager,
            R.drawable.stout,
            R.drawable.pale_ale,
            R.drawable.wheat
    };

}
