package com.example.csci3130project;

import android.app.Activity;
import android.os.Bundle;

public class MainPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
    }
    //Prevents users from going back after login in
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
