package com.example.csci3130project;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


public class NotificationActivity extends AppCompatActivity {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    ArrayList<String> favourites = new ArrayList<String>();
    ArrayList<String> allFavourites = new ArrayList<String>();
    ListView listView;
    ArrayAdapter<String > adapter;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionGranted = false;
    private static final Location currentLocale = new Location("Current Location");

    Double latitude;
    Double longitude;
    ArrayList<String> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getLocationPermission();
        getDeviceLocation();

        listView = (ListView) findViewById(R.id.lv2);
        locations = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebase.getReference();

        if (db.child("Favourites") != null) {

            db.child("Favourites").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot data) {
                    for(DataSnapshot adSnapshot: data.getChildren()){
                        favourites.add((String) adSnapshot.getValue());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if (db.child("Users") != null){
            DatabaseReference dbItems = firebase.getReference("Items");
            dbItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String type = snapshot.child("category").getValue(String.class);
                        String id = snapshot.getKey();
                        String userID = snapshot.child("userID").getValue(String.class);
                        Boolean status = snapshot.child("status").getValue(Boolean.class);
                        String category = snapshot.child("category").getValue(String.class);
                        Integer itemValue = snapshot.child("itemValue").getValue(Integer.class);

                        Double lat = snapshot.child("latitude").getValue(Double.class);
                        Double lon = snapshot.child("longitude").getValue(Double.class);



                        Log.d(TAG, "Lat and Long: " + lat + " " + lon);
                        Log.d(TAG, "Name and Category: " + name + " " + category);

                        Location itemLocale = new Location("Item Location");

                        itemLocale.setLatitude(lat);
                        itemLocale.setLongitude(lon);

                        //getDeviceLocation();
                        Log.d(TAG, "current locale: " + currentLocale);

                        float distance = currentLocale.distanceTo(itemLocale);
                        Log.d(TAG, "DISTANCE BETWEEN: " + distance);

                        if (!userID.equals(user.getUid())) {
                            if (favourites.contains(category) && !status) {
                                locations.add(lat + " " + lon);
                                // if we are within 15km then
                                if ((distance / 1000) <= 15) {
                                allFavourites.add(name);
                                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, allFavourites);
                                listView.setAdapter(adapter);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NotificationActivity.this, MapsActivity.class);
                intent.putExtra("itemLocation", locations.get(i));
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                intent.putExtra("itemClicked", 1);
                startActivity(intent);

            }
        });
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission : starts");
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                Log.d(TAG, "getLocationPermission : Permissions already granted");
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions, LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "getLocationPermission : Request for permissions");
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_REQUEST_CODE);
            Log.d(TAG, "getLocationPermission : Request for permissions");
        }
        Log.d(TAG, "getLocationPermission :  ends");
    }

    public void getDeviceLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "getDeviceLocation: onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            if(currentLocation != null) {
                                Log.d(TAG, "getDeviceLocation: currentLocation Lattitude: " + currentLocation.getLatitude());
                                Log.d(TAG, "getDeviceLocation: currentLocation Longitude: " + currentLocation.getLongitude());
                                currentLocale.setLatitude(currentLocation.getLatitude());
                                currentLocale.setLongitude(currentLocation.getLongitude());
                            }else
                                Log.d(TAG, "getDeviceLocation: Current location is null");
                        }else {
                            Log.d(TAG, "getDeviceLocation: Current location is null");
                        }
                    }
                });
            }
        } catch (SecurityException se) {
            Log.d(TAG, "getDeviceLocation: SecurityException: =" + se.getMessage());
        }
    }
}

