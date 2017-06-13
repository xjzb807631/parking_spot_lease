package com.example.acer.parking;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import client.ClientSpotHandler;
import data.*;

public class inserting_spot extends AppCompatActivity {

    private EditText m_user_id;
    private EditText m_address;
    private EditText m_description;
    private EditText m_category;
    private EditText m_size;
    private EditText m_spot_local_ref;
    StringBuffer result;
    void doPost_comfirm_on_inserting_spot(Spot spot)
    {
        Handlers.clientSpotHandler.insertSpot(spot,result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_spot);

        m_user_id=(EditText) findViewById(R.id.edit_user_id);
        m_address=(EditText) findViewById(R.id.edit_address);
        m_description=(EditText) findViewById(R.id.edit_description);
        m_category=(EditText) findViewById(R.id.edit_category);
        m_size=(EditText) findViewById(R.id.edit_size);
        m_spot_local_ref=(EditText) findViewById(R.id.edit_spot_local_ref);

        String user_name=m_user_id.getText().toString().trim();
        String address=m_address.getText().toString().trim();
        String description=m_description.getText().toString().trim();
        String category=m_category.getText().toString().trim();
        String size=m_size.getText().toString().trim();
        String spot_local_ref=m_spot_local_ref.getText().toString().trim();

        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        String userName=pref.getString("userName","");
        String user_id=Handlers.clientAccountHandler.user.userID;

        //Spot spot=new Spot(Integer.valueOf(user_id),address,spot_local_ref,description,category,size);

        Button m_confirm_button=(Button) findViewById(R.id.confirm);
        Button m_cancel_button=(Button) findViewById(R.id.cancel);

        //m_confirm_button.setOnClickListener(view->doPost_confirm_on_inserting_spot(spot));

    }
}
