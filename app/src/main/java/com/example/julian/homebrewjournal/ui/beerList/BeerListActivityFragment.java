package com.example.julian.homebrewjournal.ui.beerList;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.julian.homebrewjournal.BeerAdapter;
import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.data.BeerDbAdapter;
import com.example.julian.homebrewjournal.model.Beer;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerListActivityFragment extends Fragment {

    EditText nameTxt, styleTxt;
    RecyclerView recyclerView;
    BeerAdapter adapter;
    ArrayList<Beer> beers = new ArrayList<>();

    public BeerListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beer_list, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new BeerAdapter(getContext(), beers);

//        retrieve();

        return rootView;
    }

    public void showDialog() {

        Dialog dialog = new Dialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.custom_layout);

        nameTxt = (EditText) dialog.findViewById(R.id.nameEditText);
        styleTxt = (EditText) dialog.findViewById(R.id.styleEditText);

        final Button save = (Button) dialog.findViewById(R.id.save);
        final Button retrieve = (Button) dialog.findViewById(R.id.ret);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(nameTxt.getText().toString(), styleTxt.getText().toString());
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieve();
            }
        });

        dialog.show();
    }

    private void save(String name, String style){
        BeerDbAdapter db = new BeerDbAdapter(getContext());

        db.openDB();

        long result = db.add(name, style);

        if (result > 0) {
            nameTxt.setText("");
            styleTxt.setText("");
        } else {
            Snackbar.make(nameTxt, "Unable To save", Snackbar.LENGTH_SHORT).show();
        }

        db.closeDB();

        retrieve();
    }

    private void retrieve(){

        BeerDbAdapter db = new BeerDbAdapter(getContext());
        db.openDB();

        beers.clear();

        Cursor cursor = db.getBeerList();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String style = cursor.getString(2);

            Beer beer = new Beer(id, name, style);

            beers.add(beer);

        }

        if(!(beers.size() < 1)){
            recyclerView.setAdapter(adapter);
        }

        db.closeDB();
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieve();
    }
}
