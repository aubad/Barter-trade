package com.example.csci3130project;

import static android.content.ContentValues.TAG;

import android.app.Notification;
import android.app.NotificationManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    EditText mItemName, mItemDescription;
    EditText mItemValue;
    Spinner mItemCat;
    Button mButton;
    private Double[] location;
    private LocationRequest locationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mItemName = (EditText) findViewById(R.id.uploadItemName);
        mItemValue = (EditText) findViewById(R.id.uploadItemValue);
        mItemDescription = (EditText) findViewById(R.id.uploadItemdesc);
        mButton = (Button) findViewById(R.id.uploadBtn);
        mItemCat = (Spinner) findViewById(R.id.uploadItemCat);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.uploadItemCat);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemCatArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }


    public void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: starts");
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {

            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "getDeviceLocation: onComplete: found location");
                        Location currentLocation = (Location) task.getResult();
                        if (currentLocation != null) {
                            String itemName = mItemName.getText().toString().trim();
                            String itemDescription = mItemDescription.getText().toString().trim();
                            int itemValue = Integer.parseInt(mItemValue.getText().toString().trim());
                            String itemCategory = mItemCat.getSelectedItem().toString().trim();
                            double lat = currentLocation.getLatitude();
                            double longi = currentLocation.getLongitude();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                            DatabaseReference db = firebase.getReference();
                            String userID = db.child("Users").child(user.getUid()).getKey();

                            Item item = new Item(userID, itemName, itemDescription, itemCategory, itemValue, longi, lat,false);

                            db.child("Items").push().setValue(item).addOnSuccessListener(success -> {
                                Toast.makeText(getApplicationContext(), "Item uploaded successfully", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(fail -> {
                                Toast.makeText(getApplicationContext(), "Item upload unsuccessful", Toast.LENGTH_SHORT).show();
                            });

                            finish();
                        } else
                            Log.d(TAG, "getDeviceLocation: Current location is null");
                    } else {
                        Log.d(TAG, "getDeviceLocation: Task was not successful");
                    }
                }
            });

        } catch (SecurityException se) {
            Log.d(TAG, "getDeviceLocation: SecurityException: =" + se.getMessage());
        }
        Log.d(TAG, "getDeviceLocation: ends");
    }
}



