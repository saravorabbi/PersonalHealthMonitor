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

import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG = "DEBUG";
    private String TAG = this.getClass().getSimpleName();

    private ImageButton addRepo;
    private Button calendar;
    private Button graphs;
    private Button exportRepo;
    private Button settings;

    SettingsViewModel settingsViewModel;


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

        Settings settings_prima_volta = new Settings(0, 0, 0, 0, 0);

        settingsViewModel.insertSettings(settings_prima_volta);








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