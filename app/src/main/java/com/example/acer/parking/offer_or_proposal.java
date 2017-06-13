package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class offer_or_proposal extends AppCompatActivity {

    Button offer_button;
    Button proposal_button;
    void doPost_create_offer()
    {
        Intent intent_offer_or_proposal_to_creating_offer=new Intent(offer_or_proposal.this,inserting_offer.class);
        startActivity(intent_offer_or_proposal_to_creating_offer);
    }
    void doPost_create_proposal()
    {
        Intent intent_offer_or_proposal_to_creating_proposal=new Intent(offer_or_proposal.this,creating_proposal.class);
        startActivity(intent_offer_or_proposal_to_creating_proposal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_or_proposal);

        offer_button=(Button) findViewById(R.id.create_offer);
        proposal_button=(Button) findViewById(R.id.create_proposal);

        offer_button.setOnClickListener(view->doPost_create_offer());

        proposal_button.setOnClickListener(view->doPost_create_proposal());


    }
}
