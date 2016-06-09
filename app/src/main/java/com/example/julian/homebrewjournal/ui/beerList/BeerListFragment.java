package com.example.julian.homebrewjournal.ui.beerList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julian.homebrewjournal.DetailActivity;
import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Beer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerListFragment extends Fragment {

    public static final String TAG = "BeerListFragment";

    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Beer, BeerViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    public BeerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_beer_list, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = (RecyclerView) rootView.findViewById(R.id.recycler_view_beer_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query beerQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Beer, BeerViewHolder>(Beer.class,
                R.layout.single_beer, BeerViewHolder.class, beerQuery) {
            @Override
            protected void populateViewHolder(final BeerViewHolder viewHolder, final Beer model, int position) {
                final DatabaseReference beerRef = getRef(position);

                // Set click listener for the whole beer view
                final String beerKey = beerRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch BeerDetailActivity
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_BEER_KEY, beerKey);
                        startActivity(intent);
                    }
                });
                viewHolder.bindToBeer(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery(DatabaseReference databaseReference) {
        // All my beers
        return databaseReference.child("user-beers")
                .child(getUid());
    }

}
