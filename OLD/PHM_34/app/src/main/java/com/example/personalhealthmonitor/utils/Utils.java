package com.example.personalhealthmonitor.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalhealthmonitor.database.Settings;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;

public class Utils {

    private static final String TAG = "Utils: ";



    //passo in input 3 interi e ritorno la data in formato stringa
    public static String getDataSettings(int anno, int mese, int giorno){
        // il mese parte da 0, quindi se chiedo Dicembre mi ritorna 11
        // il giorno, se è fra i primi 9, ha solo una cifra

        String day = String.valueOf(giorno);

        //sistemo il giorno
        if( giorno >=1 && giorno <=9 ){
            day = "0" + day;    //se ho una cifra, aggiungo lo zero
        }

        //sistemo il mese
        String data = (mese+1) + "/" + day + "/" + anno;
        Log.i(TAG, "data : " + data);

        return data;
    }



    //prende in input due date formato "MM/dd/yyyy" e stabilisce se begin precede end
    //ritorna true se begin precede end
    //ritorna false se end viene prima di begin
    public static boolean checkDateSettings(String begin, String end){
        boolean bool = false;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        Date dataInizio = new Date(12/02/2020);
        Date dataFine = new Date(12/02/2020);

        try {
            dataInizio = simpleDateFormat.parse(begin);
            Log.i(TAG, "data= " + dataInizio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try{
            dataFine = simpleDateFormat.parse(end);
        } catch (ParseException e){
            e.printStackTrace();
        }

        //check effettivo
        int val = dataFine.compareTo(dataInizio);
        Log.i(TAG, "Valore = " + val);

        if (dataFine.compareTo(dataInizio) >= 0) {
            //data inizio > data fine => return 1
            Log.d(TAG,"Data Inizio >= Data Fine => return 1 (o 0) - valore = " + val);
            bool = true;
        }
        else {
            //data fine > data inizio => return -1 (CASO ERRORE)
            Log.d(TAG,"data fine > data inizio (CASO ERRORE) => return 0 - valore =" + val);
            bool = false;
        }

        //se  0 => begin == end
        //se  1 => begin PRIMA end
        //se -1 => begin DOPO end (errore)

        return bool;
    }


    //input: nome dei parametri dentro al DB
    //output: stringa corrispondente
    public static String getParameterName(String db_parameter){

        String param = "";

        switch (db_parameter) {
            case DB_TEMPERATURA:
                param = TEMPERATURA;
                break;
            case DB_PRESSIONE_SISTOLICA:
                param = PRESSIONE_SISTOLICA;
                break;
            case DB_PRESSIONE_DIASTOLICA:
                param = PRESSIONE_DIASTOLICA;
                break;
            case DB_GLICEMIA:
                param = GLICEMIA;
                break;
            case DB_PESO:
                param = PESO;
                break;
        }

        return param;
    }



    //true: i campi vanno bene
    //false: un campo non va bene
    public static boolean isCorrect(String temp, String sis, String dia, String glic, String peso, Context context, Double[] parameters){

        int count = 0;  //conto i campi vuoti, se sono >= di 4 allora do errore

        double temperaturaD, pressioneSisD, pressioneDiaD, glicemiaD, pesoD;

        //check temperatura => 34-42 °C
        if( TextUtils.isEmpty(temp) ) {
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "temp vuota - aggiorno il count");
        } else{
            temperaturaD =  Double.parseDouble(temp);

            if(temperaturaD > 42 || temperaturaD < 34){
                Toast.makeText(context,"Inserisci una temperatura valida",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "temp non corretta");
                return false;
            }

            // se c'è un numero aggiorno il vettore (che altrimenti è inizializzato a 0.0)
            parameters[0] = temperaturaD;

            Log.i(TAG, "temp OK");
        }

        //check pressione sistolica => 80-190 mmHg
        if( TextUtils.isEmpty(sis) ){
            count++;
            Log.i(TAG, "press sistolica vuota - aggiorno il count");
        } else{
            pressioneSisD =  Double.parseDouble(sis);

            if(pressioneSisD > 190 || pressioneSisD < 80){
                Toast.makeText(context,"Inserisci una pressione sistolica valida",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "pressione sistolica non corretta");
                return false;
            }
            parameters[1] = pressioneSisD;
            Log.i(TAG, "press OK");
        }

        //check pressione diastolica => 50-120 mmHg
        if( TextUtils.isEmpty(dia) ){
            count++;
            Log.i(TAG, "press diastolica vuota - aggiorno il count");
        } else{
            pressioneDiaD =  Double.parseDouble(dia);

            if(pressioneDiaD > 120 || pressioneDiaD < 50){
                Toast.makeText(context,"Inserisci una pressione diastolica valida",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "pressione diastolica non corretta");
                return false;
            }
            parameters[2] = pressioneDiaD;
            Log.i(TAG, "press OK");
        }

        //chek glicemia 50-250 mg/dl
        if( TextUtils.isEmpty(glic) ){
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "glicemia vuota - aggiorno il count");
        } else{
            glicemiaD =  Double.parseDouble(glic);

            if(glicemiaD > 250 || glicemiaD < 50){
                Toast.makeText(context,"Inserisci un indice glicemico valido",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "glicemia non corretta");
                return false;
            }
            parameters[3] = glicemiaD;
            Log.i(TAG, "glicemia OK");
        }

        //check peso => >0 kg
        if( TextUtils.isEmpty(peso) ){
            count++;    //campo vuoto, aggiorno il contatore
            Log.i(TAG, "peso vuota - aggiorno il count");
        } else{
            pesoD =  Double.parseDouble(peso);

            if( pesoD < 0 ){
                Toast.makeText(context,"Inserisci un peso valido",Toast.LENGTH_LONG ).show();
                Log.i(TAG, "peso non corretta");
                return false;
            }
            parameters[4] = pesoD;
            Log.i(TAG, "peso OK");
        }

        //CHECK FINALE
        if(count >= 4) {
            Toast.makeText(context,"Inserisci più di un campo",Toast.LENGTH_LONG ).show();

            Log.i(TAG, "ho inserito solo un campo: " + count);
            return false; // ho inserito solo un campo
        }

        Log.i(TAG, "return true " + count );
        return true;
    }



}
