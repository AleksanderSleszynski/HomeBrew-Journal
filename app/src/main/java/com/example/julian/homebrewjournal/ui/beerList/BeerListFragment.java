package com.example.julian.homebrewjournal.ui.beerList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Beer;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerListFragment extends Fragment {

    public static final String TAG = "BeerListFragment";

    private BeerListAdapter mBeerListAdapter;
    private RecyclerView recyclerView;
//    private FirebaseDatabase beerListRef;

    ArrayList<Beer> beers = new ArrayList<>();

    private ListView mListView;
    private TextView mNameTextView, mStyleTextView;


    public BeerListFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static BeerListFragment newInstance(){
        BeerListFragment fragment = new BeerListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beer_list, container, false);
        initializeScreen(rootView);

        /* Create Firebase references */
//        beerListRef = new FirebaseDatabase(Constants.FIREBASE_URL_BEER_LIST);

//        /**
//         * Create the adapater, giving it the activity, model class, layout for each row in
//         * the list and finnally, a reference to the Firebase location with the list data
//         */
//        mBeerListAdapter = new BeerListAdapter(getActivity(), Beer.class,
//                R.layout.single_beer, beerListRef);
//
//        /* Set the adapter to the mListView */
//        mListView.setAdapter(mBeerListAdapter);
//
//        /* Set click events and adapters */
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Beer selectedBeer = mBeerListAdapter.getItem(position);
//            if(selectedBeer != null){
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                String listId = mBeerListAdapter.getRef(position).getKey();
//                intent.putExtra(Constants.KEY_LIST_ID, listId);
//
//                startActivity(intent);
//            }
//            }
//        });

        return rootView;
    }


    private void initializeScreen(View rootView) {
//        mListView = (ListView) rootView.findViewById(R.id.list_view_beer_list);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_beer_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
