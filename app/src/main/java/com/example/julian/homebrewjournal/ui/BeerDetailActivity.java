package com.example.julian.homebrewjournal.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.Utility;
import com.example.julian.homebrewjournal.model.Beer;
import com.example.julian.homebrewjournal.ui.dialog.BeerImageDialogFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BeerDetailActivity extends BaseActivity
        implements BeerImageDialogFragment.BeerImageDialogListener {

    public static final String TAG = "BeerDetailActivity";

    public static final String EXTRA_BEER_KEY = "beer_key";

    private DatabaseReference mBeerReference, mBeerUserReference, mHopsReference;
    private ValueEventListener mBeerListener;

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private String mBeerKey;
    private String mUserUid;
    String originalGravity;
//    private HopAdapter mAdapter;

    private TextView mNameTextView, mStyleTextView, mOGTextView, mFGTextView,
            mBoilTextView, mBeerTextView;

    private EditText mNameEditText, mStyleEditText, mOGEditText, mFGEditText, mBoilEditText,
            mBeerEditText;

    private ImageView mBeerImageView;

    private FloatingActionButton mFabAddHop;
    private FloatingActionButton mFabAddMalt;

    private Button mSaveButton;

    private CardView mBasicInfoCardView;
    private CardView mEditCardView;

    private int mNumberDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get Beer key from Intent
        mBeerKey = getIntent().getStringExtra(EXTRA_BEER_KEY);
        if (mBeerKey == null){
            throw new IllegalArgumentException("Must pass EXTRA_BEER_KEY");
        }

        mUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize Database
        mBeerReference = FirebaseDatabase.getInstance().getReference()
                .child("beers").child(mBeerKey);
        mBeerUserReference = FirebaseDatabase.getInstance().getReference()
                .child("user-beers").child(mUserUid).child(mBeerKey);

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
        initializeScreen();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            save();
            showBasicInfo();

            }
        });

        mFabAddHop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BeerDetailActivity.this, "HOP", Toast.LENGTH_SHORT).show();
            }
        });

        mFabAddMalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BeerDetailActivity.this, "malt", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Add value event listener to the post
        ValueEventListener beerListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Beer object and use the values to update the UI
                Beer beer = dataSnapshot.getValue(Beer.class);

                if(beer !=null) {
//                    String originalGravity = beer.originalGravity + " " + (char) 0x00B0 + "P";
//                    String finalGravity = beer.finalGravity + " " + (char) 0x00B0 + "P";
//                    String boilVolume = beer.boilVolume + "  L";
//                    String beerVolume = beer.beerVolume + "  L";

                    originalGravity  = Double.toString(beer.originalGravity);
                    String finalGravity     = Double.toString(beer.finalGravity);
                    String boilVolume       = Double.toString(beer.boilVolume);
                    String beerVolume       = Double.toString(beer.beerVolume);

                    mNameTextView.setText(beer.name);
                    mStyleTextView.setText(beer.style);
                    mOGTextView.setText(originalGravity);
                    mFGTextView.setText(finalGravity);
                    mBoilTextView.setText(boilVolume);
                    mBeerTextView.setText(beerVolume);

                    Utility.setBeerImage(mBeerImageView, beer.beerImage);
                    mNumberDialog = beer.beerImage;

                    mCollapsingToolbar.setTitle(beer.name);

                    mNameEditText.setText(beer.name);
                    mStyleEditText.setText(beer.style);
                    mOGEditText.setText(originalGravity);
                    mFGEditText.setText(finalGravity);
                    mBoilEditText.setText(boilVolume);
                    mBeerEditText.setText(beerVolume);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadBeer:onCancelled", databaseError.toException());
                Toast.makeText(BeerDetailActivity.this, "Failed to load beer.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        mBeerReference.addValueEventListener(beerListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBeerListener != null){
            mBeerReference.removeEventListener(mBeerListener);
            mBeerUserReference.removeEventListener(mBeerListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                onCreateDialog();
                return true;
            case R.id.action_edit:
                hideBasicInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initializeScreen(){
        mNameTextView   = (TextView) findViewById(R.id.name_detail_textView);
        mStyleTextView  = (TextView) findViewById(R.id.style_detail_textView);
        mOGTextView     = (TextView) findViewById(R.id.og_detail_textView);
        mFGTextView     = (TextView) findViewById(R.id.fg_detail_textView);
        mBoilTextView   = (TextView) findViewById(R.id.boiling_volume_detail_textView);
        mBeerTextView   = (TextView) findViewById(R.id.beer_volume_detail_textView);

        mNameEditText   = (EditText) findViewById(R.id.name_detail_edit_text);
        mStyleEditText  = (EditText) findViewById(R.id.style_detail_edit_text);
        mOGEditText     = (EditText) findViewById(R.id.og_detail_edit_text);
        mFGEditText     = (EditText) findViewById(R.id.fg_detail_edit_text);
        mBoilEditText   = (EditText) findViewById(R.id.boil_volume_detail_edit_text);
        mBeerEditText   = (EditText) findViewById(R.id.beer_volume_detail_edit_text);

        mBeerImageView  = (ImageView) findViewById(R.id.photo_image_view);

        mFabAddHop  = (FloatingActionButton) findViewById(R.id.add_hop_detail_fab);
        mFabAddMalt = (FloatingActionButton) findViewById(R.id.add_malt_detail_fab);

        mBasicInfoCardView  = (CardView) findViewById(R.id.basic_info_detail_card_view);
        mEditCardView       = (CardView) findViewById(R.id.edit_detail_card_view);

        mSaveButton = (Button) findViewById(R.id.save_detail_button);

        mBasicInfoCardView.setVisibility(View.VISIBLE);
        mEditCardView.setVisibility(View.GONE);
    }

    public void deleteBeer(){
        mBeerUserReference.removeValue();
        mBeerReference.removeValue();
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(BeerDetailActivity.this, R.string.beer_deleted, Toast.LENGTH_SHORT).show();
    }

    public void save(){
        mBeerReference.child("name").setValue(mNameEditText.getText().toString());
        mBeerUserReference.child("name").setValue(mNameEditText.getText().toString());

        mBeerReference.child("style").setValue(mStyleEditText.getText().toString());
        mBeerUserReference.child("style").setValue(mStyleEditText.getText().toString());

        mBeerReference.child("originalGravity").setValue(Double.parseDouble(mOGEditText.getText().toString()));
        mBeerUserReference.child("originalGravity").setValue(Double.parseDouble(mOGEditText.getText().toString()));

        mBeerReference.child("finalGravity").setValue(Double.parseDouble(mFGEditText.getText().toString()));
        mBeerUserReference.child("finalGravity").setValue(Double.parseDouble(mFGEditText.getText().toString()));

        mBeerReference.child("beerVolume").setValue(Double.parseDouble(mBeerEditText.getText().toString()));
        mBeerUserReference.child("beerVolume").setValue(Double.parseDouble(mBeerEditText.getText().toString()));

        mBeerReference.child("boilVolume").setValue(Double.parseDouble(mBoilEditText.getText().toString()));
        mBeerUserReference.child("boilVolume").setValue(Double.parseDouble(mBoilEditText.getText().toString()));

    }

    public void showBasicInfo(){
        mBasicInfoCardView.setVisibility(View.VISIBLE);
        mEditCardView.setVisibility(View.GONE);
    }

    public void hideBasicInfo(){
        mBasicInfoCardView.setVisibility(View.GONE);
        mEditCardView.setVisibility(View.VISIBLE);
    }

    public void onCreateDialog(){
        new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_delete_message)
                .setTitle(R.string.dialog_delete_title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBeer();
                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }})
                .show();
    }

    private void showBeerImageDialog(){
        BeerImageDialogFragment beerImageDialogFragment = BeerImageDialogFragment.newInstance();
        beerImageDialogFragment.show(getFragmentManager(), "TAG");
    }

    @Override
    public void onFinishEditDialog(int beerImage) {
        mNumberDialog = beerImage;
        Utility.setBeerImage(mBeerImageView, mNumberDialog);

        mBeerReference.child("beerImage").setValue(mNumberDialog);
        mBeerUserReference.child("beerImage").setValue(mNumberDialog);
    }

}
