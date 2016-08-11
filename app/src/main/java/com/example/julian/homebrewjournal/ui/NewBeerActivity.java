package com.example.julian.homebrewjournal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.julian.homebrewjournal.ui.dialog.BeerImageDialogFragment;
import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.Utility;
import com.example.julian.homebrewjournal.model.Beer;
import com.example.julian.homebrewjournal.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewBeerActivity extends BaseActivity
        implements BeerImageDialogFragment.BeerImageDialogListener {

    public static final String TAG = "NewBeerActivity";
    public static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    @BindView(R.id.name_new_beer_edit_text)  EditText mNameField;
    @BindView(R.id.style_new_beer_edit_text) EditText mStyleField;
    @BindView(R.id.fg_new_beer_edit_text)    EditText mFGField;
    @BindView(R.id.og_new_beer_edit_text)    EditText mOGField;
    @BindView(R.id.beer_new_beer_edit_text)  EditText mBeerVolumeField;
    @BindView(R.id.boil_new_beer_edit_text)  EditText mBoilVolumeField;

    @BindView(R.id.beer_image) ImageView mBeerImage;

    private int mBeerImageDialog = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_new_beer);
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mBeerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeerImageDialogFragment beerImageDialogFragment = BeerImageDialogFragment.newInstance();
                beerImageDialogFragment.show(getFragmentManager(), "TAG");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_submit_beer);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBeer();
            }
        });
    }

    private void addBeer() {
        final String name   = mNameField.getText().toString();
        final String style  = mStyleField.getText().toString();
        String fgS    = mFGField.getText().toString();
        String ogS    = mOGField.getText().toString();
        String beerVolumeS = mBeerVolumeField.getText().toString();
        String boilVolumeS = mBoilVolumeField.getText().toString();

        // Name is required
        if(TextUtils.isEmpty(name)){
            mNameField.setError(REQUIRED);
            return;
        }

        // Style is required
        if(TextUtils.isEmpty(style)){
            mStyleField.setError(REQUIRED);
            return;
        }

        // If empty set 0
        if(TextUtils.isEmpty(fgS))
            fgS = "0";
        if(TextUtils.isEmpty(ogS))
            ogS = "0";
        if(TextUtils.isEmpty(beerVolumeS))
            beerVolumeS = "0";
        if(TextUtils.isEmpty(boilVolumeS))
            boilVolumeS = "0";

        // Convert to String to Double
        final Double og = Double.parseDouble(ogS);
        final Double fg = Double.parseDouble(fgS);
        final Double beerVolume = Double.parseDouble(beerVolumeS);
        final Double boilVolume = Double.parseDouble(boilVolumeS);

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
            new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                User user = dataSnapshot.getValue(User.class);

                if(user == null){
                    // User is null, error out
                    Log.e(TAG, "User " + userId + " is unexpectedly null");
                    Toast.makeText(NewBeerActivity.this, "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Add new beer
                    Log.e(TAG, "User " + userId + " adding new beer");
                    addNewBeer(userId, user.username, name, style, fg, og, beerVolume, boilVolume,
                            mBeerImageDialog);
                }

                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

    }

    private void addNewBeer(String userId, String username, String name, String style,
                            Double finalGravity, Double originalGravity, Double beerVolume,
                            Double boilVolume, int beerImage ) {
        String key = mDatabase.child("beers").push().getKey();
        Beer beer = new Beer(userId, username, name, style, finalGravity, originalGravity,
                beerVolume, boilVolume, beerImage);
        Map<String, Object> beerValues = beer.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/beers/" + key, beerValues);
        childUpdates.put("/user-beers/" + userId + "/" + key, beerValues);

        mDatabase.updateChildren(childUpdates);
    }

    @Override
    public void onFinishEditDialog(int beerImage) {
        mBeerImageDialog = beerImage;
        Utility.setBeerImage(mBeerImage, mBeerImageDialog);
    }
}
