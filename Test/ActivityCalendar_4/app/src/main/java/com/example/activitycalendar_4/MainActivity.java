package com.example.activitycalendar_4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG: MainActivity";

    private TextView textView;
    private String prova1, prova2, data, data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        //SIMPLE DATE FORMAT
        //dd-MM-yyyy
        Calendar calendarSimple = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateSimple = sdf.format(calendarSimple.getTime());
        Log.i(TAG, "SIMPLE DATE FORMAT " + currentDateSimple); //stampa => 02/12/2020

        //MM-dd-yyyy
        Calendar calendarStorto = Calendar.getInstance();
        SimpleDateFormat sdfStorto = new SimpleDateFormat("MM/dd/yyyy");
        String simpleStorta = sdfStorto.format(calendarStorto.getTime());
        Log.i(TAG, "SIMPLE DATE FORMAT STORTA " + simpleStorta); //stampa => 12/25/2020
        //END simple date format


        data =  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
        data2 = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());

        Log.i(TAG, "QUI DATA E ORA " + data + " QUI DATA " + data2);


        Log.i(TAG, "===========================================================");

        //EVENTI nel calendario
        //array dove inserire gli eventi
        List<EventDay> events = new ArrayList<>();

        //istanza calendario
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        //Log.i(TAG, "SUPER CALENDAIO CHE STO USANDO: " + String.valueOf(calendar));

        //ho la data come string mm/dd/yyyy
        String dataString = "12/25/2020";
        String dataString2 = "12/27/2020";

        //trasformo
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfNew = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null;   // DateFormat.getDateInstance();
        try {
            date1 = sdfNew.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date2 = null;
        try {
            date2 = sdfNew.parse(dataString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i(TAG, String.valueOf(date1));
        Log.i(TAG, String.valueOf(date2));
//1
        calendar1.setTime(date1);

        //event day con la data e l'immagine da inserire ne calendario
        EventDay giorno1 = new EventDay(calendar1, R.drawable.ic_baseline_turned_in_24);

        //aggiungo l'event day nella lista di eventi
        events.add(giorno1);

//2
        calendar2.setTime(date2);

        //event day con la data e l'immagine da inserire ne calendario
        EventDay giorno2 = new EventDay(calendar2, R.drawable.ic_baseline_turned_in_24);

        //aggiungo l'event day nella lista di eventi
        events.add(giorno2);


        //inserisco la lista di eventi nella view
        calendarView.setEvents(events);

        Log.i(TAG, "===========================================================");
//        //date = reports.get(i).getGiorno();
//        int year = 2020;
//        int month = 11;
//        int giornoo = 24;
//
//        //Date dateProva = new Date( year,  month, giornoo);
//        Date dateProva = new Date();
//        Calendar calendarProva = Calendar.getInstance();
//
//        calendarProva.setTime(dateProva);
//
//        Log.i(TAG, String.valueOf(calendarProva.getTime()) );
//
//        events.add(new EventDay(calendarProva, R.drawable.ic_baseline_library_add_check_24));
//        events.add(new EventDay(calendarProva, R.drawable.ic_baseline_library_add_check_24));
//        calendarView.setEvents(events);
//
//        //setta le immagini nel calendario
//        calendarView.setEvents(events);







        //click di una data
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                //Calendar clickedDayCalendar = eventDay.getCalendar();

                //DATA NEL FORMATO 02/12/2020
                Log.i(TAG, "INIZIO PROVE ORAAA");
                String uno = String.valueOf( eventDay.getCalendar().get(Calendar.DATE) );
                if( uno.length() == 1 ){
                    //ho una cifra
                    uno = "0" + uno;
                    Log.i(TAG, "Inserisco lo 0 " + uno);

                }
                Log.i(TAG, "DATE " + uno);

                String sette = String.valueOf( eventDay.getCalendar().get(Calendar.DAY_OF_MONTH) );
                Log.i(TAG, "DAY_OF_MONTH è " + sette);
                if( sette.length() == 1 ){
                    //ho una cifra
                    sette = "0" + sette;
                    Log.i(TAG, "Inserisco lo 0 " + sette);

                }
                Log.i(TAG, "DAY_OF_MONTH di nuovo è " + sette);


                String due = String.valueOf( eventDay.getCalendar().get(Calendar.SHORT) );
                Log.i(TAG, "La data è " + due);
                String tre = String.valueOf( eventDay.getCalendar().get(Calendar.SHORT_FORMAT) );
                Log.i(TAG, "La data è " + tre);

                //eventDay.getCalendar().getTime();
                String quattro = String.valueOf( eventDay.getCalendar().getTime() );
                Log.i(TAG, "La data è " + quattro);




                String year = String.valueOf( eventDay.getCalendar().get(Calendar.YEAR) );
                String month = String.valueOf( eventDay.getCalendar().get(Calendar.MONTH) );
                String day = String.valueOf( eventDay.getCalendar().get(Calendar.DAY_OF_MONTH) );

                String dataa = month + "/" + day + "/" + year;
                Log.i(TAG, "La data è " + dataa);


                Calendar cal1 = Calendar.getInstance();

                //prende info dalla data attuale
                int mese = cal1.get(Calendar.MONTH);
                Log.i(TAG, String.valueOf(mese));

                //prende info dalla data selezionata
                int meseDinamico = eventDay.getCalendar().get(Calendar.MONTH);
                Log.i(TAG, String.valueOf(meseDinamico));

                //prende info dalla data attuale
                Date data = Calendar.getInstance().getTime();
                prova1 = data.toString();   //stamapa .toString() -> Thu Nov 12 10:47:00 GMT+00:00 2020

                prova2 = eventDay.getCalendar().getTime().toString();    //prende il giorno corrente selezionato -> Fri Nov 20 00:00:00 GMT+00:00 2020



                String prova;
                prova = eventDay.getCalendar().getTime().toString() + " Funziona? ";
                textView = findViewById(R.id.dataCal);
                textView.setText(prova);

/*
                calendarView.setOnDayClickListener(eventDay ->
                        Toast.makeText(getApplicationContext(), eventDay.getCalendar().getTime().toString() + " " + eventDay.isEnabled(), Toast.LENGTH_SHORT).show());
*/

            }
        });

    }
}