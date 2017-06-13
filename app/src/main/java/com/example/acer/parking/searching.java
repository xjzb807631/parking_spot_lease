package com.example.acer.parking;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import data.*;
import client.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class searching extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<>();
    StringBuffer result=new StringBuffer();

    private Spinner mProvince;
    private Spinner mDistrict;
    private Spinner mCity;
    private Spinner mBlock;
    private EditText mStartTime;
    private EditText mStopTime;

    private ArrayAdapter province_adapter;
    private ArrayAdapter city_adapter;
    private ArrayAdapter<String> district_adapter;
    private ArrayAdapter<String> block_adapter;
    private ArrayAdapter<String> start_time_adapter;
    private ArrayAdapter<String> stop_time_adapter;

    private List<String> province_contactsList=new ArrayList<>();
    private List<String> district_contactsList=new ArrayList<>();
    private List<String> block_contactsList=new ArrayList<>();
    private List<String> start_time_contactsList=new ArrayList<>();
    private List<String> stop_time_contactsList=new ArrayList<>();
    private String[] provinces;
    private String[] cities;
    private String[] districts;
    private String[] areas;
    private searching parent;
    void doPost_confirm()
    {
        String m_province=mProvince.getText().toString().trim();
        String m_district=mDistrict.getText().toString().trim();
        String m_block=mBlock.getText().toString().trim();
        String m_start_time=mStartTime.getText().toString().trim();
        String m_stop_time=mStopTime.getText().toString().trim();

        Area area=new Area();
        ClientMainHandler clientMainHandler=new ClientMainHandler();
        Intent intent_searching_to_parking_place = new Intent(searching.this,parking_place.class) ;
        intent_searching_to_parking_place.putExtra("m_province",m_province);
        intent_searching_to_parking_place.putExtra("m_district",m_district);
        intent_searching_to_parking_place.putExtra("m_block",m_block);
        intent_searching_to_parking_place.putExtra("m_start_time",m_start_time);
        intent_searching_to_parking_place.putExtra("m_stop_time",m_stop_time);
        startActivity(intent_searching_to_parking_place);

        Intent intent_searching_to_renting_in_order = new Intent(searching.this,renting_in_order.class) ;
        intent_searching_to_renting_in_order.putExtra("m_province",m_province);
        intent_searching_to_renting_in_order.putExtra("m_district",m_district);
        intent_searching_to_renting_in_order.putExtra("m_block",m_block);
        intent_searching_to_renting_in_order.putExtra("m_start_time",m_start_time);
        intent_searching_to_renting_in_order.putExtra("m_stop_time",m_stop_time);
        startActivity(intent_searching_to_renting_in_order);
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
        parent=this;
        mProvince=(Spinner) findViewById(R.id.edit_province);
        mCity=(Spinner) findViewById(R.id.edit_city_name);
        mDistrict=(Spinner) findViewById(R.id.edit_district);
        mBlock=(Spinner) findViewById(R.id.edit_block_name);
        mStartTime=(EditText) findViewById(R.id.edit_start_time);
        mStopTime=(EditText) findViewById(R.id.edit_stop_time);
        provinces=new String[1];
        provinces[0]="上海";
        province_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,provinces);
        district_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,district_contactsList);
        block_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,block_contactsList);
        start_time_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,start_time_contactsList);
        stop_time_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stop_time_contactsList);

        Button mConfirmButton = (Button) findViewById(R.id.confirm);
        Button mCancelButton = (Button) findViewById(R.id.cancel);

        mProvince.setAdapter(adapter);
        mProvince.setOnItemSelectedListener(new ProvinceSelectedListener());

        mConfirmButton.setOnClickListener(view->doPost_confirm());

        mCancelButton.setOnClickListener(view->doPost_cancel());

    }
    public class ProvinceSelectedListener implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            List<String> cityList=new ArrayList<String>();
            StringBuffer result=new StringBuffer();
            Handlers.clientSpotHandler.searchCitiesByProvince(provinces[arg2],cityList,result);
            cities=(String[])cityList.toArray();
            city_adapter=new ArrayAdapter<String>(parent,android.R.layout.simple_spinner_item,cities);
            mCity.setAdapter(city_adapter);
            mCity.setOnItemSelectedListener(new CitySelectedListener());
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public class CitySelectedListener implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            List<String> districtList=new ArrayList<String>();
            StringBuffer result=new StringBuffer();
            Handlers.clientSpotHandler.searchDistrictsByCity(cities[arg2],districtList,result);
            districts=(String[])districtList.toArray();
            district_adapter=new ArrayAdapter<String>(parent,android.R.layout.simple_spinner_item,districts);
            mDistrict.setAdapter(district_adapter);
            mDistrict.setOnItemSelectedListener(new AreaSelectedListener());
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public class AreaSelectedListener implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            List<Area> areaList=new ArrayList<Area>();
            StringBuffer result=new StringBuffer();
            List<Area> TempAreas=new ArrayList<>();
            do {
                Handlers.clientSpotHandler.searchAreaByDistrict((String) mCity.getSelectedItem(), districts[arg2], 0, areaList, result);
                TempAreas.addAll(areaList);
            }while (result.toString().equals("Success"));
            areas=new String[TempAreas.size()];
            int i=0;
            for (Area area:TempAreas)
            {
                areas[i]=area.name+'['+area.address+']';
            }
            block_adapter=new ArrayAdapter<String>(parent,android.R.layout.simple_spinner_item,areas);
            mBlock.setAdapter(block_adapter);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
