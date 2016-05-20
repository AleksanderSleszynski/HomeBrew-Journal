package com.example.julian.homebrewjournal.ui.beerList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.julian.homebrewjournal.R;
import com.example.julian.homebrewjournal.model.Beer;
import com.example.julian.homebrewjournal.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.util.HashMap;

public class AddBeerDialogFragment extends DialogFragment {
    EditText mEditTextBeerName;

    public static AddBeerDialogFragment newInstance(){
        AddBeerDialogFragment addBeerDialogFragment = new AddBeerDialogFragment();
        Bundle bundle = new Bundle();
        addBeerDialogFragment.setArguments(bundle);
        return addBeerDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_beer, null);
        mEditTextBeerName = (EditText) rootView.findViewById(R.id.edit_text_beer_name);

        // Call addBeer() when user taps "Done keyboard action
        mEditTextBeerName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN) {
                    addBeer();
                }
                return true;
            }
        });

        /* Inflate and set the layout for the dialog
            Pass null as the parent view because its going in the dialog layout */
        builder.setView(rootView)
                // Add action button
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addBeer();
                    }
                });

        return builder.create();
    }

    // Add new Beer
    public void addBeer(){
        // Get string that the user entered into the EditText and make an object with it
        String userEntredName = mEditTextBeerName.getText().toString();
        // if EditText input is not empty
        if(!userEntredName.equals("")){
            // Create Firebase references
            Firebase beersRef = new Firebase(Constants.FIREBASE_URL_BEER_LIST);
            Firebase newBeerRef = beersRef.push();

            // Save beersRef.push() to maintain same random Id
            final String listId = newBeerRef.getKey();

            // Set raw version of date to the ServerValue.TIMESTAMP value
            // and save into timestampCreatedMap
            HashMap<String, Object> timestampCreated = new HashMap<>();
            timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

            // Build the beer
            Beer newBeer = new Beer(userEntredName);

            // Add the beer
            newBeerRef.setValue(newBeer);

            // Close the dialog fragment
            AddBeerDialogFragment.this.getDialog().cancel();
        }
    }
}
