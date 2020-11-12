package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG: CalendarActivity";

    private CalendarView calendar;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //prova
        data = findViewById(R.id.dataCal);

        calendar = findViewById(R.id.calendarView);

        //click di una data
        calendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                //Calendar clickedDayCalendar = eventDay.getCalendar();

                //eventDay.getCalendar().getTime();
                String prova, prova2;

                Calendar cal = Calendar.getInstance();  //prende tutte le info del calendario
                Date call = Calendar.getInstance().getTime();
                prova2 = call.toString();

                Log.i(DEBUG, prova2);

                prova = eventDay.getCalendar().getTime().toString();
                data = findViewById(R.id.dataCal);
                data.setText(prova);

/*
                calendarView.setOnDayClickListener(eventDay ->
                        Toast.makeText(getApplicationContext(), eventDay.getCalendar().getTime().toString() + " " + eventDay.isEnabled(), Toast.LENGTH_SHORT).show());
*/
            }
        });


    }
}