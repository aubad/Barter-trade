package com.example.csci3130project;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebase.getReference();
        String userID = db.child("Users").child(user.getUid()).getKey();

        TextView accountProfit = findViewById(R.id.accountDetail);
        TextView itemsSold = findViewById(R.id.itemSoldDetail);

        if (user != null) {

            TextView nameBox = findViewById(R.id.fullNameTextTrans);
            TextView emailBox = findViewById(R.id.emailTextTrans);
            TextView userNameBox = findViewById(R.id.userNameTextTrans);

            db.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot data) {
                    String fName = data.child("firstName").getValue(String.class);
                    String lName = data.child("lastName").getValue(String.class);
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
        }
        ArrayList<Integer> prices = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot adSnapshot: snapshot.getChildren()){
                    String name = adSnapshot.child("name").getValue(String.class);
                    String category = adSnapshot.child("category").getValue(String.class);
                    String itemsUserID = adSnapshot.child("userID").getValue(String.class);
                    String description = adSnapshot.child("description").getValue(String.class);
                    Double lat = adSnapshot.child("latitude").getValue(Double.class);
                    Double lon = adSnapshot.child("longitude").getValue(Double.class);
                    Integer itemValue = adSnapshot.child("itemValue").getValue(Integer.class);
                    Boolean status = adSnapshot.child("status").getValue(Boolean.class);
                    Item item = new Item(itemsUserID,name,description,category,itemValue,lon,lat,status);
                    String holder = "";
                    //Checks if item is sold and it belongs to the intended user
                    if (item.getStatus() && (userID.equals(item.getUserID()))) {
                        prices.add(item.getItemValue());
                        items.add(item);
                        Log.v("Sum added", "" + item.getItemValue());
                        Log.v("item added", "" + item.toString());
                    }
                }
                //Calculates the total money made on the account
                Log.v("Array Size", "" + prices.size());
                int sum = 0;
                for (int i =0; i < prices.size(); i ++){
                    sum += prices.get(i);
                }
                String finalTotal = "$ "+ sum;
                accountProfit.setText(finalTotal);
                prices.clear();

                //Printing the items sold by user
                StringBuilder builder = new StringBuilder();
                for (int y = 0; y < items.size(); y ++){
                    builder.append(items.get(y).toString()).append("\n");
                }
                itemsSold.setMovementMethod(new ScrollingMovementMethod());
                itemsSold.setText(builder.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}