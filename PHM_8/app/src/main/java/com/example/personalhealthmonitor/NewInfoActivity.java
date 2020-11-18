package com.example.personalhealthmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

//Activity per creare un nuovo report

public class NewInfoActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private EditText temperaturaET, pressioneET, glicemiaET, pesoET, notaET;
    private double temperaturaD, pressioneD, glicemiaD, pesoD;
    private String notaS;

    private Button saveRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        temperaturaET = findViewById(R.id.new_temperatura);
        pressioneET = findViewById(R.id.new_pressione);
        glicemiaET = findViewById(R.id.new_glicemia);
        pesoET = findViewById(R.id.new_peso);
        notaET = findViewById(R.id.new_nota);



        //Salvataggio del report nel DB
        saveRepo = findViewById(R.id.saveNewReport);
        saveRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //almeno 3 campi devono essere riempiti, altrimenti non salva il report -> errore
                if( isCorrect() ){

                    String id = UUID.randomUUID().toString();

                    //String data = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
                    String data = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());

                    Info info = new Info(id, temperaturaD, pressioneD, glicemiaD, pesoD, notaS, data);

                    MainActivity.infoViewModel.insert(info);
                    Toast.makeText(getApplicationContext(),"Inserito",Toast.LENGTH_SHORT ).show();
                    Log.i(TAG, "inserito");

                } else{
                    Toast.makeText(getApplicationContext(),"Inserisci più di un campo",Toast.LENGTH_LONG ).show();
                    Log.i(TAG, "non inserito");
                }

                //activity conclusa
                finish();
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
    public boolean isCorrect(){

        int count = 0;  //conto i campi vuoti, se sono >= di 4 allora do errore

        //check temperatura
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

        //check pressione
        if( TextUtils.isEmpty(pressioneET.getText()) ){
            count++;
            Log.i(TAG, "press vuota - aggiorno il count");
        } else{
            pressioneD =  Double.parseDouble(pressioneET.getText().toString());

            if(pressioneD >= 160 || pressioneD <= 60){
                Toast.makeText(getApplicationContext(),"Inserisci una pressione valida",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "pressione non corretta");
                return false;
            }
            Log.i(TAG, "press OK");
        }

        //chek glicemia
        if( TextUtils.isEmpty(glicemiaET.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "glicemia vuota - aggiorno il count");
        } else{
            glicemiaD =  Double.parseDouble(glicemiaET.getText().toString());

            if(glicemiaD >= 250 || glicemiaD <= 50){
                Toast.makeText(getApplicationContext(),"Inserisci un indice glicemico valido",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "glicemia non corretta");
                return false;
            }
            Log.i(TAG, "glicemia OK");
        }

        //check peso
        if( TextUtils.isEmpty(pesoET.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "peso vuota - aggiorno il count");
        } else{
            pesoD =  Double.parseDouble(pesoET.getText().toString());

            if( pesoD < 0 ){
                Toast.makeText(getApplicationContext(),"Inserisci un peso valida",Toast.LENGTH_LONG ).show();
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