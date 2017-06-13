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
    void doPost_creating_offer()
    {
        Intent intent_order_to_creating_offer=new Intent(order.this,creating_offer.class);
        startActivity(intent_order_to_creating_offer);
    }
    void doPost_creating_proposal()
    {
        Intent intent_order_to_creating_proposal=new Intent(order.this,creating_offer.class);
        startActivity(intent_order_to_creating_proposal);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        renting_in_button=(Button) findViewById(R.id.renting_in_order);
        renting_out_button=(Button) findViewById(R.id.renting_out_order);
        creating_offer_button=(Button) findViewById(R.id.create_offer);
        creating_proposal_button=(Button) findViewById(R.id.create_proposal);
        renting_in_button.setOnClickListener(view->doPost_renting_in());

        renting_out_button.setOnClickListener(view->doPost_renting_out());

        creating_offer_button.setOnClickListener(view->doPost_creating_offer());

        creating_proposal_button.setOnClickListener(view->doPost_creating_proposal());



    }
}
