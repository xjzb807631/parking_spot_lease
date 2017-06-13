package com.example.acer.parking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Spinner;

import data.*;
import client.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import client.ClientMainHandler;
import client.ClientSpotHandler;

public class searching extends AppCompatActivity {

    private EditText mProvince;
    private EditText mDistrict;
    private EditText mBlock;
    private EditText mStartTime;
    private EditText mStopTime;

    private ArrayAdapter<String> province_adapter;
    private ArrayAdapter<String> district_adapter;
    private ArrayAdapter<String> block_adapter;
    private ArrayAdapter<String> start_time_adapter;
    private ArrayAdapter<String> stop_time_adapter;

    private List<String> province_contactsList=new ArrayList<>();
    private List<String> district_contactsList=new ArrayList<>();
    private List<String> block_contactsList=new ArrayList<>();
    private List<String> start_time_contactsList=new ArrayList<>();
    private List<String> stop_time_contactsList=new ArrayList<>();

    void doPost_confirm()
    {

        Area area=new Area();
        ClientMainHandler clientMainHandler=new ClientMainHandler();
        Intent intent_searching_to_parking_place = new Intent(searching.this,parking_place.class) ;
        startActivity(intent_searching_to_parking_place);
    }
    void doPost_cancel()
    {
        Intent intent_searching_to_user=new Intent(searching.this,user.class);
        startActivity(intent_searching_to_user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        mProvince=(EditText) findViewById(R.id.edit_province);
        mDistrict=(EditText) findViewById(R.id.edit_district);
        mBlock=(EditText) findViewById(R.id.edit_block_name);
        mStartTime=(EditText) findViewById(R.id.edit_start_time);
        mStopTime=(EditText) findViewById(R.id.edit_stop_time);

        province_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,province_contactsList);
        district_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,district_contactsList);
        block_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,block_contactsList);
        start_time_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,start_time_contactsList);
        stop_time_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stop_time_contactsList);

        Button mConfirmButton = (Button) findViewById(R.id.confirm);
        Button mCancelButton = (Button) findViewById(R.id.cancel);

        mConfirmButton.setOnClickListener(view->doPost_confirm());

        mCancelButton.setOnClickListener(view->doPost_cancel());

    }
}
