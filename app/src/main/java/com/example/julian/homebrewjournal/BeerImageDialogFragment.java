package com.example.julian.homebrewjournal;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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

        Button lager = (Button) view.findViewById(R.id.lager);
        Button stout = (Button) view.findViewById(R.id.stout);
        Button apa   = (Button) view.findViewById(R.id.apa);


        lager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeerImage = 1;
                sendBackResult();
            }
        });

        stout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeerImage = 2;
                sendBackResult();
            }
        });

        apa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeerImage = 3;
                sendBackResult();
            }
        });

    }

}
