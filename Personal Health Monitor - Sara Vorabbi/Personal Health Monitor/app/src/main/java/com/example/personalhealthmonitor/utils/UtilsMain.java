package com.example.personalhealthmonitor.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.personalhealthmonitor.R;
import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.Settings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;
import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;
import static com.example.personalhealthmonitor.utils.UtilsValue.*;


public class UtilsMain {

    // Creazione delle Shared Preferences
    public static void setSharedPrefs(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        //Shared prefs del DB = false (quando apro l'app per la prima volta è TRUE)
        prefs.edit().putBoolean(FIRST_RUN_MAIN, false).apply();

        //Shared prefs - notifiche giornaliere disattivate = FALSE
        prefs.edit().putBoolean(NOTIFICATION_ON, false).apply();

        //Shared prefs - report giornaliero = TRUE
        prefs.edit().putBoolean(NEW_REPORT, true).apply();

        //Shared prefs - ora delle notifiche = "false" non ho ancora attivato le notifiche
        prefs.edit().putString(DAILY_TIME, DAILY_TIME_DEFAULT).apply();

        //Shared prefs per il monitoraggio
        prefs.edit().putBoolean(MONITORA_TEMP, false).apply();
        prefs.edit().putBoolean(MONITORA_PRESS_SIS, false).apply();
        prefs.edit().putBoolean(MONITORA_PRESS_DIA, false).apply();
        prefs.edit().putBoolean(MONITORA_GLIC, false).apply();
        prefs.edit().putBoolean(MONITORA_PESO, false).apply();

        prefs.edit().putBoolean(EXCEEDED_TEMP, false).apply();
        prefs.edit().putBoolean(EXCEEDED_PRESS_SIS, false).apply();
        prefs.edit().putBoolean(EXCEEDED_PRESS_DIA, false).apply();
        prefs.edit().putBoolean(EXCEEDED_GLIC, false).apply();
        prefs.edit().putBoolean(EXCEEDED_PESO, false).apply();

    }

    //creazione dei settings e report statici
    public static void addDBElements(){

        //creo i settings nel DataBase
        Settings tempSet = new Settings(1, DB_TEMPERATURA, 1, 0, 0, "01/01/2021", "01/01/2021");
        settingsViewModel.insertSettings(tempSet);
        Settings pressSisSet = new Settings(2, DB_PRESSIONE_SISTOLICA, 1, 0, 0, "01/01/2021", "01/01/2021");
        settingsViewModel.insertSettings(pressSisSet);
        Settings pressDiaSet = new Settings(3, DB_PRESSIONE_DIASTOLICA, 1, 0, 0, "01/01/2021", "01/01/2021");
        settingsViewModel.insertSettings(pressDiaSet);
        Settings glicSet = new Settings(4, DB_GLICEMIA, 1, 0, 0, "01/01/2021", "01/01/2021");
        settingsViewModel.insertSettings(glicSet);
        Settings pesoSet = new Settings(5, DB_PESO, 1, 0, 0, "01/01/2021", "01/01/2021");
        settingsViewModel.insertSettings(pesoSet);

        //creo dei report statici
        Info info1 = new Info(UUID.randomUUID().toString(), 36, 0.0, 0.0, 90, 70, "Primo Report!", "01/02/2021");
        infoViewModel.insert(info1);
        Info info2 = new Info(UUID.randomUUID().toString(), 35.7, 0.0, 84, 0.0, 70.2, "Secondo Report!", "01/04/2021");
        infoViewModel.insert(info2);
        Info info3 = new Info(UUID.randomUUID().toString(), 0.0, 124, 86, 96, 0.0, "Terzo Report!", "01/06/2021");
        infoViewModel.insert(info3);
        Info info4 = new Info(UUID.randomUUID().toString(), 37.2, 0.0, 0.0, 98, 69.8, "Quarto Report!", "01/07/2021");
        infoViewModel.insert(info4);
        Info info5 = new Info(UUID.randomUUID().toString(), 0.0, 115, 82, 0.0, 0.0, "Quinto Report!", "01/08/2021");
        infoViewModel.insert(info5);
        Info info6 = new Info(UUID.randomUUID().toString(), 0.0, 116, 81, 102, 0.0, "Sesto Report!", "01/10/2021");
        infoViewModel.insert(info6);
        Info info7 = new Info(UUID.randomUUID().toString(), 36.1, 0.0, 80, 0.0, 70.5, "Settimo Report!", "01/10/2021");
        infoViewModel.insert(info7);
        Info info8 = new Info(UUID.randomUUID().toString(), 35.8, 0.0, 85, 0.0, 70.4, "Ottavo Report!", "01/11/2021");
        infoViewModel.insert(info8);
        Info info9 = new Info(UUID.randomUUID().toString(), 0.0, 119, 78, 85, 0.0, "Nono Report!", "01/12/2021");
        infoViewModel.insert(info9);
        Info info10 = new Info(UUID.randomUUID().toString(), 36.3, 0.0, 80, 97, 70.2, "Decimo Report!", "01/14/2021");
        infoViewModel.insert(info10);
        Info info11 = new Info(UUID.randomUUID().toString(), 0.0, 0.0, 0.0, 91, 70.9, "Glicemia sotto controllo", "01/15/2021");
        infoViewModel.insert(info11);

    }



    //==================//
    // CLASSI ASINCRONE //
    //==================//

    //funzione che mostra il numero di report inseriti nella giornata
    public static void showNumberOfReportAddedToday(Activity activity){
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String today = sdf.format(cal.getTime());

        NrReportAsyncTask task = new NrReportAsyncTask(activity);
        task.execute(today);
    }

    private static class NrReportAsyncTask extends AsyncTask<String, Void, Void> {

        Activity myActivity;
        int nr = 0;

        //costruttore
        public NrReportAsyncTask(Activity activity) {
            super();
            this.myActivity = activity;
        }

        @Override
        protected Void doInBackground(String... strings) {
            nr = infoViewModel.getReportsCount(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            TextView nrRepo = myActivity.findViewById(R.id.repo_text_number);
            nrRepo.setText("Ciao! Oggi hai aggiunto " + nr + " report");

        }
    }


    //funzione che chiama l'async task per mostrare i valori medi nella main activity
    public static void showAverageParameters(Activity activity){
        AverageParamAsyncTask task = new AverageParamAsyncTask(activity);
        task.execute();
    }


    //Classe asincrona che aggiorna la UI
    private static class AverageParamAsyncTask extends AsyncTask<Void, Void, Void> {

        Activity myActivity;
        double temp, sis, dia, glic, peso;

        //costruttore
        public AverageParamAsyncTask(Activity activity) {
            super();
            this.myActivity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //creo data nel formato MM/dd/yyyy
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String today = sdf.format(calendar.getTime());

            temp = infoViewModel.getAverageTemperatura(today, today);
            sis = infoViewModel.getAveragePressioneSistolica(today, today);
            dia = infoViewModel.getAveragePressioneDiastolica(today, today);
            glic = infoViewModel.getAverageGlicemia(today, today);
            peso = infoViewModel.getAveragePeso(today, today);

            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            TextView tempView = myActivity.findViewById(R.id.main_temperatura_media);
            TextView sisView = myActivity.findViewById(R.id.main_pressione_sistolica_media);
            TextView diaView = myActivity.findViewById(R.id.main_pressione_diastolica_media);
            TextView glicView = myActivity.findViewById(R.id.main_glicemia_media);
            TextView pesoView = myActivity.findViewById(R.id.main_peso_media);

            //approssima il valore dopo una cifra decimale
            tempView.setText(String.format("%.1f", temp));
            sisView.setText(String.format("%.1f", sis));
            diaView.setText(String.format("%.1f", dia));
            glicView.setText(String.format("%.1f", glic));
            pesoView.setText(String.format("%.1f", peso));
        }
    }


}
