package com.example.android.alarmmanagerdemo;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;


public class AlarmIntentService extends IntentService {

    public AlarmIntentService() {
        super("AlarmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MediaPlayer player = MediaPlayer.create(this, R.raw.gong);
        player.start();
    }
}
