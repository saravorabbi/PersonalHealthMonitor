package com.example.personalhealthmonitor;


import androidx.appcompat.app.AppCompatActivity;
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
import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG: CalendarActivity";


    private CalendarView calendar;
    private List<EventDay> eventiDaInserire;    //lista di eventi per cui mettere il segnale
    private TextView dataTV;

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
        dataTV = findViewById(R.id.data_calendar_activity);
        eventiDaInserire= new ArrayList<>();

        //recycler view
        recyclerView = findViewById(R.id.recyclerview);
        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //view model per il DB
        infoViewModelCal = ViewModelProviders.of(this).get(InfoViewModel.class);

        //click di una data
        calendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                // Creo la stringa che mi dice il giorno selezionato nel calendario
                //anno
                String year = String.valueOf( eventDay.getCalendar().get(Calendar.YEAR) );

                //mese
                int meseint = eventDay.getCalendar().get(Calendar.MONTH);
                String month = String.valueOf(meseint + 1);

                //giorno
                String day = String.valueOf( eventDay.getCalendar().get(Calendar.DAY_OF_MONTH) );
                if( day.length() == 1 ){
                    //ho una cifra, aggiungo lo zero
                    day = "0" + day;
                }
                Log.i(DEBUG, "DAY_OF_MONTH è " + day);

                // Costruzione della data da mandare al DB
                String dataClick = month + "/" + day + "/" + year;
                Log.i(DEBUG, "La data è " + dataClick);

                //costruzione della data da vedere a video
                String dataDisplay = "Report del giorno: " + day + "/" + month + "/" + year;
                dataTV.setText(dataDisplay);

                //prendo report dal DB
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