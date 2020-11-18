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
    private TextView dataTV;

    private RecyclerView recyclerView;
    private InfoListAdapter infoListAdapter;
    public static InfoViewModel infoViewModel;
    private LiveData<List<Info>> listInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //find id
        dataTV = findViewById(R.id.dataCal);
        calendar = findViewById(R.id.calendarView);

        //recycler view
        recyclerView = findViewById(R.id.recyclerview);
        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);

        //infoViewModel.getAllInfoInDate().observe()

        //click di una data
        calendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                String year = String.valueOf( eventDay.getCalendar().get(Calendar.YEAR) );
                String month = String.valueOf( eventDay.getCalendar().get(Calendar.MONTH) );
                String day = String.valueOf( eventDay.getCalendar().get(Calendar.DAY_OF_MONTH) );

                String dataClick = month + "/" + day + "/" + year;
                Log.i(DEBUG, "La data è " + dataClick);


                infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);

                infoViewModel.getAllInfoInDate(dataClick).observe(this, new Observer<List<Info>>() {
                    @Override
                    public void onChanged(List<Info> infos) {
                        infoListAdapter.setInfos(infos);
                    }
                });

            }
        });


    }
    //end on create

    public void osserva(String dataClick){
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);


        infoViewModel.getAllInfoInDate(dataClick).observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> infos) {
                infoListAdapter.setInfos(infos);
            }
        });
    }


}