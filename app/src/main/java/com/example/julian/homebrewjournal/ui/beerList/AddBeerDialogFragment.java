package com.example.julian.homebrewjournal.ui.beerList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.julian.homebrewjournal.R;

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
        return super.onCreateDialog(savedInstanceState);

    }
}
