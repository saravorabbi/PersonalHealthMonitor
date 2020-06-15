package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button buttonAddReport;
    private Button buttonCalendario;
    private Button buttonGrafico;
    private Button buttonStorico;
    private Button buttonImpostazioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //associo il testo della home a dei bottoni attraverso l'id
        buttonAddReport = findViewById(R.id.addReport);
        buttonCalendario = findViewById(R.id.calendario);
        buttonGrafico = findViewById(R.id.grafici);
        buttonStorico = findViewById(R.id.storico);
        buttonImpostazioni = findViewById(R.id.impostazioni);

        //metto i bottini in ascolto, se vengono cliccati dovrebbero far partire o activity o fragment
        /*buttonAddReport.setOnClickListener(this);
        buttonCalendario.setOnClickListener(this);
        buttonGrafico.setOnClickListener(this);
        buttonStorico.setOnClickListener(this);
        buttonImpostazioni.setOnClickListener();


    new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,StandingsActivity.class));
        }

    }
    */

    }







}
