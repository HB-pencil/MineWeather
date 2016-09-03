package com.example.mineweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.mineweather.model.City;
import com.example.mineweather.receiver.AutoUpdateReceiver;
import com.example.mineweather.util.HttpUtility;
import com.example.mineweather.util.Utility;

/**
 * Created by dell on 2016/8/24.
 */
public class AutoUpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();

    AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
    long time = SystemClock.elapsedRealtime() + 2*60*60*1000;
    Intent i = new Intent(this,AutoUpdateReceiver.class);
    PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
    manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,time,pi);
    return super.onStartCommand(intent,flags,startId);
    }

    /**
     * 更新天气
     */
    public void updateWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this);
        City city = new City();
        String currentcity = prefs.getString("city_name","");
        String address1 = "http://api.yytianqi.com/observe?city=" + city.getId(currentcity) + "&key=3nqi7ma9hmaq5j7b";
        HttpUtility.sendHttpRequest(address1, new HttpUtility.HttpCallbackListener() {
            @Override
            public void onFinsh(String respone) {
                Utility.handleWeatherRespone(AutoUpdateService.this,respone);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        String address2 = "http://api.yytianqi.com/forecast7d?city=" + city.getId(currentcity) + "&key=3nqi7ma9hmaq5j7b";
        HttpUtility.sendHttpRequest(address2, new HttpUtility.HttpCallbackListener() {
            @Override
            public void onFinsh(String respone) {
                Utility.handleWeatherRespone(AutoUpdateService.this,respone);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }



}


