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

        saveRepo = findViewById(R.id.saveNewReport);
        saveRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();
                //almeno 3 campi devono essere riempiti, altrimenti non salva il report -> errore

                //nessun campo compilato
                if( TextUtils.isEmpty(peso.getText()) ){

                    setResult(RESULT_CANCELED, resultIntent);   // 0

                } else if( checkCampi() ) {
                    //meno di 2 campi compilati
                    Toast.makeText(getApplicationContext(),"inserisci 2 campi min",Toast.LENGTH_LONG );

                } else{
                        //2 su 5 compilati
                        String note = peso.getText().toString();
                        resultIntent.putExtra(NOTE_ADDED, note);
                        setResult(RESULT_OK, resultIntent); // -1
                }

                //activity conclusa
                finish();
            }

        });
    }

    //funzione che controlla che 2 campi siano inseriti
    public boolean checkCampi(){
        int i = 0;
        boolean check = false;

        //code check

        //un solo campo compilato
        if(i > 1) check = true;

        return check;
    }
}