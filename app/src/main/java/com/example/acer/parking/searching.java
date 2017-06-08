package com.example.acer.parking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import client.ClientSpotHandler;

public class searching extends AppCompatActivity {

    private EditText mProvince;
    private EditText mDistrict;
    private Button mConfirmButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        mProvince = (EditText) findViewById(R.id.edit_province);
        mDistrict = (EditText) findViewById(R.id.edit_district);
        mConfirmButton = (Button) findViewById(R.id.confirm);
        mCancelButton = (Button) findViewById(R.id.cancel);

        final String provinceName = mProvince.getText().toString().trim();    //获取当前输入的省和区
        final String districtPwd = mDistrict.getText().toString().trim();

        mConfirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ClientSpotHandler clientSpotHandler=new ClientSpotHandler();
               // clientSpotHandler.searchAreaByDistrict(provinceName,districtPwd,1,null);
                Intent intent_searching_to_parking_place = new Intent(searching.this,parking_place.class) ;
                startActivity(intent_searching_to_parking_place);
            }
        });
    }
}
