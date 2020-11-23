package com.example.personalhealthmonitor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG: CalendarActivity";
    private static final String TAG = "DEBUG: CalendarActivity";

    private CalendarView calendar;

    private RecyclerView recyclerView;
    private InfoListAdapter infoListAdapter;
    private LiveData<List<Info>> listInfo;

    public static InfoViewModel infoViewModelCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //find id
        calendar = findViewById(R.id.calendarView);

        //recycler view
        recyclerView = findViewById(R.id.recyclerview);
        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        infoViewModelCal = ViewModelProviders.of(this).get(InfoViewModel.class);


        //click di una data
        calendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                String year = String.valueOf( eventDay.getCalendar().get(Calendar.YEAR) );
                year = year.substring(2,4);
                int meseint = eventDay.getCalendar().get(Calendar.MONTH) + 1;
                String month = String.valueOf(meseint);
                String day = String.valueOf( eventDay.getCalendar().get(Calendar.DAY_OF_MONTH) );

                String dataClick = month + "/" + day + "/" + year;
                Log.i(DEBUG, "La data è " + dataClick);

                double tempClick = 38;

                infoViewModelCal.getAllInfoInDate(dataClick).observeForever(new Observer<List<Info>>() {
                    @Override
                    public void onChanged(List<Info> infos) {
                        //setto la lista di dati che ho appena recuperato dal DB nel recyclerViewAdapter
                        Log.i(DEBUG, "Sono dentro il click listener");
                        infoListAdapter.setInfos(infos);
                    }
                });


            }
        });


    }
    //end on create


}