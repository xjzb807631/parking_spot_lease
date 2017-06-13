package com.example.acer.parking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class renting_out_order extends AppCompatActivity {

    Button confirm_order_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting_out_order);

        confirm_order_button=(Button) findViewById(R.id.confirm_order);
    }
}
