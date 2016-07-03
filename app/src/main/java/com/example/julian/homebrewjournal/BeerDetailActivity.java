package com.example.julian.homebrewjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julian.homebrewjournal.model.Beer;
import com.example.julian.homebrewjournal.ui.BaseActivity;
import com.example.julian.homebrewjournal.ui.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BeerDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "BeerDetailActivity";

    public static final String EXTRA_BEER_KEY = "beer_key";

    private DatabaseReference mBeerReference;
    private DatabaseReference mHopsReference;
    private ValueEventListener mBeerListener;

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private String mBeerKey;
//    private HopAdapter mAdapter;

    private TextView mNameTextView;
    private TextView mStyleTextView;
    private TextView mOGTextView;
    private TextView mFGTextView;
    private TextView mBoilTextView;
    private TextView mBeerTextView;

    private ImageView mBeerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get Beer key from Intent
        mBeerKey = getIntent().getStringExtra(EXTRA_BEER_KEY);
        if (mBeerKey == null){
            throw new IllegalArgumentException("Must pass EXTRA_BEER_KEY");
        }

        // Initialize Database
        mBeerReference = FirebaseDatabase.getInstance().getReference()
                .child("beers").child(mBeerKey);


        // Initialize Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mBeerImageView = (ImageView) findViewById(R.id.photo_image_view);
        mBeerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBeerImageDialog();
            }
        });

        // Initialize Views
        mNameTextView   = (TextView) findViewById(R.id.detail_name_textView);
        mStyleTextView  = (TextView) findViewById(R.id.detail_style_textView);
        mOGTextView     = (TextView) findViewById(R.id.detail_og_textView);
        mFGTextView     = (TextView) findViewById(R.id.detail_fg_textView);
        mBoilTextView   = (TextView) findViewById(R.id.detail_boiling_volume_textView);
        mBeerTextView   = (TextView) findViewById(R.id.detail_beer_volume_textView);

        mBeerImageView  = (ImageView) findViewById(R.id.photo_image_view);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Add value event listener to the post
        ValueEventListener beerlistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Beer object and use the values to update the UI
                Beer beer = dataSnapshot.getValue(Beer.class);

                String originalGravity = beer.originalGravity + " " + (char) 0x00B0 +"P";
                String finalGravity = beer.finalGravity + " " + (char) 0x00B0 +"P";
                String boilVolume = beer.boilVolume + "  L";
                String beerVolume = beer.beerVolume + "  L";

                mNameTextView.setText(beer.name);
                mStyleTextView.setText(beer.style);
                mOGTextView.setText(originalGravity);
                mFGTextView.setText(finalGravity);
                mBoilTextView.setText(boilVolume);
                mBeerTextView.setText(beerVolume);

                Utility.setBeerImage(mBeerImageView, beer.beerImage);

                mCollapsingToolbar.setTitle(beer.name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadBeer:onCancelled", databaseError.toException());
                Toast.makeText(BeerDetailActivity.this, "Failed to load beer.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        mBeerReference.addValueEventListener(beerlistener);
    }

    @Override
    public void onClick(View v) {

    }

    private void showBeerImageDialog(){
        BeerImageDialogFragment beerImageDialogFragment = BeerImageDialogFragment.newInstance();
        beerImageDialogFragment.show(getFragmentManager(), "TAG");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                // Intent to SignIntActivity
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
