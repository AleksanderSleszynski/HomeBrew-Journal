package com.example.julian.homebrewjournal;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class BeerImageDialogFragment extends DialogFragment {

    private Button lager;
    private Button stout;
    private Button apa;

    private ImageView image;

    public BeerImageDialogFragment(){}

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

        lager = (Button) view.findViewById(R.id.lager);
        stout = (Button) view.findViewById(R.id.stout);
        apa   = (Button) view.findViewById(R.id.apa);

        image = (ImageView) view.findViewById(R.id.photo_image_view);

        lager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.lager));
            }
        });

        stout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.stout));
            }
        });

        apa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.pale_ale));
            }
        });

    }

}
