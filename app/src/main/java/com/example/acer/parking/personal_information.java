package com.example.acer.parking;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class personal_information extends AppCompatActivity {

    private Button mReturn_to_load_button;
    private Button mReturn_to_user_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        String userName=pref.getString("userName","");


        mReturn_to_load_button=(Button) findViewById(R.id.return_to_load);
        mReturn_to_user_button=(Button) findViewById(R.id.return_to_user);
        mReturn_to_load_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent_personal_information_to_load = new Intent(personal_information.this,load.class) ;
                startActivity(intent_personal_information_to_load);
            }
        });

        mReturn_to_user_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent_personal_information_to_user = new Intent(personal_information.this,user.class) ;
                startActivity(intent_personal_information_to_user);
            }
        });
    }
}
