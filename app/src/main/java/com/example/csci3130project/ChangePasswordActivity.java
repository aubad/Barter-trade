package com.example.csci3130project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassText,newPassText;
    Button resetPasswordButton;
    TextView display;

    String retrievedPassword;
    String retrievedUserID;
    String retrievedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebase.getReference();

        oldPassText = findViewById(R.id.oldPasswordText);
        newPassText = findViewById(R.id.newPasswordText);
        resetPasswordButton = findViewById(R.id.resetPassButton);
        display = findViewById(R.id.displayTextView);


        db.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                String retrievedPassword = data.child("password").getValue(String.class);
                resetPasswordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPass = oldPassText.getText().toString().trim();
                        String newPass = newPassText.getText().toString().trim();

                        /*
                         * Conditions that evaluate if the information entered is correct or not
                         * If the conditions are all correct, then the password is updated
                         * */
                        if (oldPass.isEmpty()){
                            oldPassText.setError("Old Password is invalid!");
                            oldPassText.requestFocus();
                            display.setText("Old Password is Invalid!");
                            return;
                        }
                        else if ((oldPass.length() < 8) && (!oldPass.equals(retrievedPassword))){
                            oldPassText.setError("Old Password is invalid!");
                            oldPassText.requestFocus();
                            display.setText("Old Password is Invalid!");
                            return;
                        }
                        else if (newPass.isEmpty()) {
                            newPassText.setError("New Password is invalid!");
                            newPassText.requestFocus();
                            display.setText("New Password is Invalid!");
                            return;
                        }
                        else if (newPass.length() < 8){
                            newPassText.setError("New Password is invalid!");
                            newPassText.requestFocus();
                            display.setText("New Password is Invalid!");
                            return;
                        }
                        else if (newPass.equals(oldPass)){
                            display.setText("Passwords Cannot be the Same!");
                            return;
                        }
                        else if ((newPass.length() >= 8) && (oldPass.equals(retrievedPassword)) && (!oldPass.equals(newPass))){

                            String email = data.child("email").getValue(String.class);
                            String pass = data.child("password").getValue(String.class);

                            AuthCredential cred = EmailAuthProvider.getCredential(email, pass);

                            user.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        user.updatePassword(newPass);
                                        db.child("Users").child(user.getUid()).child("password").setValue(newPass);
                                        Toast.makeText(ChangePasswordActivity.this, "Password has been updated!", Toast.LENGTH_SHORT).show();
                                        //After the password update, the page closes
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(ChangePasswordActivity.this, "Password has not been updated!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}