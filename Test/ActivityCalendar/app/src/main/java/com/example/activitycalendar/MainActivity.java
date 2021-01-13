package com.example.activitycalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //per calendario
    private static final String TAG = "MainActivity";
    private Button butCalendario;
    private TextView thedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CALENDARIO BEGIN
        //associo il testo della home a dei bottoni attraverso l'id
        butCalendario = findViewById(R.id.calendario);
        thedate = (TextView) findViewById(R.id.date);
        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");
        thedate.setText(date);
        //CALENDARIO END


    }

    //funzione chiamata quando clicco il pulsante CALENDARIO
    public void showCalendario(View view){
        //HOME->ACTIVITY DEL CALENDARIO
        Intent intent = new Intent(this, ActivityCalendario.class);
        startActivity(intent);
    }


}