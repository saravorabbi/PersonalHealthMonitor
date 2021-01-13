package com.example.personalhealthmonitor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

    //RECYCLER VIEW
    private RecyclerView recyclerView;
    private InfoListAdapter infoListAdapter;
    private View viewReport;
    private TextView dailyReportText;
    private String dataClick;

    private LiveData<List<Info>> listInfo;

    public static InfoViewModel infoViewModelCal;

    private TextView temperaturaAVG, presSisAVG, presDiaAVG, glicemiaAVG, pesoAVG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //find id
        calendar = findViewById(R.id.calendarView);
        dataTV = findViewById(R.id.data_calendar_activity);
        eventiDaInserire= new ArrayList<>();

        //media report
        temperaturaAVG = findViewById(R.id.cal_temperatura_media);
        presSisAVG = findViewById(R.id.cal_pressione_sistolica_media);
        presDiaAVG = findViewById(R.id.cal_pressione_diastolica_media);
        glicemiaAVG = findViewById(R.id.cal_glicemia_media);
        pesoAVG = findViewById(R.id.cal_peso_media);


        //RECYCLER
        viewReport = findViewById(R.id.layout_recycler_view);
        dailyReportText = findViewById(R.id.calendar_activity_reports);
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

                viewReport.setVisibility(View.GONE);
                dailyReportText.setVisibility(View.GONE);

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
                dataClick = month + "/" + day + "/" + year;
                Log.i(DEBUG, "La data è " + dataClick);

                //costruzione della data da vedere a video
                String dataDisplay = "Media dei report del: " + day + "/" + month + "/" + year;
                dataTV.setText(dataDisplay);


                //ASYNC TASK
                showAverage(dataClick);

            }
        });


    }
    //end on create


    public void showAllDailyReport(View view){

        viewReport.setVisibility(View.VISIBLE);
        dailyReportText.setVisibility(View.VISIBLE);

        infoViewModelCal.getAllInfoInDate(dataClick).observeForever(new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> infos) {
                //setto la lista di dati che ho appena recuperato dal DB nel recyclerViewAdapter
                Log.i(DEBUG, "Sono dentro il click listener");
                infoListAdapter.setInfos(infos);

                showAverage(dataClick);
            }
        });

    }



    //funzione che chiama l'async task
    public void showAverage(String data){
        AverageAsyncTask task = new AverageAsyncTask();
        task.execute(data);
    }

    //Classe asincrona che aggiorna la UI
    private class AverageAsyncTask extends AsyncTask<String, Void, Void>{

        double temp, sis, dia, glic, peso;

        @Override
        protected Void doInBackground(String... data) {

            temp = infoViewModelCal.getAverageTemperatura(data[0], data[0]);
            sis = infoViewModelCal.getAveragePressioneSistolica(data[0], data[0]);
            dia = infoViewModelCal.getAveragePressioneDiastolica(data[0], data[0]);
            glic = infoViewModelCal.getAverageGlicemia(data[0], data[0]);
            peso = infoViewModelCal.getAveragePeso(data[0], data[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            temperaturaAVG.setText(String.valueOf(temp));
            presSisAVG.setText(String.valueOf(sis));
            presDiaAVG.setText(String.valueOf(dia));
            glicemiaAVG.setText(String.valueOf(glic));
            pesoAVG.setText(String.valueOf(peso));

        }
    }

}