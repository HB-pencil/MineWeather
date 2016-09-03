package com.example.mineweather.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/8/24.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finshAll(){
        for(Activity activity : activities){
           if(activity.isFinishing()){ activity.finish();}
        }
    }
}
