package com.example.liste_esperimenti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN ACTIVITY";

    private static final String TEMPERATURA = "Temperatura";
    private static final String PRESSIONE_SISTOLICA = "Pressione Sistolica";
    private static final String PRESSIONE_DIASTOLICA = "Pressione Diastolica";
    private static final String GLICEMIA = "Glicemia";
    private static final String PESO = "Peso";

    private String texty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> stringList = new ArrayList<String>();


        stringList.add(TEMPERATURA);
        stringList.add(PRESSIONE_SISTOLICA);
        stringList.add(PRESSIONE_DIASTOLICA);
        stringList.add(GLICEMIA);
        stringList.add(PESO);

        texty="";

        for (int i = 0; i < stringList.size(); i++){
            texty = texty + stringList.get(i);
            if( i != (stringList.size() - 1) ){
                texty = texty + " - ";
            }
        }

        Log.i(TAG, "STRINGA listaaa = " + texty);



    }
}