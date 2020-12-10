package com.example.personalhealthmonitor;

import android.util.Log;
import android.view.View;

import com.example.personalhealthmonitor.database.Settings;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final String TAG = "Utils: ";



    // il mese parte da 0, quindi se chiedo Dicembre mi ritorna 11
    // il giorno, se è fra i primi 9, ha solo una cifra
    public static String getDataSettings(int anno, int mese, int giorno){

        String day = String.valueOf(giorno);

        if( giorno >=1 && giorno <=9 ){
            day = "0" + day;    //se ho una cifra, aggiungo lo zero
        }

        String data = (mese+1) + "/" + day + "/" + anno;
        Log.i(TAG, "data : " + data);

        return data;
    }



    //prende in input due date formato "MM/dd/yyyy" e stabilisce se begin precede end
    //ritorna true se begin precede end
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

        int val = dataFine.compareTo(dataInizio);
        Log.i(TAG, "Valore = " + val);

        if (dataFine.compareTo(dataInizio) >= 0) {
            //data inizio > data fine => return 1
            Log.d(TAG,"data inizio >= data fine => return 1 (o 0) - valore =" + val);
            bool = true;
        }
        else {
            //data fine > data inizio => return -1 (CASO ERRORE)
            Log.d(TAG,"data fine > data inizio (CASO ERRORE) => return 0 - valore =" + val);
            bool = false;
        }

        return bool;
    }

}
