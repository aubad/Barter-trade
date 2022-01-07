package com.example.csci3130project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    EditText messageBox;
    Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        LinearLayout lLayout = findViewById(R.id.linearLayout); // Root ViewGroup in which you want to add textviews
        ScrollView scroll = findViewById(R.id.scroll);



        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference().child("Chat");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent recipientIdIntent = getIntent();
                String recipientID = recipientIdIntent.getStringExtra("userId");
                int i = 0;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String tempUserId = snap.child("userId").getValue(String.class);
                    String tempRecipientId = snap.child("recipientId").getValue(String.class);

                    if ( (tempUserId.equals(userID) && tempRecipientId.equals(recipientID)) ||
                            (tempUserId.equals(recipientID) && tempRecipientId.equals(userID))) {

                        String msg = (String) snap.child("msg").getValue();
                        String sender= (String) snap.child("userName").getValue();

                        TextView tv = new TextView(ChatActivity.this);
                        tv.setText(sender);
                        tv.setId(i);
                        tv.setPadding(40, 30, 200, 0);
                        tv.setTextSize(17);
                        tv.setTypeface(null, Typeface.BOLD);
                        tv.setTextColor(Color.rgb(190, 0, 0));
                        lLayout.addView(tv);
                        i++;

                        tv = new TextView(ChatActivity.this); // Prepare textview object programmatically
                        tv.setText(msg);
                        tv.setId(i + 1);
                        tv.setPadding(40, 0, 0, 0);
                        tv.setTextSize(20);
                        tv.setTextColor(Color.rgb(0, 0, 0));
                        lLayout.addView(tv);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        messageBox =(EditText) findViewById(R.id.chatMessage);
        sendBtn = findViewById(R.id.chatSendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempMessage = messageBox.getText().toString().trim();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                DatabaseReference uidRef = firebase.getReference().child("Users").child(userId);
                System.out.println(uidRef);
                if(isEmptyMsg(tempMessage)){
                    Toast.makeText(getApplicationContext(),"Message field empty",Toast.LENGTH_LONG).show();
                    return;
                }

                System.out.println("Calling something "+getUserName());

                uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String firstNameFromDb = snapshot.child("firstName").getValue(String.class);
                        Intent recipientIdIntent = getIntent();
                        String recipientID = recipientIdIntent.getStringExtra("userId");
                        String recipientFName = recipientIdIntent.getStringExtra("userFName");
                        ProxyChat chat = new ProxyChat(tempMessage,userId,firstNameFromDb, recipientID,recipientFName);
                        Chat real = chat.getReal();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference().child("Chat");
                        databaseReference.push().setValue(real).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    int i=0;
                                    for(DataSnapshot snap: snapshot.getChildren()){
                                        i++;
                                    }

                                    TextView tv = new TextView(ChatActivity.this);
                                    tv.setText(firstNameFromDb);
                                    tv.setId(i);
                                    tv.setPadding(40, 30, 200, 0);
                                    tv.setTextSize(17);
                                    tv.setTypeface(null, Typeface.BOLD);
                                    tv.setTextColor(Color.rgb(190, 0, 0));
                                    lLayout.addView(tv);
                                    i++;

                                    tv = new TextView(ChatActivity.this); // Prepare textview object programmatically
                                    tv.setText(tempMessage);
                                    tv.setId(i + 1);
                                    tv.setPadding(40, 0, 0, 0);
                                    tv.setTextSize(20);
                                    tv.setTextColor(Color.rgb(0, 0, 0));
                                    lLayout.addView(tv);

                                    Toast.makeText(getApplicationContext(),"Chat uploaded",Toast.LENGTH_LONG).show();
                                    //clearing the text box here
                                    messageBox.setText("");
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Error not uploaded",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage()+" "+ error.getDetails(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public boolean isEmptyMsg(String msg){
        return msg.isEmpty();
    }
    public boolean noRecipient(String recipient){
        return recipient.isEmpty();

    }
    public String getUserName(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference uidRef = firebase.getReference().child("Users").child(userId);
        String [] userNameArr = new String[1];

        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String usernameFromDb = snapshot.child("username").getValue(String.class);
                userNameArr[0] = usernameFromDb;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return userNameArr[0];
    }



}