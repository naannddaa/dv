package com.amelia.dv;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("file_kunci", context.MODE_PRIVATE);
    }
    public boolean UserLogin(){
        return sharedPreferences.getBoolean("userlogin", false);
    }

    public void UpdateUserLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("userlogin", status);
        editor.apply();
    }

    public void SavedNik (String nik){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nik", nik);
        editor.apply();
    }

    public String getNik(){
        return sharedPreferences.getString("nik", "Unknown");
    }

}
