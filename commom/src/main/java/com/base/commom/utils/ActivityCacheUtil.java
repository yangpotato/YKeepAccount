package com.base.commom.utils;

import android.app.Activity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */

public class ActivityCacheUtil {
    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    public static void finishAll(){
        if(activityList.size() > 0){
            final List<Activity> mList = activityList;
            for(Activity activity : mList){
//                activity.finish();
//                if(!activity.getClass().equals(MainActivity.class)) {
                    finishActivity(activity.getClass());
//                }
            }
            activityList.clear();
        }
    }

    public static void finishActivity(Class<?> cls){
        Activity mActivity = null;
        for(Activity activity : activityList){
            if(activity.getClass().equals(cls)){
                mActivity = activity;
            }
        }
        finishActivityByClass(mActivity);
    }

    private static void finishActivityByClass(Activity activity){
        if(activity != null){
//            if(activityList.contains(activity)){
//                activityList.remove(activity);
//            }
            activity.finish();
        }
    }
}
