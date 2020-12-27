package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;
import static com.example.personalhealthmonitor.utils.Utils.*;


//Activity per creare un nuovo report

public class NewInfoActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private Double[] parametri;

    private EditText temperaturaNewET, pressioneSISNewET, pressioneDIANewET, glicemiaNewET, pesoNewET, notaNewET;

    private Button saveRepo;

    public static InfoViewModel infoViewModelNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        temperaturaNewET = findViewById(R.id.new_temperatura);
        pressioneSISNewET = findViewById(R.id.new_pressione_sistolica);
        pressioneDIANewET = findViewById(R.id.new_pressione_diastolica);
        glicemiaNewET = findViewById(R.id.new_glicemia);
        pesoNewET = findViewById(R.id.new_peso);
        notaNewET = findViewById(R.id.new_nota);

        saveRepo = findViewById(R.id.saveNewReport);

        infoViewModelNew = ViewModelProviders.of(this).get(InfoViewModel.class);

        parametri = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};


        //dismiss la notifica quando clicco Add
        boolean notifyON = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
        Log.i(TAG, "NOTIFICATION_ON = " + notifyON );
        if(notifyON){
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.cancel(ID_NOTIFICATION_DAILY);
            notificationManager.cancel(ID_NOTIFICATION_REMINDER);
        }

        //Salvataggio del report nel DB
        saveRepo.setOnClickListener(view -> {

            Log.i(TAG, "1" );
            boolean correct = isCorrect(
                    temperaturaNewET.getText().toString(),
                    pressioneSISNewET.getText().toString(),
                    pressioneDIANewET.getText().toString(),
                    glicemiaNewET.getText().toString(),
                    pesoNewET.getText().toString(),
                    getApplicationContext(),
                    parametri
            );


            //almeno 3 campi devono essere riempiti, altrimenti non salva il report -> errore
            if( correct ){

                //prendo i dati dagli edit text
                double newTemp = parametri[0];
                double newPressSis = parametri[1];
                double newPressDia = parametri[2];
                double newGlic =  parametri[3];
                double newPeso =  parametri[4];
                String newNota = notaNewET.getText().toString();

                //creo id database
                String id = UUID.randomUUID().toString();

                //creo la data nel formato "dd/mm/yyyy"
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String data = sdf.format(calendar.getTime());

                //creo nuovo report
                Info info = new Info(id, newTemp, newPressSis, newPressDia, newGlic, newPeso, newNota, data);

                //inserisco il report nel DataBase
                infoViewModelNew.insert(info);


                // NOTIFICHE
                if(notifyON){

                    String dailyTime = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(DAILY_TIME, DAILY_TIME_DEFAULT);
                    Log.i(TAG, "DAILY_TIME = " + dailyTime );

                    //creo il calendario col tempo dentro DAILY_TIME
                    String ore = dailyTime.substring(0, 2);
                    String minuti = dailyTime.substring(3, 5);

                    int oreint = Integer.parseInt(ore);
                    int minutiint = Integer.parseInt(minuti);

                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, oreint);
                    c.set(Calendar.MINUTE, minutiint);
                    c.set(Calendar.SECOND, 0);

                    //ora attuale avviene prima dell'ora della notifica
                    if( Calendar.getInstance().before(c) ){
                        //non devo inviare la notifica NEW_REPORT = false
                        Log.i(TAG, "NO NOTIFICA -  NEW_REPORT = false");
                        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NEW_REPORT, false).apply();

                    }else{
                        //devo inviare la notifica NEW_REPORT = true
                        Log.i(TAG, "SI NOTIFICA - NEW_REPORT = true");
                        getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NEW_REPORT, true).apply();
                    }

                    Log.i(TAG, "NEW_REPORT = " + getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NEW_REPORT,true) );

                }else{
                    Log.i(TAG, "notifiche spente");
                }
                //END NOTIFICHE GIORNALIERE


                Toast.makeText(getApplicationContext(),"Inserito",Toast.LENGTH_SHORT ).show();
                Log.i(TAG, "inserito");

                //activity conclusa
                finish();

            }
        });
    }

}