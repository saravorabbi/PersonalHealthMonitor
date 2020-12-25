package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public static SettingsViewModel settingsViewModel;


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

        boolean notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(FIRST_RUN_MAIN, true);
        Log.i(TAG, "notCreated deve essere true per creare il db, è false se l'ha già creato = " + notCreated );


        if(notCreated) {
            //creo DB settings
            //creo shared pref delle notifiche
            firstRun();
        }


//        //check delle shared prefs
//        boolean dbCreato = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(FIRST_RUN_MAIN, true);
//        Log.i(TAG, "FIRST_RUN_MAIN = " + dbCreato );
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

        //Listener Export Report
        exportRepo.setOnClickListener(view -> {
            Log.i(TAG, "listener graph");
            Snackbar.make(view, "Plot dei data!! DA FARE", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "CHIAMATA ON DESTROY");
    }


    // Creazione Settings DataBase e Shared Preferences
    private void firstRun() {

        // Creo i settings nel DataBase
        Settings tempSet = new Settings(1,"Temperatura", 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(tempSet);
        Settings pressSisSet = new Settings(2,"Pressione Sistolica", 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pressSisSet);
        Settings pressDiaSet = new Settings(3,"Pressione Diastolica", 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pressDiaSet);
        Settings glicSet = new Settings(4,"Glicemia", 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(glicSet);
        Settings pesoSet = new Settings(5,"Peso", 1, 0, 0, "12/02/2020", "12/07/2020");
        settingsViewModel.insertSettings(pesoSet);

        //Shared prefs del DB = false (quando apro l'app per la prima volta è TRUE)
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(FIRST_RUN_MAIN, false).apply();
        boolean dbCreato = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(FIRST_RUN_MAIN, true);
        Log.i(TAG, "notCreated = " + dbCreato );

        //Shared prefs - notifiche giornaliere disattivate = FALSE
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NOTIFICATION_ON, false).apply();
        boolean isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
        Log.i(TAG, "NOTIFICATION_ON = " + isTimeCheck );

        //Shared prefs - report giornaliero aggiunto = FALSE
        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NEW_REPORT, false).apply();
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

    }

}