package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public static SettingsViewModel settingsViewModel;
    public static InfoViewModel infoViewModel;

    private TextView nrRepo;
    private TextView tempView, sisView, diaView, glicView, pesoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView addRepo = findViewById(R.id.newReport);
        Button calendar = findViewById(R.id.calendar);
        Button graphs = findViewById(R.id.graphs);
        Button filters = findViewById(R.id.filters);
        Button settings = findViewById(R.id.settings);

        nrRepo = findViewById(R.id.repo_text_number);

        tempView = findViewById(R.id.main_temperatura_media);
        sisView = findViewById(R.id.main_pressione_sistolica_media);
        diaView = findViewById(R.id.main_pressione_diastolica_media);
        glicView = findViewById(R.id.main_glicemia_media);
        pesoView = findViewById(R.id.main_peso_media);


        //DB settings & report
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);


        boolean notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(FIRST_RUN_MAIN, true);
        Log.i(TAG, "notCreated deve essere true per creare il db, è false se l'ha già creato = " + notCreated );


        if(notCreated) {
            firstRun();
        }

        showNumberOfReportAddedToday();

        showAverageParameters();

        // ================ //
        // LISTENER BUTTONS //
        // ================ //

        //Listener New Info
        addRepo.setOnClickListener(view -> {
            Log.i(TAG, "listener new report");
            Intent intent = new Intent(MainActivity.this, NewInfoActivity.class);
            startActivity(intent);
        });

        //Listener Calendario
        calendar.setOnClickListener(view -> {
            Log.i(TAG, "listener calendar");
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);

        });

        //Listener Grafi
        graphs.setOnClickListener(view ->{
            Log.i(TAG, "listener graph");
            Intent intent = new Intent(MainActivity.this, GraphActivity.class);
            startActivity(intent);
        });

        //Listener Filtri Report
        filters.setOnClickListener(view -> {
            Log.i(TAG, "listener filter");
            Intent intent = new Intent(MainActivity.this, DataFilterActivity.class);
            startActivity(intent);
        });


        //Listener Settings
        settings.setOnClickListener(view -> {
            Log.i(TAG, "listener settings");
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

    }//END ONCREATE




    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "CHIAMATA ON RESTART");

        //aggiorno il count dei report
        showNumberOfReportAddedToday();
        //aggiorno la media dei parametri
        showAverageParameters();

    }


    //===========//
    // FIRST RUN //
    //===========//

    // Creazione Settings DataBase e Shared Preferences
    private void firstRun() {

        Log.i(TAG, "FIRST RUN");

        // Creo i settings nel DataBase
        Settings tempSet = new Settings(1, DB_TEMPERATURA, 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(tempSet);
        Settings pressSisSet = new Settings(2, DB_PRESSIONE_SISTOLICA, 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pressSisSet);
        Settings pressDiaSet = new Settings(3, DB_PRESSIONE_DIASTOLICA, 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pressDiaSet);
        Settings glicSet = new Settings(4, DB_GLICEMIA, 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(glicSet);
        Settings pesoSet = new Settings(5, DB_PESO, 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pesoSet);

        //Shared prefs del DB = false (quando apro l'app per la prima volta è TRUE)
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(FIRST_RUN_MAIN, false).apply();
        boolean dbCreato = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(FIRST_RUN_MAIN, true);
        Log.i(TAG, "FIRST_RUN_MAIN = " + dbCreato );

        //Shared prefs - notifiche giornaliere disattivate = FALSE
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NOTIFICATION_ON, false).apply();
        boolean isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
        Log.i(TAG, "NOTIFICATION_ON = " + isTimeCheck );

        //Shared prefs - report giornaliero = TRUE
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NEW_REPORT, true).apply();
        boolean isRepoAdded = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NEW_REPORT, true);
        Log.i(TAG, "ADDED_REPO = " + isRepoAdded );

        //Shared prefs - ora delle notifiche = "false" non ho ancora attivato le notifiche
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putString(DAILY_TIME, DAILY_TIME_DEFAULT).apply();
        String dailyTime = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(DAILY_TIME, DAILY_TIME_DEFAULT);
        Log.i(TAG, "DAILY_TIME = " + dailyTime );

        //Shared prefs per il monitoraggio
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_TEMP, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_PRESS_SIS, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_PRESS_DIA, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_GLIC, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_PESO, false).apply();

        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_TEMP, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_SIS, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_DIA, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_GLIC, false).apply();
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PESO, false).apply();

        //creo dei report statici
        Info info1 = new Info(UUID.randomUUID().toString(), 36, 120, 80, 90, 70, "Primo Report!", "12/06/2020");
        infoViewModel.insert(info1);
        Info info2 = new Info(UUID.randomUUID().toString(), 35.7, 122, 84, 92, 70.2, "Secondo Report!", "12/07/2020");
        infoViewModel.insert(info2);
        Info info3 = new Info(UUID.randomUUID().toString(), 35.9, 124, 86, 96, 70.6, "Terzo Report!", "12/08/2020");
        infoViewModel.insert(info3);
        Info info4 = new Info(UUID.randomUUID().toString(), 37.2, 117, 79, 98, 69.8, "Quarto Report!", "12/10/2020");
        infoViewModel.insert(info4);
        Info info5 = new Info(UUID.randomUUID().toString(), 36.8, 115, 82, 88, 70.1, "Quinto Report!", "12/11/2020");
        infoViewModel.insert(info5);
    }



    //==================//
    // CLASSI ASINCRONE //
    //==================//

    public void showNumberOfReportAddedToday(){
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String today = sdf.format(cal.getTime());

        NrReportAsyncTask task = new NrReportAsyncTask();
        task.execute(today);
    }

    private class NrReportAsyncTask extends AsyncTask<String, Void, Void> {

        int nr = 0;

        @Override
        protected Void doInBackground(String... strings) {
            nr = infoViewModel.getReportsCount(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            nrRepo.setText("Ciao! Oggi hai aggiunto " + nr + " report");
        }
    }


    //funzione che chiama l'async task per mostrare i valori medi
    public void showAverageParameters(){
        AverageParamAsyncTask task = new AverageParamAsyncTask();
        task.execute();
    }


    //Classe asincrona che aggiorna la UI
    private class AverageParamAsyncTask extends AsyncTask<Void, Void, Void> {

        double temp, sis, dia, glic, peso;

        @Override
        protected Void doInBackground(Void... voids) {

            //creo data nel formato MM/dd/yyyy
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String today = sdf.format(calendar.getTime());

            Log.i(TAG, "DATA ASYNC= " + today);

            temp = infoViewModel.getAverageTemperatura(today, today);
            sis = infoViewModel.getAveragePressioneSistolica(today, today);
            dia = infoViewModel.getAveragePressioneDiastolica(today, today);
            glic = infoViewModel.getAverageGlicemia(today, today);
            peso = infoViewModel.getAveragePeso(today, today);

            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //tronca il valore dopo una cifra decimale
            tempView.setText(String.format("%.1f", temp));
            sisView.setText(String.format("%.1f", sis));
            diaView.setText(String.format("%.1f", dia));
            glicView.setText(String.format("%.1f", glic));
            pesoView.setText(String.format("%.1f", peso));
        }
    }



}