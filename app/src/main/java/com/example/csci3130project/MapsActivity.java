package com.example.csci3130project;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.csci3130project.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
The code below was originally created by Shakuntala Khatri
It was further edited to this project by Benjamin Chui
The original code is available at: https://git.cs.dal.ca/prof3130/google-map-advance-demo
Date Accessed: 26th Oct. 2021
*/

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final float DEFAULT_ZOOM = 17;
    private static float currentZoom = DEFAULT_ZOOM;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static ArrayList<String> itemsList = new ArrayList<String>();
    Marker marker;



    double productLat;
    double productLong;
    Double productLat2;
    Double productLong2;
    String itemlocation = "empty ";
    Integer itemclicked = 0;
    String[] splitLocation;

    // code for retrieving items implemented by Hesham Elokdah and refactored by Benjamin Chui
    public boolean pinItems(GoogleMap googleMap) {

        // calling add value event listener method
        // for getting the values from database.

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Items");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot adSnapshot: snapshot.getChildren()){

                    String name = adSnapshot.child("name").getValue(String.class);
                    String type = adSnapshot.child("category").getValue(String.class);
                    String id = adSnapshot.getKey();
                    String userID = adSnapshot.child("userID").getValue(String.class);
                    Boolean status = adSnapshot.child("status").getValue(Boolean.class);

                    Double lat = adSnapshot.child("latitude").getValue(Double.class);
                    Double lon = adSnapshot.child("longitude").getValue(Double.class);

                    LatLng loc = new LatLng(lat, lon);

                    if (!userID.equals(user.getUid())){
                        if(!status){
                            marker = googleMap.addMarker(new MarkerOptions().position(loc).title(name).snippet(id));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }

        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: Starts");
        super.onCreate(savedInstanceState);

        //Search intent variables, when a user has clicked a item from the search activity these variables
        //Are populated to move the users camera to product location.
        Intent searchIntent = getIntent();
        itemclicked = searchIntent.getIntExtra("itemClicked", 0);

        if(itemclicked == 1){
            itemlocation = searchIntent.getStringExtra("itemLocation");
            splitLocation = itemlocation.split(" ");
            productLat = Double.parseDouble(splitLocation[0]);
            productLong = Double.parseDouble(splitLocation[1]);
        }


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLocationPermission();
        Log.d(TAG, "onCreate: Ends");

        // To Search
        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.mapToSearchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        // To Zoom Out
        FloatingActionButton zoomOut = (FloatingActionButton) findViewById(R.id.zoomOutBtn);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentZoom -= 1;
                getDeviceLocation(currentZoom);
            }
        });

        // To Zoom In
        FloatingActionButton zoomIn = (FloatingActionButton) findViewById(R.id.zoomInBtn);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentZoom += 1;
                getDeviceLocation(currentZoom);
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
                initMap();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Requesting for permissions");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    Log.d(TAG, "onRequestPermissionsResult: permissions given by user");
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: starts");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.d(TAG, "initMap: ends");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: starts");
        mMap = googleMap;
        Toast.makeText(this, "Location Services Are Active", Toast.LENGTH_SHORT).show();
        if (mLocationPermissionGranted) {
            Log.d(TAG, "onMapReady: getting Device current location!!");
            getDeviceLocation(DEFAULT_ZOOM);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            pinItems(mMap);

            // https://www.youtube.com/watch?v=m6zcM6Q2qZU&ab_channel=GadgetsandTechnicalfieldAndroidTech
            // https://developers.google.com/maps/documentation/android-sdk/infowindows
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    String itemID = marker.getSnippet();

                    Intent i = new Intent(getApplicationContext(), ItemDetailsActivity.class);
                    i.putExtra("snippet", itemID);
                    startActivity(i);
                }
            });

        }
    }

    public void getDeviceLocation(float zoomLevel){
        Log.d(TAG, "getDeviceLocation: starts");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "getDeviceLocation: onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            if(itemclicked == 1){
                                moveCamera(new LatLng(productLat, productLong), zoomLevel,"current location");
                                return;
                            }
                            if(currentLocation != null) {
                                Log.d(TAG, "getDeviceLocation: currentLocation Lattitude: " + currentLocation.getLatitude());
                                Log.d(TAG, "getDeviceLocation: currentLocation Longitude: " + currentLocation.getLongitude());
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        zoomLevel,"current location");
                            }
                            else
                                Log.d(TAG, "getDeviceLocation: Current location is null");
                            //moveCamera(new LatLng(productLat2, productLong2), zoomLevel,"current location");

                        }else {
                            Log.d(TAG, "getDeviceLocation: Current location is null");
                            Toast.makeText(MapsActivity.this, "Unable to get curent location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException se){
            Log.d(TAG, "getDeviceLocation: SecurityException: =" + se.getMessage());
        }
        Log.d(TAG, "getDeviceLocation: ends");
    }

    public void moveCamera(LatLng latlng, float zoom, String title){
        Log.d(TAG, "moveCamera: starts with latitude: "+ latlng.latitude + " and Longitude: " + latlng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));
    }

    // Check Services are working fine or not
    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: Google Services is working fine");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: An error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You cannot make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}


