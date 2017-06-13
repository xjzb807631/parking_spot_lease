package com.example.acer.parking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import client.*;

import server.AccountHandler;
import server.SQLConnection;
import server.SpotHandler;

public class MainActivity extends AppCompatActivity {
    void doPost()
    {
        Intent intent=new Intent(MainActivity.this,load.class);
        startActivity(intent);
    };
    @Override

    protected void onCreate(Bundle savedInstanceState) {
     //   SQLConnection.MYSQLPORT= String.valueOf(27322);
     //   SQLConnection.URLSTR="jdbc:mysql://i1n7106689.iask.in:";
        AccountHandler handler1=new AccountHandler();
        handler1.start();
        SpotHandler handler2=new SpotHandler();
        handler2.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button into_load=(Button) findViewById(R.id.into_load);
        into_load.setOnClickListener(view -> doPost());

    }
}
