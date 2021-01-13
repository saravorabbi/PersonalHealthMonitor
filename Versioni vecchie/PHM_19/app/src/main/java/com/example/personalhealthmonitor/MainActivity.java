package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static final String SHARED_PREF = "sharedPref";
    public static final String DB_CREATED = "dbCreated";




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

            getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(DB_CREATED, false).apply();
            notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(DB_CREATED, true);
            Log.i(TAG, "notCreated = " + String.valueOf(notCreated));
        }


//        PROVE SHARED PREFERENCES
//        boolean firstRun = getSharedPreferences("preferences", MODE_PRIVATE).getBoolean("firstRun", true);
//        Log.i(TAG, "firstRun = " + String.valueOf(firstRun));

//        Decommenta per mettere il first run a true (e rieseguire il codice
//        getSharedPreferences("preferences", MODE_PRIVATE).edit().putBoolean("firstRun", true).apply();
//        firstRun = getSharedPreferences("preferences", MODE_PRIVATE).getBoolean("firstRun", true);
//        Log.i(TAG, "DUE = " + String.valueOf(firstRun));
//        END PROVE SHARED PREFERENCES


//        if(firstRun){
//            getSharedPreferences("preferences", MODE_PRIVATE).edit().putBoolean("firstRun", false).apply();
//            Log.i(TAG, "DUE = " + String.valueOf(firstRun));
//        }




  /*      int i = 0;
        double a = 0.0;
        float c = 12;
        float f = (float) i;
        float r = (float) a;
        Log.i(TAG, "Intero " + i);
        Log.i(TAG, "Double " + a);
        Log.i(TAG, "Float " + c);
        Log.i(TAG, "Float cast intero " + f);
        Log.i(TAG, "Float cast double " + r);
*/

        //LISTENER BUTTONS

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
    }
    //END ONCREATE


    //crea la tabella dei Settings la prima volta che apro l'app
    public void createSettingFirstTime(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(DB_CREATED, true);
    }



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