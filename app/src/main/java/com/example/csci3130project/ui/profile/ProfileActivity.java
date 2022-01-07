package com.example.csci3130project.ui.profile;
import java.util.*;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130project.R;
//import com.example.csci3130project.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity{

    TextView profileEmail,profileFullName,profileUserName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView profileEmail = findViewById(R.id.EmailText);
        TextView profileFullName = findViewById(R.id.FullNameText);
        TextView profileUserName = findViewById(R.id.UserNameText);
        Button changePassword = findViewById(R.id.changePassBtn);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = "Password";
                System.out.println(pass);
            }
        });
        Button transactionHistory = findViewById(R.id.transactionHistoryButton);
        transactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button logoutProfile = findViewById((R.id.logOutButtonProfile));
        logoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button settings = findViewById(R.id.logOutButtonProfile);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public String getProfileEmail() {
        profileEmail = findViewById(R.id.EmailText);
        return profileEmail.getText().toString().trim();
    }

    public String getProfileFullName() {
        profileFullName = findViewById(R.id.FullNameText);
        return profileFullName.getText().toString().trim();
    }

    public String getProfileUserName() {
        profileUserName = findViewById(R.id.UserNameText);
        return profileUserName.getText().toString().trim();
    }

    public void updateProfileEmail(String email){
        TextView profileEmail = findViewById(R.id.EmailText);
        profileEmail.setText(email);
    }

    public void updateProfileFullName(String first, String second){
        profileFullName.setText(first +" "+second);
    }

    public void updateProfileUserName(String userName){
        profileUserName.setText(userName);
    }


}
