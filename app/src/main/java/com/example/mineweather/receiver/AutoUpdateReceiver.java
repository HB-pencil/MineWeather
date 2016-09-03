package com.example.mineweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mineweather.service.AutoUpdateService;

/**
 * Created by dell on 2016/8/24.
 */
public class AutoUpdateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context,Intent intent){
        Intent i = new Intent(context,AutoUpdateService.class);
        context.startService(i);
    }

}
