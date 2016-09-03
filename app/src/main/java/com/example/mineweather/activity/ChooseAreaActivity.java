package com.example.mineweather.activity;

/**
 * Created by dell on 2016/8/23.
 */



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.mineweather.R;

public class ChooseAreaActivity extends AppCompatActivity {

    public static final int LEVEL_CITY = 0;

    private ListView listView;

    //城市数据
    private String [] Cities = {"广州","韶关","深圳","珠海","汕头","顺德","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江",
            "清远","东莞","中山","潮州","揭阳","云浮"};


    //选中的城市
    private String selectedCity;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);


        listView = (ListView) findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Cities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>arg0,View arg1,int arg2,long arg3){
                selectedCity = ((TextView)arg1).getText().toString();

                Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                intent.putExtra("selected_city", selectedCity);
                startActivity(intent);
            }
        });



    }





}
