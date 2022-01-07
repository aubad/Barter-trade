package com.example.csci3130project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button regBtn;
    EditText fname, lname, editEmail, password, password2, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        //storing input values
        fname = findViewById(R.id.editRegFname);
        lname = findViewById(R.id.editRegLName);
        editEmail = findViewById(R.id.editRegEmail);
        password = findViewById(R.id.editRegPass);
        password2 = findViewById(R.id.editRegPassConfirm);
        username = findViewById(R.id.editUserName);

        Button reg = (Button) findViewById(R.id.regBtn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String pass2 = password2.getText().toString().trim();
                String firstName = fname.getText().toString().trim();
                String lastName = lname.getText().toString().trim();
                String userName = username.getText().toString().trim();

                // Validate user input and inform user of errors
                if (isNotComplete(userName, email, pass, pass2, firstName, lastName)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if(!isValidEmailAddress(email)){
                    Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if(!validatePasswordLength(pass)){
                    Toast.makeText(RegisterActivity.this, "Minimum password length is 8", Toast.LENGTH_SHORT).show();
                } else if (!passwordsMatch(pass,pass2)){
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    // learned how to do this via following the tutorial : https://www.youtube.com/watch?v=Z-RE1QuUWPg&ab_channel=CodeWithMazn
                    // Date: 18/11/2021 Author: Benjamin Chui & Majed Yousef
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                // Create regular-type user with factory
                                UserFactory factory = new UserFactory();
                                User user = factory.getUser("Regular");

                                // Add user data
                                user.setFirstName(firstName);
                                user.setLastName(lastName);
                                user.setUsername(userName);
                                user.setEmail(email);
                                user.setPassword(pass);

                                // Get database references
                                DatabaseReference repdb = FirebaseDatabase.getInstance().getReference("Reputations");
                                DatabaseReference userdb = FirebaseDatabase.getInstance().getReference("Users");
                                String ID = auth.getCurrentUser().getUid();

                                // Add this user object to the database
                                userdb.child(ID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                //Generate a reputation object for the new user
                                                repdb.child(ID).setValue(new Reputation(ID)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            // If the reputation is added successfully, inform user and return to login
                                                            Toast.makeText(getApplicationContext(), "User has been added!", Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                                            startActivity(i);
                                                        }
                                                        else {
                                                            Toast.makeText(getApplicationContext(), "Reputation failed to initialize.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "User has not been added. Try again.", Toast.LENGTH_SHORT).show();
                                            }
                                          }
                                      }
                                );
                            }
                        }
                    });
                }
            }
        });
    }

        /**
         * Checks if the current input string is empty
         * @param s the String the check
         * @return true if empty, false if not
         */
        public boolean isEmpty(String s){
            return (s == null || s.equals(""));
        }

        /**
         * Checks if any of the input fields are unfilled
         * @param fname The first name of the user
         * @param lname The last name of the user
         * @param email The email of the user
         * @param uname The username of the user
         * @param pass The password of the user
         * @return true if input is incomplete, false if they are all filled
         */
        public boolean isNotComplete(String uname, String email, String pass, String pass2, String fname, String lname){
            boolean empty = isEmpty(uname) || isEmpty(email) || isEmpty(pass) || isEmpty(pass2) || isEmpty(fname) || isEmpty(lname);
            return empty;
        }

        /**
         * Use regex to check that the user email is a valid email
         * @param emailAddress the email to check
         * @return true if the email is valid, false if not
         */
        public boolean isValidEmailAddress(String emailAddress) {
            Pattern pattern = Pattern.compile("^(.+)@(.+)$");
            Matcher matcher = pattern.matcher(emailAddress);
            return matcher.matches();
        }

        /**
         * Check that the user's password is long enough
         * @param password the proposed password
         * @return true if it's logn enough, false otherwise
         */
        public boolean validatePasswordLength(String password){
            return password.length()>=8;
        }

        /**
         * Check if the user's password confirmation matches
         * @param pass1 the first password input
         * @param pass2 the confirmation password input
         * @return true if the passwords match, false if they do not
         */
        public boolean passwordsMatch(String pass1, String pass2){
            return pass1.equals(pass2);
        }
}