package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Handle the View Items Card click
        findViewById(R.id.view_items_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the View Items functionality
                Toast.makeText(DashboardActivity.this, "View Items clicked", Toast.LENGTH_SHORT).show();
                // Open another activity if necessary
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.transfer_items_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the View Items functionality
                Toast.makeText(DashboardActivity.this, "Transfer Items clicked", Toast.LENGTH_SHORT).show();
                // Open another activity if necessary
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.withdraw_items_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the View Items functionality
                Toast.makeText(DashboardActivity.this, "Withdraw Items clicked", Toast.LENGTH_SHORT).show();
                // Open another activity if necessary
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.register_items_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the View Items functionality
                Toast.makeText(DashboardActivity.this, "Register Items clicked", Toast.LENGTH_SHORT).show();
                // Open another activity if necessary
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Handle the View Bids Card click
        findViewById(R.id.view_bids_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the View Bids functionality
                Toast.makeText(DashboardActivity.this, "View Bids clicked", Toast.LENGTH_SHORT).show();
                // Open another activity if necessary
                Intent intent = new Intent(DashboardActivity.this, Bids.class);
                startActivity(intent);
            }
        });

        // Handle the View Notice Card click
        findViewById(R.id.view_notice_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the View Notice functionality
                Toast.makeText(DashboardActivity.this, "View Notice clicked", Toast.LENGTH_SHORT).show();
                // Open another activity if necessary
            }
        });
    }
}
