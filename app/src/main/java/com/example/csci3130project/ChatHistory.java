package com.example.csci3130project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        ListView listView = findViewById(R.id.chatHistoryList);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference chatRef = firebase.getReference("Chat");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> previousContacts = new ArrayList<String>();
                ArrayList<String> previousContactsId = new ArrayList<>();
                for(DataSnapshot adSnapShot: snapshot.getChildren()){
                    String tempRecipientName = adSnapShot.child("recipientName").getValue(String.class);
                    String tempRecipientId = adSnapShot.child("recipientId").getValue(String.class);
                    String tempMessageSender = adSnapShot.child("userName").getValue(String.class);
                    String tempMessageSenderId = adSnapShot.child("userId").getValue(String.class);


                    System.out.println(tempRecipientName);

                    if(tempMessageSenderId.equals(userId)){
                        if(!previousContactsId.contains(tempRecipientId)){
                            previousContacts.add(tempRecipientName);
                            previousContactsId.add(tempRecipientId);
                        }
                    }
                    else if(tempRecipientId.equals(userId)){
                        if(!previousContactsId.contains(tempMessageSenderId)){
                            previousContacts.add(tempMessageSender);
                            previousContactsId.add(tempMessageSenderId);
                        }

                    }

                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,previousContacts);

                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        System.out.println("i: "+i);
                        Intent msgIntent = new Intent(getApplicationContext(), ChatActivity.class);
                        msgIntent.putExtra("userId",previousContactsId.get(i));
                        System.out.println("hello"+ previousContacts.get(i)+" "+previousContactsId.get(i));
                        msgIntent.putExtra("userFName",previousContacts.get(i));
                        startActivity(msgIntent);


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}