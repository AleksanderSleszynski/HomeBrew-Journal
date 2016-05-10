package com.example.julian.homebrewjournal.ui.beerList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.julian.homebrewjournal.DetailActivity;
import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Beer;
import com.example.julian.homebrewjournal.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerListActivityFragment extends Fragment {

    private BeerListAdapter mBeerListAdapter;
    private ListView mListView;

    public BeerListActivityFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static BeerListActivityFragment newInstance(){
        BeerListActivityFragment fragment = new BeerListActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beer_list, container, false);

        initializeScreen(rootView);

        /* Create Firebase references */
        Firebase beerListRef = new Firebase(Constants.FIREBASE_URL_BEER_LIST);

        /**
         * Create the adapater, giving it the activity, model class, layout for each row in
         * the list and finnally, a reference to the Firebase location with the list data
         */
        mBeerListAdapter = new BeerListAdapter(getActivity(), Beer.class,
                R.layout.single_beer, beerListRef);

        /* Set the adapter to the mListView */
        mListView.setAdapter(mBeerListAdapter);

        /* Set click events and adapters */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Beer selectedBeer = mBeerListAdapter.getItem(position);
            if(selectedBeer != null){
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Toast.makeText(getContext(), "Toast", Toast.LENGTH_SHORT).show();
            }
            }
        });


        // ToDO: Move FAB to Activity
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeerListAdapter.cleanup();
    }

    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_beer_list);
    }

//    public void showDialog() {
//
//        Dialog dialog = new Dialog(getContext());
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.setContentView(R.layout.custom_layout);
//
//        nameTxt = (EditText) dialog.findViewById(R.id.nameEditText);
//        styleTxt = (EditText) dialog.findViewById(R.id.styleEditText);
//
//        final Button save = (Button) dialog.findViewById(R.id.save);
//        final Button retrieve = (Button) dialog.findViewById(R.id.ret);
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                save(nameTxt.getText().toString(), styleTxt.getText().toString());
//            }
//        });
//
//        retrieve.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                retrieve();
//            }
//        });
//
//        dialog.show();
//    }
//
//    private void save(String name, String style){
//        BeerDbAdapter db = new BeerDbAdapter(getContext());
//
//        db.openDB();
//
//        long result = db.add(name, style);
//
//        if (result > 0) {
//            nameTxt.setText("");
//            styleTxt.setText("");
//        } else {
//            Snackbar.make(nameTxt, "Unable To save", Snackbar.LENGTH_SHORT).show();
//        }
//
//        db.closeDB();
//
//        retrieve();
//    }
//
//    private void retrieve(){
//
//        BeerDbAdapter db = new BeerDbAdapter(getContext());
//        db.openDB();
//
//        beers.clear();
//
//        Cursor cursor = db.getBeerList();
//
//        while (cursor.moveToNext()){
//            int id = cursor.getInt(0);
//            String name = cursor.getString(1);
//            String style = cursor.getString(2);
//
////            Beer beer = new Beer(id, name, style);
//
////            beers.add(beer);
//
//        }
//
//        if(!(beers.size() < 1)){
//            recyclerView.setAdapter(adapter);
//        }
//
//        db.closeDB();
//    }
}
