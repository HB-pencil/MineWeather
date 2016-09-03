package com.example.mineweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dell on 2016/8/23.
 */
public class HttpUtility {

    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable(){

            @Override
            public void run(){
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder respone = new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        respone.append(line);
                    }
                    if(listener != null){
                        listener.onFinsh(respone.toString());
                    }
                }catch(Exception e){
                    if(listener == null ){
                        listener.onError(e);
                    }
                }
                finally{
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }

        }).start();
    }
    public interface HttpCallbackListener{
        void onFinsh(String respone);
        void onError(Exception e);
    }
}
