package com.wix.norsoftbd.takeyourtrip.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MNH on 11/3/2017.
 */

public class saveLoginSession {
    final static  String FileName ="UserData";
    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }
    public static void SharedPrefesSAVE(Context ctx,String Name,String type,String uname){
        SharedPreferences prefs = ctx.getSharedPreferences("NAME", 0);

        SharedPreferences.Editor prefEDIT = prefs.edit();

        prefEDIT.putString("Name", Name);
        prefEDIT.putString("type", type);
        prefEDIT.putString("uname",uname);


        prefEDIT.commit();

    }
}
