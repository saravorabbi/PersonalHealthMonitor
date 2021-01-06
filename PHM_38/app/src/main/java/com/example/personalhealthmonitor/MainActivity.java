package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.SettingsViewModel;


import static com.example.personalhealthmonitor.utils.UtilsValue.*;
import static com.example.personalhealthmonitor.utils.UtilsMain.*;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public static SettingsViewModel settingsViewModel;
    public static InfoViewModel infoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView addRepo = findViewById(R.id.newReport);
        Button calendar = findViewById(R.id.calendar);
        Button graphs = findViewById(R.id.graphs);
        Button filters = findViewById(R.id.filters);
        Button settings = findViewById(R.id.settings);

        //DB settings & report
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);


        //check first run
        boolean firstRun = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(FIRST_RUN_MAIN, true);

        if(firstRun) {
            Log.d(TAG, "FIRST RUN");
            setSharedPrefs(getApplicationContext());
            addDBElements();
        }

        //aggiorno il valore dei report aggiunti
        showNumberOfReportAddedToday(MainActivity.this);
        //aggiorno la media dei valori aggiunti
        showAverageParameters(MainActivity.this);


        // ================ //
        // LISTENER BUTTONS //
        // ================ //

        //Listener New Info
        addRepo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewInfoActivity.class);
            startActivity(intent);
        });

        //Listener Calendario
        calendar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);

        });

        //Listener Grafi
        graphs.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, GraphActivity.class);
            startActivity(intent);
        });

        //Listener Filtri Report
        filters.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DataFilterActivity.class);
            startActivity(intent);
        });


        //Listener Settings
        settings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

    }



    @Override
    protected void onRestart() {
        super.onRestart();

        //aggiorno il count dei report
        showNumberOfReportAddedToday(MainActivity.this);
        //aggiorno la media dei parametri
        showAverageParameters(MainActivity.this);
    }


}