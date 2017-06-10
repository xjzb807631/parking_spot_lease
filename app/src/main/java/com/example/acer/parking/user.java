package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import client.ClientAccountHandler;
import data.Account;

public class user extends AppCompatActivity {

    private Button mLicensePlateButton;
    private Button mOrderButton;
    private Button mPersonalInformationButton;
    private Button mParkingButton;
    private Button mSearchButton;
    void doPost_LicensePlate()
    {
        Intent intent_user_to_LicensePlate = new Intent(user.this,license_plate.class) ;
        startActivity(intent_user_to_LicensePlate);
    }
    void doPost_Order()
    {
        Intent intent_user_to_order = new Intent(user.this,order.class) ;
        startActivity(intent_user_to_order);
    }
    void doPost_PersonalInformation()
    {
        Intent intent_user_to_personalInformationr = new Intent(user.this,personal_information.class) ;
        startActivity(intent_user_to_personalInformationr);
    }
    void doPost_ParkingPlace()
    {
        Intent intent_user_to_parking_place = new Intent(user.this,parking_place.class) ;
        startActivity(intent_user_to_parking_place);
    }
    void doPost_Search()
    {
        Intent intent_user_to_searching = new Intent(user.this,searching.class) ;
        startActivity(intent_user_to_searching);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        mLicensePlateButton=(Button) findViewById(R.id.my_license_plate);
        mOrderButton=(Button) findViewById(R.id.my_order);
        mPersonalInformationButton=(Button) findViewById(R.id.my_information);
        mParkingButton=(Button) findViewById(R.id.my_parking_place);
        mSearchButton=(Button) findViewById(R.id.search);

        mLicensePlateButton.setOnClickListener(view->doPost_LicensePlate());

        mOrderButton.setOnClickListener(view->doPost_Order());

        mPersonalInformationButton.setOnClickListener(view->doPost_PersonalInformation());

        mParkingButton.setOnClickListener(view->doPost_ParkingPlace());

        mSearchButton.setOnClickListener(view->doPost_Search());

    }
}

