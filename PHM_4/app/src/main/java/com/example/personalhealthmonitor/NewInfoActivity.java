package com.example.personalhealthmonitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

//Activity per creare un nuovo report

public class NewInfoActivity extends AppCompatActivity {

    private EditText temperatura;
    private EditText pressione;
    private EditText glicemia;
    private EditText peso;
    private EditText acqua;

    private Button saveRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        temperatura = findViewById(R.id.new_temperatura);
        pressione = findViewById(R.id.new_pressione);
        glicemia = findViewById(R.id.new_glicemia);
        peso = findViewById(R.id.new_peso);
        acqua = findViewById(R.id.new_acqua);


        //Salvataggio del report nel DB
        saveRepo = findViewById(R.id.saveNewReport);
        saveRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();

                //almeno 3 campi devono essere riempiti, altrimenti non salva il report -> errore
                if( isCorrect() ){

                    //Report report = new Report(0, /*java.text.DateFormat.getDateTimeInstance().format(new Date())*/ java.text.DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date()),battiti,temperatura,pressione,glicemia, nota);
                    //MainActivity.reportViewModel.setReport(report);

                    String note = etNewNote.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED, note);
                    setResult(RESULT_OK, resultIntent); // -1

                } else {
                    setResult(RESULT_CANCELED, resultIntent);   // RESULT_CANCELED = 0
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
        double valDouble;
        int valInt;

        //check temperatura - double
        if( TextUtils.isEmpty(temperatura.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
        } else{
            valDouble =  Double.parseDouble(temperatura.getText().toString());

            if(valDouble > 42 || valDouble < 34){
                Toast.makeText(getApplicationContext(),"Inserisci una temperatura valida",Toast.LENGTH_LONG );
                return false;
            }
        }

        //check pressione - int
        if( TextUtils.isEmpty(pressione.getText()) ){
            count++;
        } else{
            valInt =  Integer.parseInt(pressione.getText().toString());

            if(valInt >= 160 || valInt <= 60){
                Toast.makeText(getApplicationContext(),"Inserisci una pressione valida",Toast.LENGTH_LONG );
                return false;
            }
        }

        //chek glicemia - int
        if( TextUtils.isEmpty(glicemia.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
        } else{
            valInt =  Integer.parseInt(glicemia.getText().toString());

            if(valInt >= 250 || valInt <= 50){
                Toast.makeText(getApplicationContext(),"Inserisci un indice glicemico valido",Toast.LENGTH_LONG );
                return false;
            }
        }

        //check peso - double
        if( TextUtils.isEmpty(peso.getText()) ){
            count++;    //campo vuoto, aggiorno il contatore
        } else{
            valDouble =  Double.parseDouble(peso.getText().toString());

            if( valDouble > 0 ){
                Toast.makeText(getApplicationContext(),"Inserisci un peso valida",Toast.LENGTH_LONG );
                return false;
            }
        }

        if(count >= 4) return false; // ho inserito solo un campo

        return true;
    }


}