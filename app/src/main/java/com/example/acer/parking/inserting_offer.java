package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import data.Order;

public class inserting_offer extends AppCompatActivity {

    EditText start_time;
    EditText stop_time;
    EditText amount;
    StringBuffer result=new StringBuffer();

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_offer);

        start_time=(EditText) findViewById(R.id.edit_start_time);
        stop_time=(EditText) findViewById(R.id.edit_stop_time);
        amount=(EditText) findViewById(R.id.edit_offer_price);

        String m_start_time=start_time.getText().toString().trim();
        String m_stop_time=stop_time.getText().toString().trim();
        String m_amount=amount.getText().toString().trim();
       //todo  String spot_id=

       //todo Order order=new Order(m_start_time,m_stop_time,spot_id,amount);
        //todo Handlers.clientMainHandler.createOffer(order,result);
       //todo Intent intent_inserting_offer_to_renting_out_order=new Intent(inserting_offer.this,renting_out_order.class);
       // todo startActivity(intent_inserting_offer_to_renting_out_order);
    }
}
