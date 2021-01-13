package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG";
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;

    private ImageButton addRepo;
    private Button calendar;
    private Button graphs;
    private Button exportRepo;
    private Button settings;

    private InfoViewModel infoViewModel;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRepo = findViewById(R.id.newReport);
        calendar = findViewById(R.id.calendar);
        graphs = findViewById(R.id.graphs);
        exportRepo = findViewById(R.id.export);
        settings = findViewById(R.id.settings);

        //DB
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);




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







    //Funzioni di supporto
    private void goToAddNewReportActivity() {
        Log.i(DEBUG, "listener new report");
        Intent intent = new Intent(MainActivity.this, NewInfoActivity.class);
        startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
    }

    //vado nell'activity del Calendario
    public void goToCalendarActivity(){
        Log.i(DEBUG, "listener calendar");
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    private void goToGraphActivity() {
        Log.i(DEBUG, "listener graph");
    }

    private void goToExportActivity() {
        Log.i(DEBUG, "listener export");
    }

    private void goToSettingsActivity() {
        Log.i(DEBUG, "listener settings");
    }
}