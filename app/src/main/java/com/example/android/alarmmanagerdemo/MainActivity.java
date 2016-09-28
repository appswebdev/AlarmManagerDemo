package com.example.android.alarmmanagerdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ALARM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTimeAndSetTheAlarm();
            }
        });
    }

    private void chooseTimeAndSetTheAlarm() {

        final DateTime now = DateTime.now();
        int h = now.getHourOfDay();
        int m = now.getMinuteOfHour();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        DateTime userSelectedTime = now
                                .withHourOfDay(hour).
                                        withMinuteOfHour(min);
                        long millis = userSelectedTime.getMillis();
                        setAlarm(millis);
                    }
                }, h, m, true);
        timePickerDialog.show();
    }

    private void setAlarm(long millis) {
        AlarmManager mgr =
                (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, MyAlarmReceiver.class);
        intent.putExtra("Time", millis);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        this,
                        REQUEST_ALARM,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mgr.setExact(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        } else {
            mgr.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            saveNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    int i = 0;

    void saveNote() {
        SharedPreferences prefs = getSharedPreferences("Notes", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Note" + i, "Minhal note");
        i++;

        editor.commit();

    }

    void loadNote() {
        SharedPreferences prefs = getSharedPreferences("Notes", MODE_PRIVATE);
        String note = prefs.getString("Note", null);
        if (note != null)
            Toast.makeText(MainActivity.this, note, Toast.LENGTH_SHORT).show();
    }
}
