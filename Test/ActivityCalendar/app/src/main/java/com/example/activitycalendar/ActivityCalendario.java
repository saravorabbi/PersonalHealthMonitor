package com.example.activitycalendar;

//lib per far funzionare "extends AppCompatActivity"
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;


public class ActivityCalendario extends AppCompatActivity {

    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;


    private TextView quiData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);


        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/"+ dayOfMonth ;
                Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
                //Setta la data nell'activity HOME
                // Intent intent = new Intent(ActivityCalendario.this, MainActivity.class);
                // intent.putExtra("date", date);
                // startActivity(intent);

                //setta la data nella activity corrente
                quiData = findViewById(R.id.dataCal);
                quiData.setText(date);

            }
        });
    }
}