package com.example.personalhealthmonitor.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;

public class Utils {

    //passo in input 3 interi e ritorno la data in formato stringa MM/dd/yyyy
    public static String getDataSettings(int anno, int mese, int giorno){
        // il mese parte da 0, quindi se chiedo Dicembre mi ritorna 11
        // il giorno, se è fra i primi 9, ha solo una cifra

        String day = String.valueOf(giorno);
        String month = "";

        //sistemo il giorno
        if( giorno >=1 && giorno <=9 ){
            day = "0" + day;        //se ho una cifra, aggiungo lo zero
        }

        //sistemo il mese
        mese = mese +1;
        month = String.valueOf(mese);
        if( mese >= 1 && mese <=9 ){
            month = "0" + month;     //se ho una cifra, aggiungo lo zero
        }

        String data = month + "/" + day + "/" + anno;

        return data;
    }


    //passo in input 3 interi e ritorno la data in formato stringa dd/MM/yyyy
    public static String getDataDisplayUI(int anno, int mese, int giorno){
        // il mese parte da 0, quindi se chiedo Dicembre mi ritorna 11
        // il giorno, se è fra i primi 9, ha solo una cifra

        String day = String.valueOf(giorno);
        String month = "";

        //sistemo il giorno
        if( giorno >=1 && giorno <=9 ){
            day = "0" + day;        //se ho una cifra, aggiungo lo zero
        }

        //sistemo il mese
        mese = mese +1;
        month = String.valueOf(mese);
        if( mese >= 1 && mese <=9 ){
            month = "0" + month;     //se ho una cifra, aggiungo lo zero
        }

        String data = day + "/" + month + "/" + anno;

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

        if (dataFine.compareTo(dataInizio) >= 0) {
            //data inizio > data fine => return 1
            bool = true;
        }
        else {
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


    //ritorna true: i campi nella edit info & new info sono corretti
    public static boolean isCorrect(String temp, String sis, String dia, String glic, String peso, Context context, Double[] parameters){

        int count = 0;  //conto i campi vuoti, se sono >= di 4 allora è errore

        double temperaturaD, pressioneSisD, pressioneDiaD, glicemiaD, pesoD;

        //check temperatura => 34-42 °C
        if( TextUtils.isEmpty(temp) ) {
            count++;    //campo vuoto, aggiorno il contatore
        } else{
            temperaturaD =  Double.parseDouble(temp);

            if( (temperaturaD > 0 && temperaturaD < 34) || temperaturaD > 42 ){
                Toast.makeText(context,"Inserisci una temperatura valida",Toast.LENGTH_LONG ).show();
                return false;
            }

            // se c'è un numero aggiorno il vettore (che altrimenti è inizializzato a 0.0)
            parameters[0] = temperaturaD;
        }

        //check pressione sistolica => 80-190 mmHg
        if( TextUtils.isEmpty(sis) ){
            count++;
        } else{
            pressioneSisD =  Double.parseDouble(sis);

            if( (pressioneSisD > 0 && pressioneSisD < 80) || pressioneSisD > 190 ){
                Toast.makeText(context,"Inserisci una pressione sistolica valida",Toast.LENGTH_LONG ).show();
                return false;
            }
            parameters[1] = pressioneSisD;
        }

        //check pressione diastolica => 50-120 mmHg
        if( TextUtils.isEmpty(dia) ){
            count++;
        } else{
            pressioneDiaD =  Double.parseDouble(dia);

            if( ( pressioneDiaD > 0 && pressioneDiaD < 50) || pressioneDiaD > 120 ){
                Toast.makeText(context,"Inserisci una pressione diastolica valida",Toast.LENGTH_LONG ).show();
                return false;
            }
            parameters[2] = pressioneDiaD;
        }

        //chek glicemia 50-250 mg/dl
        if( TextUtils.isEmpty(glic) ){
            count++;    //campo vuoto, aggiorno il contatore
        } else{
            glicemiaD =  Double.parseDouble(glic);

            if( ( glicemiaD > 0 && glicemiaD < 50) || glicemiaD > 250 ){
                Toast.makeText(context,"Inserisci un indice glicemico valido",Toast.LENGTH_LONG ).show();
                return false;
            }
            parameters[3] = glicemiaD;
        }

        //check peso => >= 0 kg
        if( TextUtils.isEmpty(peso) ){
            count++;    //campo vuoto, aggiorno il contatore
        } else{
            pesoD =  Double.parseDouble(peso);

            if( pesoD < 0 ){
                Toast.makeText(context,"Inserisci un peso valido",Toast.LENGTH_LONG ).show();
                return false;
            }
            parameters[4] = pesoD;
        }

        //CHECK FINALE
        if(count >= 4) {
            Toast.makeText(context,"Inserisci più di un campo",Toast.LENGTH_LONG ).show();

            // ho inserito solo un campo
            return false;
        }

        return true;
    }


}
