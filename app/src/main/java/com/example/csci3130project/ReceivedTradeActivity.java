package com.example.csci3130project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceivedTradeActivity extends AppCompatActivity {

    protected  String tradeRequest;
    protected  String partnerItem;
    protected  String myOwnItem;
    protected  String partnerTradeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_trade);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebase.getReference();
        Button accept = (Button) findViewById(R.id.acceptBtn);
        Button reject = (Button) findViewById(R.id.rejectBtn);

        // disable the buttons by default
        accept.setEnabled(false);
        reject.setEnabled(false);

        // if user is not null check for trades
        if (user != null){
            TextView theirItem = findViewById(R.id.receivedItemText);
            TextView myItem = findViewById(R.id.sendingItemText);
            TextView theySend = findViewById(R.id.receivedTitle1);
            TextView youSend = findViewById(R.id.receivedTitle);
            TextView relVal = findViewById(R.id.receivedTitle2);
            TextView differential = findViewById(R.id.receivedTitle3);
            TextView diffNum = findViewById(R.id.receivedTitle4);
            TextView profitLoss = findViewById(R.id.receivedTitle5);
            TextView tradeOptions = findViewById(R.id.receivedTitle6);

            // create a list of valuation categories
            ArrayList<String> valuation = new ArrayList<String>();
            valuation.add("Very Poor");
            valuation.add("Poor");
            valuation.add("Even");
            valuation.add("Good");
            valuation.add("Very Good");

            // loop through the trades node
            db.child("Trades").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()){
                        String otherPersonID = data.child("userID").getValue(String.class);
                        String myID = data.child("partnerID").getValue(String.class);
                        String theirItemName = data.child("itemName").getValue(String.class);
                        String myItemName = data.child("partnerItemName").getValue(String.class);
                        String theirItemID = data.child("itemID").getValue(String.class);
                        String myItemID = data.child("partnerItemID").getValue(String.class);
                        Float theirValue = data.child("itemValue").getValue(Float.class);
                        Float myValue = data.child("partnerItemValue").getValue(Float.class);

                        // if we find a trade that pertains to us
                        if (myID.equals(user.getUid())){
                            // set the buttons back to enabled
                            accept.setEnabled(true);
                            reject.setEnabled(true);

                            tradeRequest = data.getKey();
                            partnerItem = theirItemID;
                            myOwnItem = myItemID;
                            partnerTradeID = otherPersonID;

                            theirItem.setText(theirItemName);
                            myItem.setText(myItemName);

                            // find the difference in value
                            int totalDiff = (int) (theirValue - myValue);
                            String totalDiffStr = Integer.toString(Math.abs(totalDiff));
                            diffNum.setText(totalDiffStr + " $");
                            tradeOptions.setText("");
                            theySend.setText("They Want To Trade Their:");
                            youSend.setText("For Your:");
                            relVal.setText("The Relative Value For You Is");

                            Log.d(TAG, "My Num " + myValue );
                            Log.d(TAG, "Their num " + theirValue );
                            Log.d(TAG, "The current difference" + (theirValue-myValue) );

                            // some elseIf statements to determine how good the trade is for the user based on value
                            if (totalDiff < 0){
                                // if the difference is in the negative, we are losing value
                                // there are different levels of loss that we define, as poor and very poor
                                profitLoss.setText("Your Loss Is");
                                Log.d(TAG, "The difference if neg" + (myValue-theirValue)/myValue );
                                if((myValue-theirValue)/myValue >= 0.5){
                                    differential.setText(valuation.get(0));
                                    differential.setTextColor(Color.parseColor("#811331"));
                                }
                                else if((myValue-theirValue)/myValue > 0.25){
                                    differential.setText(valuation.get(1));
                                    differential.setTextColor(Color.parseColor("#C41E3A"));
                                }
                                else if((myValue-theirValue)/myValue <= 0.25){
                                    differential.setText(valuation.get(2));
                                    differential.setTextColor(Color.parseColor("#5F9EA0"));
                                }
                            }
                            else if (totalDiff > 0) {
                                // if they are in the positive, we are gaining value
                                // the two levels are good and very good
                                // additionally there is an even value for both positive and negative if we are close in value
                                profitLoss.setText("Your Profit Is");
                                Log.d(TAG, "The difference if pos" + (theirValue-myValue)/theirValue );
                                if((theirValue-myValue)/theirValue >= 0.5){
                                    differential.setText(valuation.get(4));
                                    differential.setTextColor(Color.parseColor("#355E3B"));
                                }
                                else if((theirValue-myValue)/theirValue > 0.25){
                                    differential.setText(valuation.get(3));
                                    differential.setTextColor(Color.parseColor("#097969"));
                                }
                                else if((theirValue-myValue)/theirValue <= 0.25){
                                    differential.setText(valuation.get(2));
                                    differential.setTextColor(Color.parseColor("#5F9EA0"));
                                }
                            }
                            else {
                                // have a specific dialogue if the values are exactly equal in value
                                profitLoss.setText("The Values Are Equal");
                                differential.setText(valuation.get(2));
                                differential.setTextColor(Color.parseColor("#5F9EA0"));
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });

            // if the user hits accept
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // switch ownership of the two items
                    db.child("Items").child(partnerItem).child("userID").setValue(user.getUid());
                    db.child("Items").child(myOwnItem).child("userID").setValue(partnerTradeID);
                    db.child("Items").child(partnerItem).child("status").setValue(true);
                    db.child("Items").child(myOwnItem).child("status").setValue(true);

                    // delete the trade item
                    db.child("Trades").child(tradeRequest).setValue(null);

                    // let the user know the trade was accepted
                    Toast.makeText(getApplicationContext(), "Trade accepted!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), ReviewUser.class);
                    i.putExtra("TradeID", partnerTradeID);
                    startActivity(i);
                }
            });

            // if the user hits reject
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // delete the trade item
                    db.child("Trades").child(tradeRequest).setValue(null);

                    Toast.makeText(getApplicationContext(), "Trade Declined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}