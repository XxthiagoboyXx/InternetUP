package com.auba.internetup.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.auba.internetup.constants.buttonStatus;

public class SecurityPreferences {
    private SharedPreferences mSharedPreferences;


    public SecurityPreferences(Context mContext) {
        this.mSharedPreferences = mContext.getSharedPreferences("status", Context.MODE_PRIVATE);
    }

    public void storedString(String key, String value) {
        this.mSharedPreferences.edit().putString(key, value).apply();
    }

    public String getStoredString(String key) {
        return this.mSharedPreferences.getString(key, buttonStatus.NO_STATUS);
    }


}


