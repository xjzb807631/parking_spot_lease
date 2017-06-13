package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class order extends AppCompatActivity {

    Button renting_in_button;
    Button renting_out_button;
    Button creating_offer_button;
    Button creating_proposal_button;

    void doPost_renting_in()
    {
        Intent intent_to_renting_in=new Intent(order.this,renting_in_order.class);
        startActivity(intent_to_renting_in);
    }
    void doPost_renting_out()
    {
        Intent intent_to_renting_out=new Intent(order.this,renting_out_order.class);
        startActivity(intent_to_renting_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        renting_in_button=(Button) findViewById(R.id.renting_in_order);
        renting_out_button=(Button) findViewById(R.id.renting_out_order);
        renting_in_button.setOnClickListener(view->doPost_renting_in());
        renting_out_button.setOnClickListener(view->doPost_renting_out());

    }
}
