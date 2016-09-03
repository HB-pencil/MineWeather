package com.example.mineweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 2016/8/23.
 */
public class Utility {



    public static void handleWeatherRespone(Context context,String respone){
        try {
            JSONObject jsonobject = new JSONObject(respone);
            JSONObject data = jsonobject.getJSONObject("data");
            String  cityId = data.getString("cityId");
            String  cityName = data.getString("cityName");
            String  lastUpdate = data.getString("lastUpdate");
            String  tq = data.getString("tq");
            String  qw = data.getString("qw");
            String  fl = data.getString("fl");
            String  fx = data.getString("fx");
            String  sd = data.getString("sd");

            saveweatherInfo(context,cityId,cityName,lastUpdate,tq,qw,fl,fx,sd);

        }catch(JSONException e){e.printStackTrace();}
    }

    public static void saveweatherInfo(Context context, String cityId, String cityName, String lastUpdate,
                                       String tq, String qw, String fl, String fx, String sd){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("city_id",cityId);
        editor.putString("city_name", cityName);
        editor.putString("last_Update",lastUpdate);
        editor.putString("q_w", qw);
        editor.putString("f_l", fl);
        editor.putString("f_x", fx);
        editor.putString("s_d",sd);
        editor.putString("t_q", tq);

        editor.apply();

    }

    public static void handleWeatherforecast(Context context,String respone){
        try{JSONObject jsonobject = new JSONObject(respone);
            JSONObject data = jsonobject.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");


                JSONObject jsonObject0 = list.getJSONObject(0);
                String tq1 = jsonObject0.getString("tq1");
                String tq2 = jsonObject0.getString("tq2");
                String qw1 = jsonObject0.getString("qw1");
                String qw2 = jsonObject0.getString("qw2");
                String date1 =jsonObject0.getString("date");

                JSONObject jsonObject1 = list.getJSONObject(1);
                String tq3 = jsonObject1.getString("tq1");
                String tq4 = jsonObject1.getString("tq2");
                String qw3 = jsonObject1.getString("qw1");
                String qw4= jsonObject1.getString("qw2");
                String date2 =jsonObject1.getString("date");



            saveWeatherforecast(context,tq1,tq2,qw1,qw2,date1,tq3,tq4,qw3,qw4,date2);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    public static void saveWeatherforecast(Context context,String tq1,String tq2,String qw1,String qw2,String date1,
                                           String tq3,String tq4,String qw3,String qw4,String date2){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("tq_1",tq1);
        editor.putString("tq_2",tq2);
        editor.putString("qw_1",qw1);
        editor.putString("qw_2",qw2);
        editor.putString("date_1",date1);

        editor.putString("tq_3",tq3);
        editor.putString("tq_4",tq4);
        editor.putString("qw_3",qw3);
        editor.putString("qw_4",qw4);
        editor.putString("date_2",date2);



        editor.apply();
    }
}
