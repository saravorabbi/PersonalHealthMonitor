package com.example.activitycalendar_2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarActivity extends AppCompatActivity {

    private static final String TAGGG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
/*
        Log.i(TAGGG, "creata");

        //lista degli eventi
        List<EventDay> events = new ArrayList<>();
        Log.i(TAGGG, "creata lista");

        //Aggiungo i nuovi Eventi in una lista
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);

        events.add(new EventDay(cal, R.drawable.ic_message_black_48dp));

        Log.i(TAGGG, "creato evento 1");

        //Associo la view del calendario a una variabile
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        Log.i(TAGGG, "aggiunti eventi in lista");

        //Prende gli eventi nella Lista e li setta nel Calendario
        calendarView.setEvents(events);
        Log.i(TAGGG, "setta eventi??");

*/

        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.ic_message_black_48dp));


        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);


    }

}


