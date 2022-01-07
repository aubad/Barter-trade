package com.example.csci3130project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SetFavourites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_favourites);

        ArrayList<String> favourites = new ArrayList<String>();

        Button confirmButton = findViewById(R.id.confirmBtn);

        // Retrieve category checkboxes
        CheckBox category1 = findViewById(R.id.category1);
        CheckBox category2 = findViewById(R.id.category2);
        CheckBox category3 = findViewById(R.id.category3);
        CheckBox category4 = findViewById(R.id.category4);
        CheckBox category5 = findViewById(R.id.category5);
        CheckBox category6 = findViewById(R.id.category6);
        CheckBox category7 = findViewById(R.id.category7);
        //TextView statusLabel = findViewById(R.id.statusLabel);

        // Confirm the user's choice of favourite categories
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reset status message
                String emptyStatus = "";
                //statusLabel.setText(emptyStatus);

                // If a category is selected, add it to the favourites collection
                if (category1.isChecked()) {
                    favourites.add(category1.getText().toString());
                }
                if (category2.isChecked()) {
                    favourites.add(category2.getText().toString());
                }
                if (category3.isChecked()) {
                    favourites.add(category3.getText().toString());
                }
                if (category4.isChecked()) {
                    favourites.add(category4.getText().toString());
                }
                if (category5.isChecked()) {
                    favourites.add(category5.getText().toString());
                }
                if (category6.isChecked()) {
                    favourites.add(category6.getText().toString());
                }
                if (category7.isChecked()) {
                    favourites.add(category7.getText().toString());
                }

                /*
                DatabaseFavourites submitFaves = new DatabaseFavourites();
                submitFaves.addFaves(favourites);
                */
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                DatabaseReference db = firebase.getReference();

                if (user != null) {
                    String userID = db.child("Users").child(user.getUid()).getKey();

                    db.child("Favourites").child(userID).setValue(favourites).addOnSuccessListener(success -> {
                        Toast.makeText(getApplicationContext(), "Favourites saved.", Toast.LENGTH_SHORT).show();
                        finish();
                    }).addOnFailureListener(fail -> {
                        Toast.makeText(getApplicationContext(), "Favourites failed to save.", Toast.LENGTH_SHORT).show();
                    });
                }

                /* Confirm favourite category selections
                String savedMessage = "Favourites have been saved.";
                statusLabel.setText(savedMessage);
                */

            }
        });
    }
}