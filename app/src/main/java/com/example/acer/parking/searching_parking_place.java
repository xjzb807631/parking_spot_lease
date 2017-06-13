package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.ClientMainHandler;
import client.ClientSpotHandler;

public class searching_parking_place extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<>();
    StringBuffer result=new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_parking_place);

        Intent intent=getIntent();
        String m_province=intent.getStringExtra("m_province");
        String m_district=intent.getStringExtra("m_district");
        String m_block=intent.getStringExtra("m_block");
        String m_start_time=intent.getStringExtra("m_start_time");
        String m_stop_time=intent.getStringExtra("m_stop_time");

        ListView contactsView=(ListView) findViewById(R.id.list_view);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
        contactsView.setAdapter(adapter);
        ClientMainHandler clientMainHandler=new ClientMainHandler();
      //todo  clientMainHandler.searchAvailableSpot();
    }
}
