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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeerDetailActivity extends BaseActivity
        implements BeerImageDialogFragment.BeerImageDialogListener {

    public static final String TAG = "BeerDetailActivity";
    public static final String EXTRA_BEER_KEY = "beer_key";
    public static final String REQUIRED = "Required";

    private DatabaseReference mBeerReference, mBeerUserReference, mHopReference, mMaltReference;
    private ValueEventListener mBeerListener;
    private FirebaseRecyclerAdapter<Hop, IngredientViewHolder> mHopAdapter;
    private FirebaseRecyclerAdapter<Malt, IngredientViewHolder> mMaltAdapter;

    private String mBeerKey;
    private String mUserUid;
    private String name;
    private String style;
    private String originalGravity;
    private String finalGravity;
    private String boilVolume;
    private String beerVolume;
    private String litre = " l";
    private String plato  = " °P";

    private int mNumberDialog;

    @BindView(R.id.recycler_hop)    RecyclerView mHopsRecycler;
    @BindView(R.id.recycler_malt)   RecyclerView mMaltsRecycler;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.name_detail_textView)    TextView mNameTextView;
    @BindView(R.id.style_detail_textView)   TextView mStyleTextView;
    @BindView(R.id.og_detail_textView)      TextView mOGTextView;
    @BindView(R.id.fg_detail_textView)      TextView mFGTextView;
    @BindView(R.id.boil_detail_textView)    TextView mBoilTextView;
    @BindView(R.id.beer_detail_textView)    TextView mBeerTextView;

    @BindView(R.id.name_detail_edit_text)   EditText mNameEditText;
    @BindView(R.id.style_detail_edit_text)  EditText mStyleEditText;
    @BindView(R.id.og_detail_edit_text)     EditText mOGEditText;
    @BindView(R.id.fg_detail_edit_text)     EditText mFGEditText;
    @BindView(R.id.boil_detail_edit_text)   EditText mBoilEditText;
    @BindView(R.id.beer_detail_edit_text)   EditText mBeerEditText;

    @BindView(R.id.photo_image_view)    ImageView mBeerImageView;

    @BindView(R.id.add_hop_detail_fab)  FloatingActionButton mFabAddHop;
    @BindView(R.id.add_malt_detail_fab) FloatingActionButton mFabAddMalt;

    @BindView(R.id.save_detail_button)  Button mSaveButton;

    @BindView(R.id.basic_info_detail_card_view) CardView mBasicInfoCardView;
    @BindView(R.id.edit_detail_card_view)       CardView mEditCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Get Beer key from Intent
        mBeerKey = getIntent().getStringExtra(EXTRA_BEER_KEY);
        if (mBeerKey == null){
            throw new IllegalArgumentException("Must pass EXTRA_BEER_KEY");
        }

        // Get user Uid
        mUserUid = getUid();

        initializeDatabase();
        initializeScreen();

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

                if(beer != null) {
                    name             = beer.name;
                    style            = beer.style;
                    originalGravity  = Double.toString(beer.originalGravity);
                    finalGravity     = Double.toString(beer.finalGravity);
                    boilVolume       = Double.toString(beer.boilVolume);
                    beerVolume       = Double.toString(beer.beerVolume);

                    mNameTextView.setText(name);
                    mStyleTextView.setText(style);
                    mOGTextView.setText(originalGravity);
                    mFGTextView.setText(finalGravity);
                    mBoilTextView.setText(boilVolume);
                    mBeerTextView.setText(beerVolume);
                    mOGTextView.append(plato);
                    mFGTextView.append(plato);
                    mBoilTextView.append(litre);
                    mBeerTextView.append(litre);

                    Utility.setBeerImage(mBeerImageView, beer.beerImage);
                    mNumberDialog = beer.beerImage;

                    mCollapsingToolbar.setTitle(name);

                    mNameEditText.setText(name);
                    mStyleEditText.setText(style);
                    mOGEditText.setText(originalGravity);
                    mFGEditText.setText(finalGravity);
                    mBoilEditText.setText(boilVolume);
                    mBeerEditText.setText(beerVolume);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Beer failed, log a message
                Log.w(TAG, "loadBeer:onCancelled", databaseError.toException());
                Toast.makeText(BeerDetailActivity.this, "Failed to load beer.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        mBeerUserReference.addValueEventListener(beerListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBeerListener != null){
            mBeerReference.removeEventListener(mBeerListener);
            mBeerUserReference.removeEventListener(mBeerListener);
        }
        mHopAdapter.cleanup();
        mMaltAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_customize:
                //ToDo: Implement ActivityForResult
                startActivityForResult(new Intent(this, CustomizeLogoActivity.class), 1);
                return true;
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

        // Initialize Toolbar
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mBasicInfoCardView.setVisibility(View.VISIBLE);
        mEditCardView.setVisibility(View.GONE);

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
    }

    public void initializeDatabase(){
        mBeerReference = FirebaseDatabase.getInstance().getReference().child("beers")
                .child(mBeerKey);
        mBeerUserReference = FirebaseDatabase.getInstance().getReference().child("user-beers")
                .child(mUserUid).child(mBeerKey);
        mHopReference = FirebaseDatabase.getInstance().getReference().child("hops")
                .child(mBeerKey);
        mMaltReference = FirebaseDatabase.getInstance().getReference().child("malts")
                .child(mBeerKey);
    }

    public void deleteBeer(){
        mBeerUserReference.removeValue();
        mBeerReference.removeValue();
        mHopReference.removeValue();
        mMaltReference.removeValue();
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(BeerDetailActivity.this, R.string.beer_deleted, Toast.LENGTH_SHORT).show();
    }

    public void save(){
        // Save values to beers
        mBeerReference.child("name").setValue(mNameEditText.getText().toString());
        mBeerReference.child("style").setValue(mStyleEditText.getText().toString());
        mBeerReference.child("originalGravity").setValue(Double.parseDouble(mOGEditText.getText().toString()));
        mBeerReference.child("finalGravity").setValue(Double.parseDouble(mFGEditText.getText().toString()));
        mBeerReference.child("beerVolume").setValue(Double.parseDouble(mBeerEditText.getText().toString()));
        mBeerReference.child("boilVolume").setValue(Double.parseDouble(mBoilEditText.getText().toString()));
        // Save values to user-beers
        mBeerUserReference.child("name").setValue(mNameEditText.getText().toString());
        mBeerUserReference.child("style").setValue(mStyleEditText.getText().toString());
        mBeerUserReference.child("originalGravity").setValue(Double.parseDouble(mOGEditText.getText().toString()));
        mBeerUserReference.child("finalGravity").setValue(Double.parseDouble(mFGEditText.getText().toString()));
        mBeerUserReference.child("beerVolume").setValue(Double.parseDouble(mBeerEditText.getText().toString()));
        mBeerUserReference.child("boilVolume").setValue(Double.parseDouble(mBoilEditText.getText().toString()));
    }

    public void addHop(final String name, final Double weight){
        FirebaseDatabase.getInstance().getReference().child("hops").child(mUserUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Hop hop =  new Hop(mUserUid, name, weight);
                        mHopReference.push().setValue(hop);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void addMalt(final String name, final Double weight){
        FirebaseDatabase.getInstance().getReference().child("malts").child(mUserUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Malt malt =  new Malt(mUserUid, name, weight);
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

}
