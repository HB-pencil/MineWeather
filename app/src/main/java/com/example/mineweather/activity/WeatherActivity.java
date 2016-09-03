package com.example.mineweather.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.example.mineweather.R;
import com.example.mineweather.model.City;
import com.example.mineweather.service.AutoUpdateService;
import com.example.mineweather.util.HttpUtility;
import com.example.mineweather.util.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WeatherActivity extends AppCompatActivity implements OnClickListener {


    private TextView cityName;
    private TextView lastUpdate;
    private TextView tq;
    private TextView qw;
    private TextView fl;
    private TextView fx;
    private TextView sd;
    private ImageButton switchCity;
    private ImageButton refreshWeather;
    private String currentcity;
    private City city;
    private ImageView weathericon;
    private TextView tq1;
    private TextView tq2;
    private TextView qw1;
    private TextView qw2;
    private TextView tq3;
    private TextView tq4;
    private TextView qw3;
    private TextView qw4;
    private TextView rq1;
    private TextView rq2;
    private LinearLayout back;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        /**
         *初始化控件
         */

        cityName = (TextView) findViewById(R.id.cityName);
        lastUpdate = (TextView) findViewById(R.id.lastupdate);
        tq = (TextView) findViewById(R.id.tq);
        qw = (TextView) findViewById(R.id.qw);
        fl = (TextView) findViewById(R.id.fl);
        fx = (TextView) findViewById(R.id.fx);
        sd = (TextView) findViewById(R.id.sd);
        tq1 = (TextView) findViewById(R.id.yb1);
        tq2 = (TextView) findViewById(R.id.yb2);
        qw1 = (TextView) findViewById(R.id.yb3);
        qw2 = (TextView) findViewById(R.id.yb4);
        tq3 = (TextView) findViewById(R.id.yb5);
        tq4 = (TextView) findViewById(R.id.yb6);
        qw3 = (TextView) findViewById(R.id.yb7);
        qw4 = (TextView) findViewById(R.id.yb8);

        rq1 = (TextView) findViewById(R.id.rq1);
        rq2 = (TextView) findViewById(R.id.rq2);
        back = (LinearLayout) findViewById(R.id.change);

        weathericon = (ImageView) findViewById(R.id.weather_icon);
        city = new City();
        switchCity = (ImageButton) findViewById(R.id.switch_city);
        refreshWeather = (ImageButton) findViewById(R.id.refresh_weather);

        Intent intent = getIntent();
        currentcity = intent.getStringExtra("selected_city");

        if (!TextUtils.isEmpty(currentcity)) {
            lastUpdate.setText("同步中......");
            queryWeather();
            Intent mintent = new Intent(this, AutoUpdateService.class);
            startService(mintent);

        } else {
            showWeather();
            showForecast();
        }

        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }

    @Override
    protected void onStop(){
        super.onStop();
        showNotification();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.switch_city:
                Intent switchintent = new Intent(this, ChooseAreaActivity.class);
                startActivity(switchintent);
                finish();
                break;
            case R.id.refresh_weather:

                lastUpdate.setText("同步中......");
                getUpdateweather();

                break;
            default:
                break;
        }
    }

    ;


    public void queryWeather( ) {
        String address1 = "http://api.yytianqi.com/observe?city=" + city.getId(currentcity) + "&key=3nqi7ma9hmaq5j7b";
        String address2 = "http://api.yytianqi.com/forecast7d?city=" + city.getId(currentcity) + "&key=3nqi7ma9hmaq5j7b";
        HttpUtility.sendHttpRequest(address1, new HttpUtility.HttpCallbackListener() {
            @Override
            public void onFinsh(String respone) {
                Utility.handleWeatherRespone(WeatherActivity.this, respone);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();

                    }


                });
            }

            @Override
            public void onError(Exception e) {
                //回到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(WeatherActivity.this, "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        HttpUtility.sendHttpRequest(address2, new HttpUtility.HttpCallbackListener() {
            @Override
            public void onFinsh(String respone) {
                Utility.handleWeatherforecast(WeatherActivity.this, respone);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showForecast();
                    }


                });
            }

            @Override
            public void onError(Exception e) {
                //回到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(WeatherActivity.this, "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }


    public void getUpdateweather( ) {
        if(cityName.getText().toString()!=""){
        String address1 = "http://api.yytianqi.com/observe?city=" + city.getId(cityName.getText().toString()) + "&key=3nqi7ma9hmaq5j7b";
        String address2 = "http://api.yytianqi.com/forecast7d?city=" + city.getId(cityName.getText().toString()) + "&key=3nqi7ma9hmaq5j7b";
        HttpUtility.sendHttpRequest(address1, new HttpUtility.HttpCallbackListener() {
            @Override
            public void onFinsh(String respone) {
                Utility.handleWeatherRespone(WeatherActivity.this, respone);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                        showForecast();
                    }


                });
            }

            @Override
            public void onError(Exception e) {
                //回到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(WeatherActivity.this, "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        HttpUtility.sendHttpRequest(address2, new HttpUtility.HttpCallbackListener() {
            @Override
            public void onFinsh(String respone) {
                Utility.handleWeatherforecast(WeatherActivity.this, respone);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showForecast();
                    }


                });
            }

            @Override
            public void onError(Exception e) {
                //回到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(WeatherActivity.this, "数据获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });}else{
            Toast.makeText(this,"请先选择城市",Toast.LENGTH_SHORT).show();
        }
    }
    public void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityName.setText(prefs.getString("city_name", ""));
        lastUpdate.setText(prefs.getString("last_Update", "") + "更新");
        tq.setText(prefs.getString("t_q", ""));
        qw.setText(prefs.getString("q_w", "") + "℃");
        fl.setText("风力：" + prefs.getString("f_l", "") + "级");
        fx.setText("风向:" + prefs.getString("f_x", ""));
        sd.setText("相对湿度:" + prefs.getString("s_d", "") + "%");
        setImageIcon(prefs.getString("t_q", ""));

    }

    public void showForecast() {


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        rq1.setText(prefs.getString("date_1",""));
        tq1.setText("白天：" + prefs.getString("tq_1", ""));
        tq2.setText("夜间:" + prefs.getString("tq_2", ""));
        qw1.setText(prefs.getString("qw_2", "") + "℃" + "~" + prefs.getString("qw_1", "") + "℃");



        rq2.setText(prefs.getString("date_2",""));
        tq3.setText("白天：" + prefs.getString("tq_3", ""));
        tq4.setText("夜间:" + prefs.getString("tq_4", ""));
        qw3.setText(prefs.getString("qw_4", "") + "℃" + "~" + prefs.getString("qw_3", "") + "℃");




    }


    public void setImageIcon(String tq) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String time = sdf.format(date);
        int currenttime = Integer.valueOf(time).intValue();
        if ( currenttime > 6 && currenttime < 18 ) {

            Drawable draw = ContextCompat.getDrawable(this,R.drawable.day);
            back.setBackground(draw);

            switch (tq) {
                case "晴":
                    weathericon.setImageResource(R.drawable.clearo);
                    break;
                case "多云":
                    weathericon.setImageResource(R.drawable.cloudyo);
                    break;
                case "阴":
                    weathericon.setImageResource(R.drawable.overcasto);
                    break;
                case "阵雨":
                    weathericon.setImageResource(R.drawable.showero);
                    break;
                case "雷阵雨":
                    weathericon.setImageResource(R.drawable.thundershowero);
                    break;
                case "雷阵雨伴有冰雹":
                    weathericon.setImageResource(R.drawable.t_with_hailo);
                    break;
                case "雨夹雪":
                    weathericon.setImageResource(R.drawable.sleeto);
                    break;
                case "小雨":
                    weathericon.setImageResource(R.drawable.lightraino);
                    break;
                case "中雨":
                    weathericon.setImageResource(R.drawable.mraino);
                    break;
                case "大雨":
                    weathericon.setImageResource(R.drawable.hraino);
                    break;
                case "暴雨":
                    weathericon.setImageResource(R.drawable.sraino);
                    break;
                case "大暴雨":
                    weathericon.setImageResource(R.drawable.hso);
                    break;
                case "特大暴雨":
                    weathericon.setImageResource(R.drawable.sso);
                    break;
                case "阵雪":
                    weathericon.setImageResource(R.drawable.sflurryo);
                    break;
                case "小雪":
                    weathericon.setImageResource(R.drawable.lsnowo);
                    break;
                case "中雪":
                    weathericon.setImageResource(R.drawable.msnowo);
                    break;
                case "大雪":
                    weathericon.setImageResource(R.drawable.hso);
                    break;
                case "暴雪":
                    weathericon.setImageResource(R.drawable.snowstormo);
                    break;
                case "雾":
                    weathericon.setImageResource(R.drawable.fogo);
                    break;
                case "冻雨":
                    weathericon.setImageResource(R.drawable.iceraino);
                    break;
                case "沙尘暴":
                    weathericon.setImageResource(R.drawable.dusto);
                    break;
                case "小到中雨":
                    weathericon.setImageResource(R.drawable.ltmro);
                    break;
                case "中到大雨":
                    weathericon.setImageResource(R.drawable.mthro);
                    break;
                case "大到暴雨":
                    weathericon.setImageResource(R.drawable.hrtso);
                    break;
                case "暴雨到大暴雨":
                    weathericon.setImageResource(R.drawable.sthso);
                    break;
                case "到暴雨到特大暴雨":
                    weathericon.setImageResource(R.drawable.htss);
                    break;
                case "小到中雪":
                    weathericon.setImageResource(R.drawable.ltmso);
                    break;
                case "中到大雪":
                    weathericon.setImageResource(R.drawable.mthso);
                    break;
                case "大到暴雪":
                    weathericon.setImageResource(R.drawable.hstsso);
                    break;
                case "浮尘":
                    weathericon.setImageResource(R.drawable.dusto);
                    break;
                case "扬沙":
                    weathericon.setImageResource(R.drawable.sando);
                    break;
                case "强沙尘暴":
                    weathericon.setImageResource(R.drawable.sandso);
                    break;
                case "浓雾":
                    weathericon.setImageResource(R.drawable.densefogo);
                    break;
                case "强浓雾":
                    weathericon.setImageResource(R.drawable.hdfo);
                    break;
                case "霾":
                    weathericon.setImageResource(R.drawable.hazeo);
                    break;
                case "中度霾":
                    weathericon.setImageResource(R.drawable.mhazeo);
                    break;
                case "重度霾":
                    weathericon.setImageResource(R.drawable.shazeo);
                    break;
                case "严重霾":
                    weathericon.setImageResource(R.drawable.hhazeo);
                    break;
                case "大雾":
                    weathericon.setImageResource(R.drawable.hfogo);
                    break;
                case "特强浓雾":
                    weathericon.setImageResource(R.drawable.ehdfogo);
                    break;
                case "无":
                    weathericon.setImageResource(R.drawable.none);
                    break;
                case "刮风":
                    weathericon.setImageResource(R.drawable.windyo);
                    break;
                default:
                    break;

            }
       } else {
            Drawable dra = ContextCompat.getDrawable(this,R.drawable.night);
            back.setBackground(dra);

            switch (tq) {
                case "晴":
                    weathericon.setImageResource(R.drawable.cleart);
                    break;
                case "多云":
                    weathericon.setImageResource(R.drawable.cloudyt);
                    break;
                case "阴":
                    weathericon.setImageResource(R.drawable.overcastt);
                    break;
                case "阵雨":
                    weathericon.setImageResource(R.drawable.showert);
                    break;
                case "雷阵雨":
                    weathericon.setImageResource(R.drawable.thundershowert);
                    break;
                case "雷阵雨伴有冰雹":
                    weathericon.setImageResource(R.drawable.t_with_hailt);
                    break;
                case "雨夹雪":
                    weathericon.setImageResource(R.drawable.sleept);
                    break;
                case "小雨":
                    weathericon.setImageResource(R.drawable.lightraint);
                    break;
                case "中雨":
                    weathericon.setImageResource(R.drawable.mraint);
                    break;
                case "大雨":
                    weathericon.setImageResource(R.drawable.hraint);
                    break;
                case "暴雨":
                    weathericon.setImageResource(R.drawable.sraint);
                    break;
                case "大暴雨":
                    weathericon.setImageResource(R.drawable.hst);
                    break;
                case "特大暴雨":
                    weathericon.setImageResource(R.drawable.sst);
                    break;
                case "阵雪":
                    weathericon.setImageResource(R.drawable.sflurryt);
                    break;
                case "小雪":
                    weathericon.setImageResource(R.drawable.lsnowt);
                    break;
                case "中雪":
                    weathericon.setImageResource(R.drawable.msnowt);
                    break;
                case "大雪":
                    weathericon.setImageResource(R.drawable.hst);
                    break;
                case "暴雪":
                    weathericon.setImageResource(R.drawable.snowstormt);
                    break;
                case "雾":
                    weathericon.setImageResource(R.drawable.fogt);
                    break;
                case "冻雨":
                    weathericon.setImageResource(R.drawable.iceraint);
                    break;
                case "沙尘暴":
                    weathericon.setImageResource(R.drawable.dustt);
                    break;
                case "小到中雨":
                    weathericon.setImageResource(R.drawable.ltmrt);
                    break;
                case "中到大雨":
                    weathericon.setImageResource(R.drawable.mthrt);
                    break;
                case "大到暴雨":
                    weathericon.setImageResource(R.drawable.hrtst);
                    break;
                case "暴雨到大暴雨":
                    weathericon.setImageResource(R.drawable.sthst);
                    break;
                case "到暴雨到特大暴雨":
                    weathericon.setImageResource(R.drawable.htsst);
                    break;
                case "小到中雪":
                    weathericon.setImageResource(R.drawable.ltmst);
                    break;
                case "中到大雪":
                    weathericon.setImageResource(R.drawable.mthst);
                    break;
                case "大到暴雪":
                    weathericon.setImageResource(R.drawable.hstsst);
                    break;
                case "浮尘":
                    weathericon.setImageResource(R.drawable.dustt);
                    break;
                case "扬沙":
                    weathericon.setImageResource(R.drawable.sandt);
                    break;
                case "强沙尘暴":
                    weathericon.setImageResource(R.drawable.sandst);
                    break;
                case "浓雾":
                    weathericon.setImageResource(R.drawable.densefogt);
                    break;
                case "强浓雾":
                    weathericon.setImageResource(R.drawable.hdft);
                    break;
                case "霾":
                    weathericon.setImageResource(R.drawable.hazet);
                    break;
                case "中度霾":
                    weathericon.setImageResource(R.drawable.mhazet);
                    break;
                case "重度霾":
                    weathericon.setImageResource(R.drawable.shazet);
                    break;
                case "严重霾":
                    weathericon.setImageResource(R.drawable.hhazet);
                    break;
                case "大雾":
                    weathericon.setImageResource(R.drawable.hfogt);
                    break;
                case "特强浓雾":
                    weathericon.setImageResource(R.drawable.ehdfogt);
                    break;
                case "无":
                    weathericon.setImageResource(R.drawable.nonet);
                    break;
                case "刮风":
                    weathericon.setImageResource(R.drawable.windyt);
                    break;
                default:
                    break;

            }
        }
    }

    public void showNotification(){

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentText(tq.getText().toString()+""+qw1.getText().toString());
        builder.setContentTitle(cityName.getText().toString()+"天气");
        builder.setPriority(Notification.PRIORITY_MIN);
        builder.setTicker("天气预报");
        builder.setAutoCancel(true);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.forecast);
        builder.setSmallIcon(R.drawable.tian);
        builder.setLargeIcon(bitmap);
        Intent intent = new Intent(this,WeatherActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(pi);
        Notification notification = builder.build();
        notification.flags |=Notification.FLAG_NO_CLEAR;
        notification.flags |=Notification.FLAG_AUTO_CANCEL;
        manager.notify(1,notification);


    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}