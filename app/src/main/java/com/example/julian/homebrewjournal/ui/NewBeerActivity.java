package com.example.julian.homebrewjournal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Beer;
import com.example.julian.homebrewjournal.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewBeerActivity extends BaseActivity {

    public static final String TAG = "NewBeerActivity";
    public static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    private EditText mNameField;
    private EditText mStyleField;
    private EditText mFGField;
    private EditText mOGField;
    private EditText mBeerVolumeField;
    private EditText mBoilVolumeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_beer_activty);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField  = (EditText) findViewById(R.id.field_beer_name);
        mStyleField = (EditText) findViewById(R.id.field_beer_style);
        mFGField    = (EditText) findViewById(R.id.field_beer_FG);
        mOGField    = (EditText) findViewById(R.id.field_beer_OG);
        mBeerVolumeField = (EditText) findViewById(R.id.field_beer_volume);
        mBoilVolumeField = (EditText) findViewById(R.id.field_beer_boil_volume);

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
        final String fgS    = mFGField.getText().toString();
        final String ogS    = mOGField.getText().toString();
        final String beerVolumeS = mBeerVolumeField.getText().toString();
        final String boilVolumeS = mBoilVolumeField.getText().toString();

        final Double og = Double.parseDouble(ogS);
        final Double fg = Double.parseDouble(fgS);
        final Double beerVolume = Double.parseDouble(beerVolumeS);
        final Double boilVolume = Double.parseDouble(boilVolumeS);

        // Name is required
        if(TextUtils.isEmpty(name)){
            mNameField.setError(REQUIRED);
            return;
        }


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
                    addNewBeer(userId, user.username, name, style, fg, og, beerVolume, boilVolume);
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
                            Double finalGravity, Double originalGravity, Double beerVolume, Double boilVolume) {
        String key = mDatabase.child("beers").push().getKey();
        Beer beer = new Beer(userId, username, name, style, finalGravity, originalGravity, beerVolume, boilVolume);
        Map<String, Object> beerValues = beer.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/beers/" + key, beerValues);
        childUpdates.put("/user-beers/" + userId + "/" + key, beerValues);

        Log.w(TAG, "We want to add new beer");
        mDatabase.updateChildren(childUpdates);
    }

}
