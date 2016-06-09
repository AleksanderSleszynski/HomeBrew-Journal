package com.example.julian.homebrewjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {

    EditText nameTxt, styleTxt;
    Button updateBtn, deleteBtn;

    public static final String EXTRA_BEER_KEY = "beer_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Receivie data form MA
        Intent intent = getIntent();


        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        nameTxt = (EditText) findViewById(R.id.nameEditText);
        styleTxt = (EditText) findViewById(R.id.styleEditText);



    }

}
