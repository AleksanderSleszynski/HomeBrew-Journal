package com.example.julian.homebrewjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.julian.homebrewjournal.data.BeerDbAdapter;

public class DetailActivity extends AppCompatActivity {

    EditText nameTxt, styleTxt;
    Button updateBtn, deleteBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Receivie data form MA
        Intent intent = getIntent();

//        String name  = intent.getExtras().getString("NAME");
//        String style = intent.getExtras().getString("STYLE");
//        final int id = intent.getExtras().getInt("ID");

        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        nameTxt = (EditText) findViewById(R.id.nameEditText);
        styleTxt = (EditText) findViewById(R.id.styleEditText);


        //Assign data to those views
//        nameTxt.setText(name);
//        styleTxt.setText(style);

//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                update(id, nameTxt.getText().toString(), styleTxt.getText().toString());
//            }
//        });
//
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                delete(id);
//            }
//        });
    }

    private void update(int id, String newName, String newStyle){
        BeerDbAdapter db = new BeerDbAdapter(this);
        db.openDB();
        long result = db.update(id, newName, newStyle);

        if(result > 0 ){
            nameTxt.setText(newName);
            styleTxt.setText(newStyle);
            Snackbar.make(nameTxt,"Updated Successfully", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(nameTxt,"Unable to update", Snackbar.LENGTH_SHORT).show();
        }
        db.closeDB();
    }

    private void delete(int id){
        BeerDbAdapter db = new BeerDbAdapter(this);
        db.openDB();
        long result = db.delete(id);

        if(result > 0 ){
           this.finish();
        } else {
            Snackbar.make(nameTxt,"Unable to delete", Snackbar.LENGTH_SHORT).show();
        }
        db.closeDB();
    }
}
