package com.example.julian.homebrewjournal.ui.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.julian.homebrewjournal.R;


public class BeerImageDialogFragment extends DialogFragment{

    private int mBeerImage;

    public BeerImageDialogFragment(){}

    public interface BeerImageDialogListener {
        void onFinishEditDialog(int beerImage);
    }

    public void sendBackResult(){
        BeerImageDialogListener listener = (BeerImageDialogListener) getActivity();
        listener.onFinishEditDialog(mBeerImage);
        dismiss();
    }

    public static BeerImageDialogFragment newInstance(){
        BeerImageDialogFragment frag = new BeerImageDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_beer_image, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = (GridView) view.findViewById(R.id.beer_image_dialog_grid_view);
        gridView.setAdapter(new BeerImageDialogAdapter(view.getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBeerImage = position + 1;
                sendBackResult();
            }
        });

    }

}
