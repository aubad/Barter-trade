package com.example.csci3130project;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notify extends Application {
    public static final String Channel_1 = "A new Item has been added, have a look!";

    @Override
    public void onCreate() {
        super.onCreate();
        makeNotificationChannel();
    }

    private void makeNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel chan1 = new NotificationChannel(Channel_1,"Channel 1", NotificationManager.IMPORTANCE_HIGH);
            chan1.setDescription("This is channel 1");
            NotificationManager man = getSystemService(NotificationManager.class);
            man.createNotificationChannel(chan1);
        }
    }
}
