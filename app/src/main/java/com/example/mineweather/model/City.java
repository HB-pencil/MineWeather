package com.example.mineweather.model;

/**
 * Created by dell on 2016/8/23.
 */
public class City {
    private String cityId;

    public String getId(String cityname){
        if(cityname.equals("广州")){
            cityId = "CH280101";
        }else if(cityname.equals("深圳")){
            cityId = "CH280601";
        }else if(cityname.equals("韶关")){
            cityId = "CH280201";
        }else if(cityname.equals("珠海")){
            cityId = "CH280701";
        }else if(cityname.equals("汕头")){
            cityId = "CH280501";
        }else if(cityname.equals("顺德")){
            cityId = "CH280801";
        }else if(cityname.equals("江门")){
            cityId = "CH281101";
        }else if(cityname.equals("湛江")){
            cityId = "CH281001";
        }else if(cityname.equals("茂名")){
            cityId = "CH282001";
        }else if(cityname.equals("肇庆")){
            cityId = "CH280901";
        }else if(cityname.equals("惠州")){
            cityId = "CH280301";
        }else if(cityname.equals("梅州")){
            cityId = "CH280401";
        }else if(cityname.equals("汕尾")){
            cityId = "CH282101";
        }else if(cityname.equals("河源")){
            cityId = "CH281201";
        }else if(cityname.equals("阳江")){
            cityId = "CH281801";
        }else if(cityname.equals("清远")){
            cityId = "CH281301";
        }else if(cityname.equals("东莞")){
            cityId = "CH281601";
        }else if(cityname.equals("中山")){
            cityId = "CH281701";
        }else if(cityname.equals("潮州")){
            cityId = "CH281501";
        }else if(cityname.equals("揭阳")){
            cityId = "CH281901";
        }else if(cityname.equals("云浮")){
            cityId = "CH281401";
        }



        return cityId;
    }

}
