package com.example.acer.parking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class creating_offer extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<>();
    StringBuffer result=new StringBuffer();

    @Override

   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_offer);

        ListView contactsView=(ListView) findViewById(R.id.list_view);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
        contactsView.setAdapter(adapter);
        String user_id=Handlers.clientAccountHandler.user.userID;
      //todo  Handlers.clientSpotHandler.GetSpotByUser(Integer.valueOf(user_id),contactsList,result);
    }
}
