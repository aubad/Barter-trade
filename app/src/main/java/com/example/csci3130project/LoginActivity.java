package com.example.csci3130project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText userEmail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPassword);
        auth = FirebaseAuth.getInstance();

        Button reg = (Button) findViewById(R.id.regButton);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        Button login = (Button) findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if (email.isEmpty()){
                    userEmail.setError("Must enter an email.");
                    userEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    pass.setError("Must enter a password.");
                    pass.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmail.setError("This is not a valid email!");
                    userEmail.requestFocus();
                    return;
                }

                // learned how to do this via following the tutorial : https://www.youtube.com/watch?v=KB2BIm_m1Os&ab_channel=CodeWithMazn
                // Date: 18/11/2021 Author: Benjamin Chui & Majed Yousef
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Failed to log in. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}