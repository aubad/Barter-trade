package com.example.csci3130project;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//import com.example.csci3130project.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notifManager;
    TextView notifHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifManager = NotificationManagerCompat.from(this);
        notifHolder = findViewById(R.id.successView);
        notifHolder.setText("");

        // Intents

        // To Profile
        Button profile = (Button) findViewById(R.id.profBtn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
        ImageView profileImage = (ImageView) findViewById(R.id.profImg);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });

        // To Map
        Button map = (Button) findViewById(R.id.mapBtn);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });
        ImageView mapImage = (ImageView) findViewById(R.id.mapImg);
        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });

        // To Search
        Button search = (Button) findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), searchHomeActivity.class);
                startActivity(i);
            }
        });
        ImageView searchImage = (ImageView) findViewById(R.id.searchImg);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), searchHomeActivity.class);
                startActivity(i);
            }
        });

        // To Upload
        Button upload = (Button) findViewById(R.id.uploadBtn);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(i);
            }
        });
        ImageView uploadImage = (ImageView) findViewById(R.id.itemBtn);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(i);
            }
        });

        // To Alerts
        Button alert = (Button) findViewById(R.id.notifBtn);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ReceivedTradeActivity.class);
                startActivity(i);
            }
        });
        ImageView notifImage = (ImageView) findViewById(R.id.notifImg);
        notifImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ReceivedTradeActivity.class);
                startActivity(i);
            }
        });

        // To Chat
        Button chat = (Button) findViewById(R.id.chatBtn);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChatHistory.class);
                startActivity(i);
            }
        });
        ImageView chatImage = (ImageView) findViewById(R.id.chatImg);
        chatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChatHistory.class);
                startActivity(i);
            }
        });

        // Send alert
        Button refresh = (Button) findViewById(R.id.refreshBtn);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "Scan Complete!";
                String message = "See local items.";
                Intent i = new Intent(getApplicationContext(), NotificationActivity.class);
                PendingIntent pI = PendingIntent.getActivity(getApplicationContext(), 0, i,0);
                Notification notification = new NotificationCompat.Builder(getApplicationContext(),Notify.Channel_1)
                        .setSmallIcon(R.drawable.notify_me).setContentTitle(title)
                        .setContentText(message).setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pI).build();
                notifManager.notify(1,notification);
                notifHolder.setText(title);
            }
        });
    }

    // Disable going back to login
    @Override
    public void onBackPressed() { }
}