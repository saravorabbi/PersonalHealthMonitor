package com.example.activitycalendar5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();

        int giorno =  cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        cal.add(Calendar.DAY_OF_MONTH, Calendar.MONDAY-cal.get(Calendar.DAY_OF_WEEK));
        Log.i(TAG, "first day of week" + cal.getTime());




    }
}