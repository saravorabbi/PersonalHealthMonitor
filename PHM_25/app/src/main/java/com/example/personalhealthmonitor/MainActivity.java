package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.example.personalhealthmonitor.notifications.MonitorJobService;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG";
    private final String TAG = this.getClass().getSimpleName();

    public static SettingsViewModel settingsViewModel;

    //shared prefs
    public static final String SHARED_PREF = "sharedPref";
    public static final String DB_CREATED = "dbCreated";

//    public static final String FIRST_RUN_MAIN = "MainActivityFirstRun";
    public static final String FIRST_RUN_SETTINGS = "SettingsActivityFirstRun";
//    AL POSTO DI db_CREATED POTREI USARE QUESTE DUE VAR PER ESEGUIRE LE AZIONI SOLO UNA VOLTA PER TUTTA LA VITA DELL'APP

    public static final String NOTIFICATION_ON = "DailyNotifications";
    public static final String NEW_REPORT = "AddedReport";
    public static final String DAILY_TIME = "OraNotificaGiornaliera";

    //costante
    public static final String DAILY_TIME_DEFAULT = "false";


    //MONITORAGGIO DEI PARAMETRI
    public static final String MONITORA_TEMP = "MonitoraggioTemperatura";
    public static final String MONITORA_PRESS_SIS = "MonitoraggioPressioneSistolica";
    public static final String MONITORA_PRESS_DIA = "MonitoraggioPressioneDiastolica";
    public static final String MONITORA_GLIC = "MonitoraggioGlicemia";
    public static final String MONITORA_PESO = "MonitoraggioPeso";

    //MONITORAGGIO DEI PARAMETRI
    public static final String EXCEEDED_TEMP = "SuperamentoTemperatura";
    public static final String EXCEEDED_PRESS_SIS = "SuperamentoPressioneSistolica";
    public static final String EXCEEDED_PRESS_DIA = "SuperamentoPressioneDiastolica";
    public static final String EXCEEDED_GLIC = "SuperamentoGlicemia";
    public static final String EXCEEDED_PESO = "SuperamentoPeso";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton addRepo = findViewById(R.id.newReport);
        Button calendar = findViewById(R.id.calendar);
        Button graphs = findViewById(R.id.graphs);
        Button exportRepo = findViewById(R.id.export);
        Button settings = findViewById(R.id.settings);



        //DB settings
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        boolean notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(DB_CREATED, true);
        Log.i(TAG, "notCreated deve essere true per creare il db, è false se l'ha già creato = " + notCreated );


        if(notCreated) {
            //creo DB settings
            //creo shared pref delle notifiche
            firstRun();
        }


//        //check delle shared prefs
//        boolean dbCreato = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(DB_CREATED, true);
//        Log.i(TAG, "DB_CREATED = " + dbCreato );
//
//        boolean isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
//        Log.i(TAG, "NOTIFICATION_ON = " + isTimeCheck );
//
//        boolean isRepoAdded = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NEW_REPORT, true);
//        Log.i(TAG, "ADDED_REPO = " + isRepoAdded );
//
//        String dailyTime = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(DAILY_TIME, DAILY_TIME_DEFAULT);
//        Log.i(TAG, "DAILY_TIME = " + dailyTime );



        // ================ //
        // LISTENER BUTTONS //
        // ================ //

        //Listener Add Report
        addRepo.setOnClickListener(view -> goToAddNewReportActivity());

        //Listener Calendario
        calendar.setOnClickListener(view -> goToCalendarActivity());

        //Listener Grafi
        graphs.setOnClickListener(view -> goToGraphActivity());

        //Listener Export Report
        exportRepo.setOnClickListener(view -> {
            Snackbar.make(view, "Plot dei data!! DA FARE", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        });


        //Listener Settings
        settings.setOnClickListener(view -> goToSettingsActivity());

    }//END ONCREATE


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "CHIAMATA ON RESTART");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "CHIAMATA ON DESTROY");
    }


    // Creazione Settings DataBase e Shared Preferences
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
        Log.i(TAG, "notCreated = " + dbCreato );

        // Setto la shared_pref delle notifiche a FALSE (notifiche disattivate)
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NOTIFICATION_ON, false).apply();
        boolean isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
        Log.i(TAG, "NOTIFICATION_ON = " + isTimeCheck );

        // Setto la shared_pref delle notifiche a FALSE -> le notifiche sono disattivat e non deve arrivare la notifica (aggiunta del report)
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NEW_REPORT, false).apply();
        boolean isRepoAdded = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NEW_REPORT, true);
        Log.i(TAG, "ADDED_REPO = " + isRepoAdded );

        // Setto la shared_pref dell'ora delle notifiche -> se settata a "false" non ho ancora attivato le notifiche
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putString(DAILY_TIME, DAILY_TIME_DEFAULT).apply();
        String dailyTime = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(DAILY_TIME, DAILY_TIME_DEFAULT);
        Log.i(TAG, "DAILY_TIME = " + dailyTime );

        //Shared prefs del monitoraggio
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



    private void goToSettingsActivity() {
        Log.i(DEBUG, "listener settings");
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}