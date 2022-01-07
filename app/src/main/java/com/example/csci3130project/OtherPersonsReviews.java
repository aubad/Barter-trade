package com.example.csci3130project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OtherPersonsReviews extends AppCompatActivity {

    ArrayList<String> reviewList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_reviews);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        String userIDIntent = getIntent().getStringExtra("userID");
        String otherPersonsID = userIDIntent.toString();

        ListView listView = (ListView) findViewById(R.id.list);
        TextView status = findViewById(R.id.status);
        reviewList = new ArrayList<String>();

        if (user != null) {
            // Retrieve reviews for this user's reputation
            db.child("Reputations").child(otherPersonsID).child("reviews").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot data) {
                    // Get the current user's reviews
                    if (data.exists()){
                        for (DataSnapshot d:data.getChildren()){
                            // Get each review for this reputation
                            Review review = d.getValue(Review.class);
                            if (review == null) {
                                status.setText("Reviews unavailable.");
                            } else {
                                status.setText("User reviews");
                                // Get data for each review
                                String count = Double.toString(review.getRating());
                                String submitter = review.getSubmitter();
                                String comment = review.getComment();

                                // Build review string and add to review list
                                String ratingText = "RATING: " + count;
                                String reviewText = ratingText + System.lineSeparator() + comment + System.lineSeparator() + "By: " + submitter;
                                reviewList.add(reviewText);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        // Display list of reviews for this user
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reviewList);
        listView.setAdapter(adapter);
    }
}
