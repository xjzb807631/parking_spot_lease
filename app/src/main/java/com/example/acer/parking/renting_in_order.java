package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Timestamp;

import data.*;

public class renting_in_order extends AppCompatActivity {

    float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting_in_order);

        Intent intent=getIntent();
        String m_province=intent.getStringExtra("m_province");
        String m_district=intent.getStringExtra("m_district");
        String m_block=intent.getStringExtra("m_block");
        String m_start_time=intent.getStringExtra("m_start_time");
        String m_stop_time=intent.getStringExtra("m_stop_time");

        Order order=new Order(m_start_time,m_stop_time,spot,amount);

    }
}
