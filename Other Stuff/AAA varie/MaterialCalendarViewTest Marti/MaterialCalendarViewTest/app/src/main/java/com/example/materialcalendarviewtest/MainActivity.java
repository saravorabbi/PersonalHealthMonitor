package com.example.materialcalendarviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //crea gli eventi e ci collega le immagini
        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.ic_add));
        //events.add(new EventDay(calendar, R.drawable.sample_three_icons));
//or
        //events.add(new EventDay(calendar, new Drawable());
//or if you want to specify event label color
        //events.add(new EventDay(calendar, R.drawable.background_color_circle_selector, Color.parseColor(rgb(Color.))));

        //aggiunge gli eventi con le immagini nel calendario
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);

/*      Roba inutile per fare highlights

        List<Calendar> calendars = new ArrayList<>();
        Calendar punto = Calendar.getInstance();
        punto.set(2020, 10, 15);
        calendars.add(punto);
        punto.set(2020, 10, 16);
        calendars.add(punto);
        calendarView.setHighlightedDays(calendars);
*/

    }

}

