package com.example.csci3130project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class searchHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home);

        // Retrieve category checkboxes
        Button category1 = findViewById(R.id.searchCategory1);
        Button category2 = findViewById(R.id.searchCategory2);
        Button category3 = findViewById(R.id.searchCategory3);
        Button category4 = findViewById(R.id.searchCategory4);
        Button category5 = findViewById(R.id.searchCategory5);
        Button category6 = findViewById(R.id.searchCategory6);
        Button category7 = findViewById(R.id.searchCategory7);

        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("category", category1.getText().toString());
                startActivity(i);
            }
        });

        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("category", category2.getText().toString());
                startActivity(i);
            }
        });

        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("category", category3.getText().toString());
                startActivity(i);
            }
        });

        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("category", category4.getText().toString());
                startActivity(i);
            }
        });

        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("category", category5.getText().toString());
                startActivity(i);
            }
        });

        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("category", category6.getText().toString());
                startActivity(i);
            }
        });

        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("category", category7.getText().toString());
                startActivity(i);
            }
        });
    }
}