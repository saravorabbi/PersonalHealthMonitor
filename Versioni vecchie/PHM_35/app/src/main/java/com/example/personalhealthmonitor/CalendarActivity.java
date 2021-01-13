package com.example.personalhealthmonitor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;


public class CalendarActivity extends AppCompatActivity {

    public static final String TAG = "CalendarActivity: ";
    private static final String DEBUG = "DEBUG: CalendarActivity";

    //calendario
    private CalendarView calendarView;
    private List<EventDay> eventiDaInserire;    //lista di eventi per cui mettere il segnale
    private TextView dataTV;

    //private List<EventDay> events;
    private LiveData<List<Info>> listInfo;

    //recycler view
    private RecyclerView recyclerView;
    private InfoListAdapter infoListAdapter;
    private View viewReportRecycler;
    private TextView dailyReportText;
    private String dataClick;


    private TextView temperaturaAVG, presSisAVG, presDiaAVG, glicemiaAVG, pesoAVG;
    private LinearLayout linearLayoutAVG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //find id
        calendarView = findViewById(R.id.calendarView);
        dataTV = findViewById(R.id.data_calendar_activity);
        eventiDaInserire= new ArrayList<>();
        linearLayoutAVG = findViewById(R.id.layout_media_giornaliera);

        //media report
        temperaturaAVG = findViewById(R.id.cal_temperatura_media);
        presSisAVG = findViewById(R.id.cal_pressione_sistolica_media);
        presDiaAVG = findViewById(R.id.cal_pressione_diastolica_media);
        glicemiaAVG = findViewById(R.id.cal_glicemia_media);
        pesoAVG = findViewById(R.id.cal_peso_media);


        //RECYCLER
        viewReportRecycler = findViewById(R.id.include_report_recycler_view);
        dailyReportText = findViewById(R.id.calendar_activity_reports);
        //recycler view
        recyclerView = findViewById(R.id.recyclerview);
        infoListAdapter = new InfoListAdapter(this);
        recyclerView.setAdapter(infoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //view model per il DB
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);



        showIconCalendar();



        //click di una data
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                //media
                linearLayoutAVG.setVisibility(View.VISIBLE);
                //recycler view
                viewReportRecycler.setVisibility(View.GONE);
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
                showAverageDay(dataClick);

            }
        });


    }
    //end on create



    //funzione chiamata quando clicco il bottone Report, mostra la recycler view con i report giornalieri
    public void showAllDailyReport(View view){

        viewReportRecycler.setVisibility(View.VISIBLE);
        dailyReportText.setVisibility(View.VISIBLE);

        //recupero i report dal DB
        infoViewModel.getAllInfoInDate(dataClick).observeForever(new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> infos) {

                //setto la lista di dati che ho appena recuperato dal DB nel recyclerViewAdapter
                infoListAdapter.setInfos(infos);

                //ASYNC TASK per aggiornare le medie giornaliere e le icone del calendario nel caso modifico i report o li elimino
                showAverageDay(dataClick);
                showIconCalendar();
            }
        });

    }


    //=====================//
    // FUNZIONI  ASINCRONE //
    //=====================//

    //funzione che mostra le icone nel giorno in cui sono presenti dei report
    public void showIconCalendar(){
        ShowIconAsyncTask task = new ShowIconAsyncTask();
        task.execute();
    }


    private class ShowIconAsyncTask extends AsyncTask<Void, Void, Void>{

        private List<String> eventsString;
        private List<EventDay> events;

        //costruttore
        public ShowIconAsyncTask() {
            super();
            this.events = new ArrayList<>();
            this.eventsString = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //lista di date in formato string
            eventsString = infoViewModel.getAllDatesOnce();

            //var da riutilizzare nel ciclo
            String dataString = "";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfNew = new SimpleDateFormat("MM/dd/yyyy");
            Date date = null;   // DateFormat.getDateInstance();
            EventDay eventDay = null;

            for(int i = 0; i < eventsString.size(); i++){

                //ho la data come stringa MM/dd/yyyy
                dataString = eventsString.get(i);

                //trasformo la stringa in SimpleDateFormat
                try {
                    date = sdfNew.parse(dataString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //istanza calendario - serve una nuova istanza ad ogni ciclo
                Calendar cal = Calendar.getInstance();

                //setto il calendar.instance all'i-esima data
                cal.setTime(date);

                //event day con la data e l'immagine da inserire ne calendario
                eventDay = new EventDay(cal, R.drawable.ic_baseline_turned_in_24);

                //aggiungo l'event day nella lista di eventi
                events.add(eventDay);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //setto la calendar view
            super.onPostExecute(aVoid);

            //inserisco la lista di eventi nella view
            calendarView.setEvents(events);
        }
    }


    //funzione che chiama l'async task per mostrare i valori medi
    public void showAverageDay(String data){
        AverageAsyncTask task = new AverageAsyncTask();
        task.execute(data);
    }


    //Classe asincrona che aggiorna la UI
    private class AverageAsyncTask extends AsyncTask<String, Void, Void> {

        double temp, sis, dia, glic, peso;

        @Override
        protected Void doInBackground(String... data) {

            temp = infoViewModel.getAverageTemperatura(data[0], data[0]);
            sis = infoViewModel.getAveragePressioneSistolica(data[0], data[0]);
            dia = infoViewModel.getAveragePressioneDiastolica(data[0], data[0]);
            glic = infoViewModel.getAverageGlicemia(data[0], data[0]);
            peso = infoViewModel.getAveragePeso(data[0], data[0]);

            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //tronca il valore dopo una cifra decimale
            temperaturaAVG.setText(String.format("%.1f", temp));
            presSisAVG.setText(String.format("%.1f", sis));
            presDiaAVG.setText(String.format("%.1f", dia));
            glicemiaAVG.setText(String.format("%.1f", glic));
            pesoAVG.setText(String.format("%.1f", peso));

        }
    }


}
