package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class parking_place extends AppCompatActivity {

    void doPost_inserting_spot()
    {
        Intent intent_parking_place_to_inserting_spot=new Intent(parking_place.this,inserting_spot.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_place);



        Button plusing_spot_button=(Button) findViewById(R.id.plusing_spot);
        plusing_spot_button.setOnClickListener(view->doPost_inserting_spot());

    }
}
