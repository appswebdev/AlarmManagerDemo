package com.example.android.alarmmanagerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyAirPlaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Minhal", intent.toString());
        Bundle extras = intent.getExtras();

        for (String key : extras.keySet()) {
            Log.d("Minhal", key);
            Log.d("Minhal", extras.get(key).toString());
        }
    }
}
