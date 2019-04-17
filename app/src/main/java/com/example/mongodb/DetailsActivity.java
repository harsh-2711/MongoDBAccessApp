package com.example.mongodb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

   String propertyName, propertyLocation, propertyType, propertyAmount, contractType, floor, carpetArea, state, city, description;

   TextView name, location, type, amount, contract, floor_text, carpet, state_text, city_text, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = (TextView) findViewById(R.id.Name);
        location = (TextView) findViewById(R.id.location);
        type = (TextView) findViewById(R.id.type);
        amount = (TextView) findViewById(R.id.amount);
        contract = (TextView) findViewById(R.id.contract);
        floor_text = (TextView) findViewById(R.id.floor);
        carpet = (TextView) findViewById(R.id.carpet);
        state_text = (TextView) findViewById(R.id.state);
        city_text = (TextView) findViewById(R.id.city);
        desc = (TextView) findViewById(R.id.description);

        Intent intent = getIntent();

        propertyName = intent.getStringExtra("Place");
        name.setText("Propert Name: " + propertyName);
        propertyLocation = intent.getStringExtra("PropertyLocation");
        location.setText("Property Location: " + propertyLocation);
        propertyType = intent.getStringExtra("PropertyType");
        type.setText("Property Type: " + propertyType);
        propertyAmount = intent.getStringExtra("ProeprtyAmount");
        amount.setText("Proeprty Amount: " + propertyAmount);
        contractType = intent.getStringExtra("ContractType");
        contract.setText("Contract Type: " + contractType);
        floor = intent.getStringExtra("Floor");
        floor_text.setText("Floor: " + floor);
        carpetArea = intent.getStringExtra("CarpetArea");
        carpet.setText("Carpet Area: " + carpetArea);
        city = intent.getStringExtra("City");
        city_text.setText("City: " + city);
        state = intent.getStringExtra("State");
        state_text.setText("State: " + state);
        description = intent.getStringExtra("Description");
        desc.setText("Description: " + description);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
