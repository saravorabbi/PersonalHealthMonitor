package com.example.personalhealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.example.personalhealthmonitor.MainActivity.DAILY_TIME;
import static com.example.personalhealthmonitor.MainActivity.DAILY_TIME_DEFAULT;
import static com.example.personalhealthmonitor.MainActivity.NEW_REPORT;
import static com.example.personalhealthmonitor.MainActivity.NOTIFICATION_ON;
import static com.example.personalhealthmonitor.MainActivity.SHARED_PREF;
import static com.example.personalhealthmonitor.notifications.AlertReceiver.ID_NOTIFICATION_DAILY;
import static com.example.personalhealthmonitor.notifications.AlertReceiver.ID_NOTIFICATION_REMINDER;

//Activity per creare un nuovo report

public class NewInfoActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private EditText temperaturaET, pressione_sistolicaET, pressione_diastolicaET, glicemiaET, pesoET, notaET;
    private double temperaturaD, pressioneSisD, pressioneDiaD, glicemiaD, pesoD;
    private String notaS;

    private Button saveRepo;

    public static InfoViewModel infoViewModelNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        temperaturaET = findViewById(R.id.new_temperatura);
        pressione_sistolicaET = findViewById(R.id.new_pressione_sistolica);
        pressione_diastolicaET = findViewById(R.id.new_pressione_diastolica);
        glicemiaET = findViewById(R.id.new_glicemia);
        pesoET = findViewById(R.id.new_peso);
        notaET = findViewById(R.id.new_nota);

        saveRepo = findViewById(R.id.saveNewReport);

        infoViewModelNew = ViewModelProviders.of(this).get(InfoViewModel.class);


        //dismiss la notifica quando clicco Add
        boolean notifyON = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
        Log.i(TAG, "NOTIFICATION_ON = " + notifyON );
        if(notifyON){
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.cancel(ID_NOTIFICATION_DAILY);
            notificationManager.cancel(ID_NOTIFICATION_REMINDER);
        }

        //Salvataggio del report nel DB
        saveRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //almeno 3 campi devono essere riempiti, altrimenti non salva il report -> errore
                if( isCorrect() ){

                    //creo id database
                    String id = UUID.randomUUID().toString();

                    //creo la data nel formato "dd/mm/yyyy"
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String data = sdf.format(calendar.getTime());

                    //creo nuovo report
                    Info info = new Info(id, temperaturaD, pressioneSisD, pressioneDiaD, glicemiaD, pesoD, notaS, data);

                    //inserisco il report nel DataBase
                    infoViewModelNew.insert(info);

                    //NOTIFICHE
                    //check
//                    boolean notifyON = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
//                    Log.i(TAG, "NOTIFICATION_ON = " + notifyON );

                    boolean addedRepo = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NEW_REPORT, true);
                    Log.i(TAG, "ADDED_REPO = " + addedRepo );

                    String timeNotify = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(DAILY_TIME, DAILY_TIME_DEFAULT);
                    Log.i(TAG, "DAILY_TIME = " + timeNotify);

                    if(notifyON){

                        String dailyTime = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(DAILY_TIME, DAILY_TIME_DEFAULT);
                        Log.i(TAG, "DAILY_TIME = " + dailyTime );

                        //setto il calendario col tempo dentro DAILY_TIME
                        String ore = dailyTime.substring(0, 2);
                        String minuti = dailyTime.substring(3, 5);

//                        Log.i(TAG, "Ore " + dailyTime);
//                        Log.i(TAG, "Ora: " + ore + " minuti " + minuti);

                        int oreint = Integer.parseInt(ore);
                        int minutiint = Integer.parseInt(minuti);

                        Log.i(TAG, "Ora " + oreint + " minuti " + minutiint);

                        Calendar c = Calendar.getInstance();

                        c.set(Calendar.HOUR_OF_DAY, oreint);
                        c.set(Calendar.MINUTE, minutiint);
                        c.set(Calendar.SECOND, 0);

                        //ora attuale avviene prima dell'ora della notifica
                        if( Calendar.getInstance().before(c) ){
                            //non devo inviare la notifica NEW_REPORT = false
                            Log.i(TAG, "non devo inviare la notifica NEW_REPORT = false");
                            getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NEW_REPORT, false).apply();

                        }else{
                            //devo inviare la notifica NEW_REPORT = true
                            Log.i(TAG, "devo inviare la notifica NEW_REPORT = true");
                            getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NEW_REPORT, true).apply();
                        }

                        Log.i(TAG, "NEW_REPORT = " + getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NEW_REPORT,true) );

                    }else{
                        Log.i(TAG, "notifiche spente");
                    }

                    //END NOTIFICHE

                    Toast.makeText(getApplicationContext(),"Inserito",Toast.LENGTH_SHORT ).show();
                    Log.i(TAG, "inserito");

                    //activity conclusa
                    finish();


                } else{
                    Toast.makeText(getApplicationContext(),"Inserisci più di un campo",Toast.LENGTH_LONG ).show();
                    Log.i(TAG, "non inserito");
                }


            }

        });
    }

/*
        // Funzione che controlla se TUTTI i campi sono vuoti -> non salva il report
        public boolean checkEmpty(){
            if( TextUtils.isEmpty(temperatura.getText()) ){
                if( TextUtils.isEmpty(pressione.getText()) ){
                    if( TextUtils.isEmpty(glicemia.getText()) ){
                        if( TextUtils.isEmpty(peso.getText()) ){
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        // Funzione che controlla che almeno 2 campi siano inseriti -> da toast di avvertimento
        public boolean checkCampi(){
            int i = 0;
            boolean check = false;

            //se un campo è vuoto incremento il contatore
            if( TextUtils.isEmpty(temperatura.getText()) ) i++;
            if( TextUtils.isEmpty(pressione.getText()) ) i++;
            if( TextUtils.isEmpty(glicemia.getText()) ) i++;
            if( TextUtils.isEmpty(temperatura.getText()) ) i++;
            if( TextUtils.isEmpty(peso.getText()) ) i++;

            //se ho più di un campo vuoto -> return true
            if(i > 1) check = true;

            return check;
        }
 */


    //true: i campi vanno bene
    //false: un campo non va bene
    public boolean  isCorrect(){

        int count = 0;  //conto i campi vuoti, se sono >= di 4 allora do errore

        //check temperatura => 34-42 °C
        if( TextUtils.isEmpty(temperaturaET.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "temp vuota - aggiorno il count");
        } else{
            temperaturaD =  Double.parseDouble(temperaturaET.getText().toString());

            if(temperaturaD > 42 || temperaturaD < 34){
                Toast.makeText(getApplicationContext(),"Inserisci una temperatura valida",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "temp non corretta");
                return false;
            }
            Log.i(TAG, "temp OK");
        }

        //check pressione sistolica => 80-190 mmHg
        if( TextUtils.isEmpty(pressione_sistolicaET.getText()) ){
            count++;
            Log.i(TAG, "press sistolica vuota - aggiorno il count");
        } else{
            pressioneSisD =  Double.parseDouble(pressione_sistolicaET.getText().toString());

            if(pressioneSisD > 190 || pressioneSisD < 80){
                Toast.makeText(getApplicationContext(),"Inserisci una pressione sistolica valida",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "pressione sistolica non corretta");
                return false;
            }
            Log.i(TAG, "press OK");
        }

        //check pressione diastolica => 50-120 mmHg
        if( TextUtils.isEmpty(pressione_diastolicaET.getText()) ){
            count++;
            Log.i(TAG, "press diastolica vuota - aggiorno il count");
        } else{
            pressioneDiaD =  Double.parseDouble(pressione_diastolicaET.getText().toString());

            if(pressioneDiaD > 120 || pressioneDiaD < 50){
                Toast.makeText(getApplicationContext(),"Inserisci una pressione diastolica valida",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "pressione diastolica non corretta");
                return false;
            }
            Log.i(TAG, "press OK");
        }


        //chek glicemia 50-250 mg/dl
        if( TextUtils.isEmpty(glicemiaET.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "glicemia vuota - aggiorno il count");
        } else{
            glicemiaD =  Double.parseDouble(glicemiaET.getText().toString());

            if(glicemiaD > 250 || glicemiaD < 50){
                Toast.makeText(getApplicationContext(),"Inserisci un indice glicemico valido",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "glicemia non corretta");
                return false;
            }
            Log.i(TAG, "glicemia OK");
        }

        //check peso => >0 kg
        if( TextUtils.isEmpty(pesoET.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "peso vuota - aggiorno il count");
        } else{
            pesoD =  Double.parseDouble(pesoET.getText().toString());

            if( pesoD < 0 ){
                Toast.makeText(getApplicationContext(),"Inserisci un peso valido",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "peso non corretta");
                return false;
            }
            Log.i(TAG, "peso OK");
        }

        //check nota
        if( TextUtils.isEmpty(notaET.getText()) ) {
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "nota vuota - aggiorno il count");
        }else{
            notaS = notaET.getText().toString();
            Log.i(TAG, "nota OK");
        }


        if(count >= 4) {
            Log.i(TAG, "ho inserito solo un campo");
            return false; // ho inserito solo un campo
        }

        Log.i(TAG, "return true");
        return true;
    }


}