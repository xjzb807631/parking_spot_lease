package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.ClientSpotHandler;

public class parking_place extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<>();
    StringBuffer result=new StringBuffer();

    void doPost_inserting_spot()
    {
        Intent intent_parking_place_to_inserting_spot=new Intent(parking_place.this,inserting_spot.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_place);

        ListView contactsView=(ListView) findViewById(R.id.list_view);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
        contactsView.setAdapter(adapter);
        String user_id=Handlers.clientAccountHandler.user.userID;
        Handlers.clientSpotHandler.GetSpotByUser(Integer.valueOf(user_id),contactsList,result);

        Button plusing_spot_button=(Button) findViewById(R.id.plusing_spot);
        plusing_spot_button.setOnClickListener(view->doPost_inserting_spot());

    }
}
