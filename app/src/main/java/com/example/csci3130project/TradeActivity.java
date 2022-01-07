package com.example.csci3130project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TradeActivity extends AppCompatActivity {

    protected ArrayList<Item> myItems = new ArrayList<Item>();
    protected String[] itemSelection = new String[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        // get intents
        String userIDIntent = getIntent().getStringExtra("userID");
        String itemIDIntent = getIntent().getStringExtra("itemID");
        String userNameIntent = getIntent().getStringExtra("userName");
        String itemNameIntent = getIntent().getStringExtra("itemName");
        Integer itemValueIntent = getIntent().getIntExtra("itemValue", 0);
        String itemValue = itemValueIntent.toString();

        // create text views
        TextView tradePartner = findViewById(R.id.viewTradePartner);
        TextView tradeItem = findViewById(R.id.viewTradeItem);
        Spinner myItemChoice = (Spinner) findViewById(R.id.tradeItemChoice);
        myItems.clear();

        // get user items
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "current user: " + user.getUid());
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebase.getReference("Items");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String name = data.child("name").getValue(String.class);
                    String type = data.child("category").getValue(String.class);
                    String desc = data.child("description").getValue(String.class);
                    String id = data.getKey();
                    String userID = data.child("userID").getValue(String.class);
                    Boolean status = data.child("status").getValue(Boolean.class);
                    Integer itemValue = data.child("itemValue").getValue(Integer.class);

                    Double lat = data.child("latitude").getValue(Double.class);
                    Double lon = data.child("longitude").getValue(Double.class);

                    Item thisItem = new Item(userID, name, desc, type, itemValue, lon, lat, status);

                    Log.d(TAG, "other user: " + userID);

                    // if this item belongs to us, add it to the list
                    if (userID.equals(user.getUid())){
                        myItems.add(thisItem);

                    }
                }
                // create a selection dropdown using a spinner and the arraylist we created
                Spinner spinner = (Spinner) findViewById(R.id.tradeItemChoice);
                ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(getApplicationContext(), android.R.layout.simple_spinner_item, myItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                Log.d(TAG, "selected item (Inside): " + myItemChoice.getSelectedItem().toString());
                itemSelection[0] = (myItemChoice.getSelectedItem().toString().trim());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        // when the user clicks send
        Button send = (Button) findViewById(R.id.tradeBtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String me = user.getUid();
                DatabaseReference dbMain = firebase.getReference();
                DatabaseReference db = firebase.getReference("Items");
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()){
                            String name = data.child("name").getValue(String.class);
                            String type = data.child("category").getValue(String.class);
                            String desc = data.child("description").getValue(String.class);
                            String id = data.getKey();
                            String userID = data.child("userID").getValue(String.class);
                            Boolean status = data.child("status").getValue(Boolean.class);
                            Integer itemValue = data.child("itemValue").getValue(Integer.class);

                            Double lat = data.child("latitude").getValue(Double.class);
                            Double lon = data.child("longitude").getValue(Double.class);

                            // if the item belongs to us
                            if (userID.equals(user.getUid())){
                                Item thisItem = new Item(userID, name, desc, type, itemValue, lon, lat, status);
                                Log.d(TAG, "Item to trade " + thisItem);
                                Log.d(TAG, "Item I want: " + myItemChoice.getSelectedItem());
                                // if this item belongs to us, and is the selected dropdown item
                                if (thisItem.equals(myItemChoice.getSelectedItem())){
                                    Log.d(TAG, "SUCCESS");
                                    Integer myValue = itemValue;
                                    String myItem = data.getKey();

                                    // create the trade request
                                    TradeRequest newTrade = new TradeRequest(user.getUid(), userIDIntent, name, itemNameIntent, myItem, itemIDIntent, myValue, itemValueIntent, 0);

                                    // send the trade request
                                    dbMain.child("Trades").push().setValue(newTrade).addOnSuccessListener(success -> {
                                        Toast.makeText(getApplicationContext(), "Trade offer sent succesfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }).addOnFailureListener(fail -> {
                                        Toast.makeText(getApplicationContext(), "Item upload unsuccessful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    });
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });
        Log.d(TAG, "current user: " + myItems);

        // displays the trade info on the page
        tradePartner.setText(userNameIntent + "'s Item:");
        tradeItem.setText(itemNameIntent);
    }
}