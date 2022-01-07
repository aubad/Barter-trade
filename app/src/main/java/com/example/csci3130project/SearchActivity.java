package com.example.csci3130project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    Spinner filter;

    ArrayList<String> locations;
    ArrayList<String> categories;

    Double latitude;
    Double longitude;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String catChoice = getIntent().getStringExtra("category");


        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);

        //A list to keep track of the items, and another list to keep track of the item locations in the list
        list = new ArrayList<>();
        locations = new ArrayList<>();
        categories = new ArrayList<>();

        // Creating the searchview and listeview object by finding the searchview and listview from the uI.
        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebase.getReference();
        db.child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                if (data.exists()){
                    // clear the current array
                    list.clear();
                    for (DataSnapshot d:data.getChildren()){
                        latitude = d.child("latitude").getValue(Double.class);
                        longitude = d.child("longitude").getValue(Double.class);
                        String userID = data.child("userID").getValue(String.class);
                        //Adds the item location to the list based on the position in search, so it will be tied to the correct item / location

                        Boolean status2 = d.child("status").getValue(Boolean.class);

                        // Keep track of item categories
                        category = d.child("category").getValue(String.class);
                        categories.add(category);

                        String itemName = d.child("name").getValue(String.class);

                                /* Adds product location lat and long to search display (using to visualize)
                                + "\n LOCATION: Lat: " + latitude
                                + " Long: " + longitude;*/
                        if (!status2 && catChoice.equals(category) && !user.getUid().equals(userID)) {
                            list.add(itemName);
                            locations.add(latitude + " " + longitude);
                            // If no category is selected, use the generic list
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
                            listView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createSearchBar();
    }

    private void createSearchBar() {

        //Creating on click listener for items in search list, user clicks item and gets sent to map location.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
                Log.d(TAG, "Item location: " + latitude + " " + longitude);
                intent.putExtra("itemLocation", locations.get(i));
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                intent.putExtra("itemClicked", 1);
                startActivity(intent);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //This method is performed when the user clicks the enter button and "submits" the text. this can be modified to do other things
            @Override
            public boolean onQueryTextSubmit(String query) {

//                if (list.contains(query)) {
//                    adapter.getFilter().filter(query);
//                } else {
//                    Toast.makeText(SearchActivity.this, "No Match found", Toast.LENGTH_SHORT).show();
//                }

                return false;
            }

            //This is the method that takes in the text as the user is typing and calls the filter method to see if there are items available regarding what the user is typing.
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}
