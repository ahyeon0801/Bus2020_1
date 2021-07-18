package com.ahyeon.bus2020_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "rebuild_preference";
    private static final String DEFAULT_VALUE_STRING = "";
    private static final String TAG = "[[ PreferenceManager ]]";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
    /**
     * String 값 저장
     * @param context
     * @param key
     * @param value
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    /**
     * String 값 로드
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, DEFAULT_VALUE_STRING);
        Log.d(TAG, "getString@@@");
        return value;
    }
    /**
     * 키 값 삭제
     * @param context
     * @param key
     */
    public static void removeKey(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 모든 저장 데이터 삭제
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }
    public static String listData(Context context){
        SharedPreferences prefs = getPreferences(context);
        String dataList = "";
        Map<String, ?> toalValue = prefs.getAll();
        for(Map.Entry<String, ?> entry : toalValue.entrySet()){
            dataList += entry.getKey().toString()+ " :"+entry.getValue().toString()+"\r\n";
            Log.d(TAG, "listData@@@");
        }
        return dataList;
    }
    public static ArrayList<String> arrayList(Context context){
        SharedPreferences prefs = getPreferences(context);
        ArrayList<String> arrayID = new ArrayList<>();
        Map<String, ?> toalValue = prefs.getAll();
        for(Map.Entry<String, ?> entry : toalValue.entrySet()){
            //entry.getKey().toString()+ " :"+entry.getValue().toString()+"\r\n";
            arrayID.add(entry.getKey().toString());

            Log.d(TAG, "arrayName 생성@@@");
        }
        return arrayID;
    }
    public static Map<String, ?> mapString(Context context){
        SharedPreferences prefs = getPreferences(context);
        Map<String, ?> toalValue = prefs.getAll();

        Log.d(TAG, "mapString 생성@@@");
        return toalValue;
    }
}
