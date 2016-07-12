package com.example.julian.homebrewjournal.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.julian.homebrewjournal.model.Hop;
import com.example.julian.homebrewjournal.model.Malt;
import com.example.julian.homebrewjournal.ui.dialog.BeerImageDialogFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BeerDetailActivity extends BaseActivity
        implements BeerImageDialogFragment.BeerImageDialogListener {

    public static final String TAG = "BeerDetailActivity";
    public static final String EXTRA_BEER_KEY = "beer_key";
    public static final String REQUIRED = "Required";

    private DatabaseReference mBeerReference, mBeerUserReference, mHopReference, mMaltReference;
    private ValueEventListener mBeerListener;
    private FirebaseRecyclerAdapter<Hop, IngredientViewHolder> mHopAdapter;
    private FirebaseRecyclerAdapter<Malt, IngredientViewHolder> mMaltAdapter;
    private RecyclerView mHopsRecycler, mMaltsRecycler;

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private String mBeerKey, mUserUid, originalGravity;

    private TextView mNameTextView, mStyleTextView, mOGTextView, mFGTextView,
            mBoilTextView, mBeerTextView;
    private EditText mNameEditText, mStyleEditText, mOGEditText, mFGEditText, mBoilEditText,
            mBeerEditText;
    private ImageView mBeerImageView;

    private FloatingActionButton mFabAddMalt, mFabAddHop;
    private Button mSaveButton;

    private CardView mBasicInfoCardView, mEditCardView;

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
        mHopReference = FirebaseDatabase.getInstance().getReference()
                .child("hops").child(mBeerKey);
        mMaltReference = FirebaseDatabase.getInstance().getReference()
                .child("malts").child(mBeerKey);

        // Initialize Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Views
        initializeScreen();

        mHopsRecycler.setHasFixedSize(true);
        mHopsRecycler.setLayoutManager(new LinearLayoutManager(this));

        mMaltsRecycler.setHasFixedSize(true);
        mMaltsRecycler.setLayoutManager(new LinearLayoutManager(this));

        Query hopQuery = mHopReference;
        mHopAdapter = new FirebaseRecyclerAdapter<Hop, IngredientViewHolder>
                (Hop.class, R.layout.item_ingredient, IngredientViewHolder.class, hopQuery){

            @Override
            protected void populateViewHolder(IngredientViewHolder viewHolder, Hop model, final int position) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mHopAdapter.getRef(position).removeValue();
                        return false;
                    }
                });
                viewHolder.bindToHop(model);
            }
        };
        mHopsRecycler.setAdapter(mHopAdapter);

        Query maltQuery = mMaltReference;
        mMaltAdapter = new FirebaseRecyclerAdapter<Malt, IngredientViewHolder>
                (Malt.class, R.layout.item_ingredient, IngredientViewHolder.class, maltQuery){

            @Override
            protected void populateViewHolder(IngredientViewHolder viewHolder, Malt model, final int position) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mMaltAdapter.getRef(position).removeValue();
                        return false;
                    }
                });
                viewHolder.bindToMalt(model);
            }
        };
        mMaltsRecycler.setAdapter(mMaltAdapter);

        mBeerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBeerImageDialog();
            }
        });

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
                onCreateIngredientDialog(getString(R.string.dialog_hop_title), 1);
            }
        });

        mFabAddMalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateIngredientDialog(getString(R.string.dialog_malt_title), 2);
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
        mHopAdapter.cleanup();
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

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mBasicInfoCardView.setVisibility(View.VISIBLE);
        mEditCardView.setVisibility(View.GONE);

        mHopsRecycler = (RecyclerView) findViewById(R.id.recycler_hop);
        mMaltsRecycler = (RecyclerView) findViewById(R.id.recycler_malt);
    }

    public void deleteBeer(){
        mBeerUserReference.removeValue();
        mBeerReference.removeValue();
        mHopReference.removeValue();
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

    public void addHop(final String name, final Double weight){
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("hops").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Hop hop =  new Hop(uid, name, weight);
                        mHopReference.push().setValue(hop);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void addMalt(final String name, final Double weight){
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("malts").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Malt malt =  new Malt(uid, name, weight);
                        mMaltReference.push().setValue(malt);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

    public void onCreateIngredientDialog(String title, final int ingredient){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_ingredient, null);
        dialogBuilder.setView(dialogView);

        final EditText nameEditText = (EditText) dialogView.findViewById(R.id.name_dialog_hop_edit_text);
        final EditText weightEditText = (EditText) dialogView.findViewById(R.id.weight_dialog_hop_edit_text);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameEditText.getText().toString();
                final Double weight = Double.parseDouble(weightEditText.getText().toString());

                if(ingredient == 1) {
                    addHop(name, weight);
                } else {
                    addMalt(name, weight);
                }
            }
        });
        dialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void showBeerImageDialog(){
        BeerImageDialogFragment beerImageDialogFragment = BeerImageDialogFragment.newInstance();
        beerImageDialogFragment.show(getFragmentManager(), "TAG");
    }

    @Override
    public void onFinishEditDialog(int beerImage){
        mNumberDialog = beerImage;
        Utility.setBeerImage(mBeerImageView, mNumberDialog);

        mBeerReference.child("beerImage").setValue(mNumberDialog);
        mBeerUserReference.child("beerImage").setValue(mNumberDialog);
    }


    public Query getHopQuery(DatabaseReference databaseReference) {
        // All my beers
        return databaseReference.child("hops").child(mBeerKey);
    }

}
