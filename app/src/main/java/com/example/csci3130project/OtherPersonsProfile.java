package com.example.csci3130project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OtherPersonsProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_persons_profile);

        String userIDIntent = getIntent().getStringExtra("userID");
        String otherPersonsID = userIDIntent.toString();

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebase.getReference();

        TextView nameBox = findViewById(R.id.FullNameText);
        TextView emailBox = findViewById(R.id.EmailText);
        TextView userNameBox = findViewById(R.id.UserNameText);

        RatingBar userStars = findViewById(R.id.ratingBar2);
        TextView userRating = findViewById(R.id.ratingNumber2);
        TextView ratingCount = findViewById(R.id.ratingCount2);

        // Used for setting colors for the rating bar
        LayerDrawable starcolor = (LayerDrawable) userStars.getProgressDrawable();
        starcolor.getDrawable(1).setColorFilter(Color.parseColor("#e3e6e9"), PorterDuff.Mode.SRC_ATOP);
        starcolor.getDrawable(0).setColorFilter(Color.parseColor("#e3e6e9"), PorterDuff.Mode.SRC_ATOP);
        starcolor.getDrawable(2).setColorFilter(Color.parseColor("#900C3F"), PorterDuff.Mode.SRC_ATOP);


        db.child("Users").child(otherPersonsID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                String fName = data.child("firstName").getValue(String.class);
                String lName =  data.child("lastName").getValue(String.class);
                String fullName = fName + " " + lName;
                String userName = data.child("username").getValue(String.class);
                String email = data.child("email").getValue(String.class);

                nameBox.setText(fullName);
                emailBox.setText(email);
                userNameBox.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Display the user's reputation
        db.child("Reputations").child(otherPersonsID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                // Get the current user's reputation
                if (data.exists()){
                    Reputation rep = data.getValue(Reputation.class);
                    // Calculate the user's rating and update their profile
                    ReputationCalculator calc = new ReputationCalculator();
                    calc.takeReputation(rep);
                    calc.calculateReputation();

                    //rep.calculateRating();
                    double score = rep.getTotalScore();
                    float fscore = (float) score;
                    userRating.setText(Double.toString(score));
                    userStars.setRating(fscore);
                    String count = Integer.toString(rep.getReviewCount());
                    ratingCount.setText("(" + count + ") ratings");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Intent to transaction page
        Button transaction = (Button) findViewById(R.id.transactionHistoryButton);
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OtherPersonsTransactionHistory.class);
                i.putExtra("userID",otherPersonsID);
                startActivity(i);
            }
        });

        // Intent to review page
        Button reviews = (Button) findViewById(R.id.otherReviews);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OtherPersonsReviews.class);
                i.putExtra("userID",otherPersonsID);
                startActivity(i);
            }
        });
    }
}