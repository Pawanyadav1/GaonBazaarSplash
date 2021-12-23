package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static String MY_PREFER = "MY_PREFER";

    public static void putkey(Context context, String key, String value)
    {
        sharedPreferences=context.getSharedPreferences(MY_PREFER,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();

    }
    public static String getKey(Context context,String key)
    {
        sharedPreferences=context.getSharedPreferences(MY_PREFER,context.MODE_PRIVATE);
        return  sharedPreferences.getString(key, "");
    }

}