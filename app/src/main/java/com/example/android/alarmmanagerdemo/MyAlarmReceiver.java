package com.example.android.alarmmanagerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class MyAlarmReceiver extends WakefulBroadcastReceiver {
    public MyAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AlarmIntentService.class);
        startWakefulService(context, serviceIntent);
        //context.startService(serviceIntent);
    }
}
