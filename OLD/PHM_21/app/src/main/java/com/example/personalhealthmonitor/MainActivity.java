package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG";
    private String TAG = this.getClass().getSimpleName();

    private ImageButton addRepo;
    private Button calendar;
    private Button graphs;
    private Button exportRepo;
    private Button settings;

    public static SettingsViewModel settingsViewModel;

    //shared prefs
    public static final String SHARED_PREF = "sharedPref";
    public static final String DB_CREATED = "dbCreated";
    public static final String IS_TIME_ON = "DailyNotifications";
    public static final String ADDED_REPO = "AddedReport";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRepo = findViewById(R.id.newReport);
        calendar = findViewById(R.id.calendar);
        graphs = findViewById(R.id.graphs);
        exportRepo = findViewById(R.id.export);
        settings = findViewById(R.id.settings);


        //DB settings -> DA ESEGUIRE UNA VOLTA E BASTAAAA!!!!
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        boolean notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(DB_CREATED, true);
        Log.i(TAG, "notCreated deve essere true per creare il db, è false se l'ha già creato = " + String.valueOf(notCreated));

//        Decommenta per mettere il first run a true (e riesegui il codice)
//        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(DB_CREATED, true).apply();
//        notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(DB_CREATED, true);
//        Log.i(TAG, "DUE = " + String.valueOf(notCreated));    //se è a true allora crea la tabella settings
//        END PROVE SHARED PREFERENCES

        if(notCreated) {
            //creo DB settings
            //creo shared pref delle notifiche
            firstRun();
        }




        // ================ //
        // LISTENER BUTTONS //
        // ================ //

        //Listener Add Report
        addRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddNewReportActivity();
            }
        });

        //Listener Calendario
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCalendarActivity();
            }
        });

        //Listener Grafi
        graphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGraphActivity();
            }
        });

        //Listener Export Report
        exportRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "boh -> fai plot dei data?", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                goToExportActivity();
            }
        });

        //Listener Settings
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettingsActivity();
            }
        });
    }//END ONCREATE




    private void firstRun() {

        // Creo i settings nel DataBase
        Settings tempSet = new Settings(1,"Temperatura", 0, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(tempSet);
        Settings pressSisSet = new Settings(2,"Pressione Sistolica", 0, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pressSisSet);
        Settings pressDiaSet = new Settings(3,"Pressione Diastolica", 0, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pressDiaSet);
        Settings glicSet = new Settings(4,"Glicemia", 0, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(glicSet);
        Settings pesoSet = new Settings(5,"Peso", 0, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pesoSet);

        // Setto la shared_pref del DB a false (quando apro l'app per la prima volta è TRUE)
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(DB_CREATED, false).apply();
        boolean dbCreato = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(DB_CREATED, true);
        Log.i(TAG, "notCreated = " + String.valueOf(dbCreato));


        // Setto la shared_pref delle notifiche a FALSE (notifiche disattivate)
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(IS_TIME_ON, false).apply();
        boolean isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(IS_TIME_ON, true);
        Log.i(TAG, "IS_TIME_ON = " + String.valueOf(isTimeCheck));

        // Setto la shared_pref delle notifiche a FALSE -> le notifiche sono disattivat e non deve arrivare la notifica (aggiunta del report)
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(ADDED_REPO, false).apply();
        boolean isRepoAdded = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(ADDED_REPO, true);
        Log.i(TAG, "ADDED_REPO = " + String.valueOf(isRepoAdded));

    }






    // ================ //
    // INTENT FUNCTIONS //
    // ================ //

    //Funzioni di supporto per andare nelle Activity
    private void goToAddNewReportActivity() {
        Log.i(DEBUG, "listener new report");
        Intent intent = new Intent(MainActivity.this, NewInfoActivity.class);
        startActivity(intent);
    }

    //vado nell'activity del Calendario
    public void goToCalendarActivity(){
        Log.i(DEBUG, "listener calendar");
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    private void goToGraphActivity() {
        Log.i(DEBUG, "listener graph");
        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
        startActivity(intent);
    }

    private void goToExportActivity() {
        Log.i(DEBUG, "listener export");
    }

    private void goToSettingsActivity() {
        Log.i(DEBUG, "listener settings");
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}