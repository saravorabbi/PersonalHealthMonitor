package com.example.personalhealthmonitor.notifications;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.personalhealthmonitor.R;
import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.SettingsViewModel;


import static com.example.personalhealthmonitor.notifications.App.CHANNEL_1_ID;
import static com.example.personalhealthmonitor.utils.UtilsValue.*;

public class ForegroundService extends Service {

    public static final String TAG = "ForegroundService = ";

    public static SettingsViewModel settingsViewModel;
    public static InfoViewModel infoViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        infoViewModel = new InfoViewModel(getApplication());
        settingsViewModel = new SettingsViewModel(getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //notifica foreground
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setContentTitle("Controllo dei parametri...")
                .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)
                .setColor(Color.CYAN)
                .build();

        startForeground(1, notification);

        //lavoro eseguito in un nuovo thread
        doBackgroundWork();

        return START_STICKY;
    }


    private void doBackgroundWork(){

        new Thread(() -> {

            Log.i(TAG, "ForegroundService - Background Thread");

            //TEMPERATURA
            Log.d(TAG, TEMPERATURA);

            int valTemp = settingsViewModel.getValueFromParameter(DB_TEMPERATURA);

            if (valTemp >= 3 ) {
                //get begin date - end date
                String beginTemp = settingsViewModel.getBeginDate(DB_TEMPERATURA);
                String endTemp = settingsViewModel.getEndDate(DB_TEMPERATURA);

                //get AVG value
                double avgTemp = infoViewModel.getAverageTemperatura(beginTemp, endTemp);

                //get upper - lower bound
                double upperBoundTemp = settingsViewModel.getUpperBound(DB_TEMPERATURA);
                double lowerBoundTemp = settingsViewModel.getLowerBound(DB_TEMPERATURA);

                //check AVG value
                if ((avgTemp < lowerBoundTemp || avgTemp > upperBoundTemp) && (avgTemp != 0)) {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_TEMP, true).apply();
                } else {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_TEMP, false).apply();
                }
                boolean bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_TEMP, true);
                Log.d(TAG, "begindate: " + beginTemp + " endDate " + endTemp + " average " + avgTemp);
                Log.d(TAG, "Upper: " + upperBoundTemp + " lower: " + lowerBoundTemp + " soglia: " + bool);

            } else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_TEMP, false).apply();


            //PRESSIONE SISTOLICA
            Log.d(TAG, PRESSIONE_SISTOLICA);

            int valPresSis = settingsViewModel.getValueFromParameter(DB_PRESSIONE_SISTOLICA);

            if(valPresSis >= 3) {
                //get begin date - end date
                String beginPresSis = settingsViewModel.getBeginDate(DB_PRESSIONE_SISTOLICA);
                String endPresSis = settingsViewModel.getEndDate(DB_PRESSIONE_SISTOLICA);

                //get AVG value
                double avgPresSis = infoViewModel.getAveragePressioneSistolica(beginPresSis, endPresSis);

                //get upper - lower bound
                double upperBoundPresSis = settingsViewModel.getUpperBound(DB_PRESSIONE_SISTOLICA);
                double lowerBoundPresSis = settingsViewModel.getLowerBound(DB_PRESSIONE_SISTOLICA);

                //check AVG value
                if ((avgPresSis < lowerBoundPresSis || avgPresSis > upperBoundPresSis) && (avgPresSis != 0)) {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_SIS, true).apply();
                } else {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_SIS, false).apply();
                }
                boolean bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_PRESS_SIS, true);
                Log.d(TAG, "begindate: " + beginPresSis + " endDate " + endPresSis + " average " + avgPresSis);
                Log.d(TAG, "Upper: " + upperBoundPresSis + " lower: " + lowerBoundPresSis + " soglia: " + bool);

            } else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_SIS, false).apply();


            //PRESSIONE DIASTOLICA
            Log.d(TAG, PRESSIONE_DIASTOLICA);

            int valPresDia = settingsViewModel.getValueFromParameter(DB_PRESSIONE_DIASTOLICA);

            if(valPresDia >= 3) {
                //get begin date - end date
                String beginPresDia = settingsViewModel.getBeginDate(DB_PRESSIONE_DIASTOLICA);
                String endPresDia = settingsViewModel.getEndDate(DB_PRESSIONE_DIASTOLICA);

                //get AVG value
                double avgPresDia = infoViewModel.getAveragePressioneDiastolica(beginPresDia, endPresDia);

                //get upper - lower bound
                double upperBoundPresDia = settingsViewModel.getUpperBound(DB_PRESSIONE_DIASTOLICA);
                double lowerBoundPresDia = settingsViewModel.getLowerBound(DB_PRESSIONE_DIASTOLICA);

                //check AVG value
                if ((avgPresDia < lowerBoundPresDia || avgPresDia > upperBoundPresDia) && (avgPresDia != 0)) {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_DIA, true).apply();
                } else {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_DIA, false).apply();
                }
                boolean bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_PRESS_DIA, true);
                Log.d(TAG, "begindate: " + beginPresDia + " endDate " + endPresDia + " average " + avgPresDia);
                Log.d(TAG, "Upper: " + upperBoundPresDia + " lower: " + lowerBoundPresDia + " soglia: " + bool);

            } else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_DIA, false).apply();


            //GLICEMIA
            Log.d(TAG, GLICEMIA);

            int valGlicemia = settingsViewModel.getValueFromParameter(DB_GLICEMIA);

            if(valGlicemia >= 3) {
                //get begin date - end date
                String beginGlic = settingsViewModel.getBeginDate(DB_GLICEMIA);
                String endGlic = settingsViewModel.getEndDate(DB_GLICEMIA);

                //get AVG value
                double avgGlic = infoViewModel.getAverageGlicemia(beginGlic, endGlic);

                //get upper - lower bound
                double upperBoundGlic = settingsViewModel.getUpperBound(DB_GLICEMIA);
                double lowerBoundGlic = settingsViewModel.getLowerBound(DB_GLICEMIA);

                //check AVG value
                if ((avgGlic < lowerBoundGlic || avgGlic > upperBoundGlic) && (avgGlic != 0)) {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_GLIC, true).apply();
                } else {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_GLIC, false).apply();
                }
                boolean bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_GLIC, true);
                Log.d(TAG, "begindate: " + beginGlic + " endDate " + endGlic + " average " + avgGlic);
                Log.d(TAG, "Upper: " + upperBoundGlic + " lower: " + lowerBoundGlic + " soglia: " + bool);

            } else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_GLIC, false).apply();


            //PESO
            Log.d(TAG, PESO);

            int valPeso = settingsViewModel.getValueFromParameter(DB_PESO);

            if(valPeso >= 3) {
                //get begin date - end date
                String beginPeso = settingsViewModel.getBeginDate(DB_PESO);
                String endPeso = settingsViewModel.getEndDate(DB_PESO);

                //get AVG value
                double avgPeso = infoViewModel.getAveragePeso(beginPeso, endPeso);

                Log.d(TAG, "AVERAGE PESO " + avgPeso);

                //get upper - lower bound
                double upperBoundPeso = settingsViewModel.getUpperBound(DB_PESO);
                double lowerBoundPeso = settingsViewModel.getLowerBound(DB_PESO);

                //check AVG value
                if ((avgPeso < lowerBoundPeso || avgPeso > upperBoundPeso) && (avgPeso != 0)) {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PESO, true).apply();
                } else {
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PESO, false).apply();
                }
                boolean bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_PESO, true);
                Log.d(TAG, "begindate: " + beginPeso + " endDate " + endPeso + " average " + avgPeso);
                Log.d(TAG, "Upper: " + upperBoundPeso + " lower: " + lowerBoundPeso + " soglia: " + bool);

            } else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PESO, false).apply();


            //Setto la notifica
            AlarmHandler alarmHandler = new AlarmHandler(getApplicationContext());
            alarmHandler.setMonitorAlarm();


            //tolgo la notifica del foreground service quando finisco il lavoro nel thread
            stopForeground(true);

            //termino il foreground service
            stopSelf();

        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ON DESTROY - FOREGROUND SERVICE");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "ON BIND FOREGROUND SERVICE");
        return null;
    }
}
