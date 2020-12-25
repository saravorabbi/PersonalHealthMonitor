package com.example.personalhealthmonitor.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.SettingsViewModel;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;

public class MonitorJobService extends JobService {


    private static final String TAG = "MonitorJobService: ";
    private boolean jobCancelled = false;

    public static InfoViewModel infoViewModelMonitor;
    public static SettingsViewModel settingsViewModelMonitor;


    //Costruttore
    public MonitorJobService() {
        super();

        infoViewModelMonitor =  new InfoViewModel(this.getApplication());
        settingsViewModelMonitor = new SettingsViewModel(this.getApplication());
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "MonitorJobService job iniziato");

        doBackgroundWork(jobParameters);
        //return false;
        return true;
        //setto a true se ho del lavoro in background che è lungo -> avverte il sistema di tenere il device awake così da finire di eseguire il lavoro
        // questo implica che dobbiamo anche dire al sistema quando abbiamo finito col lavoro in backgound
    }

    private void doBackgroundWork(JobParameters jobParameters) {

        new Thread(() -> {

            Log.d(TAG, "Fra 15 secondi parte il nuovo thread");

            //spetto che il database sia aggiornato, metto il thread in attesa per 20 secondi
            try {
                Thread.sleep(15*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //LAVORO NEL BACKGOUND THREAD
            Log.d(TAG, "MonitorJobService SONO NEL NUOVO thread");

            if(jobCancelled){
                return;
                //lasciamo il metodo e smettiamo di eseguire il background work
            }


            //TEMPERATURA
            Log.d(TAG, TEMPERATURA);

            int valTemp = settingsViewModelMonitor.getValueFromParameter(DB_TEMPERATURA);

            if (valTemp >= 3 ) {
                //get begin date - end date
                String beginTemp = settingsViewModelMonitor.getBeginDate(DB_TEMPERATURA);
                String endTemp = settingsViewModelMonitor.getEndDate(DB_TEMPERATURA);

                //get AVG value
                double avgTemp = infoViewModelMonitor.getAverageTemperatura(beginTemp, endTemp);

                //get upper - lower bound
                double upperBoundTemp = settingsViewModelMonitor.getUpperBound(DB_TEMPERATURA);
                double lowerBoundTemp = settingsViewModelMonitor.getLowerBound(DB_TEMPERATURA);

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

            int valPresSis = settingsViewModelMonitor.getValueFromParameter(DB_PRESSIONE_SISTOLICA);

            if(valPresSis >= 3) {
                //get begin date - end date
                String beginPresSis = settingsViewModelMonitor.getBeginDate(DB_PRESSIONE_SISTOLICA);
                String endPresSis = settingsViewModelMonitor.getEndDate(DB_PRESSIONE_SISTOLICA);

                //get AVG value
                double avgPresSis = infoViewModelMonitor.getAveragePressioneSistolica(beginPresSis, endPresSis);

                //get upper - lower bound
                double upperBoundPresSis = settingsViewModelMonitor.getUpperBound(DB_PRESSIONE_SISTOLICA);
                double lowerBoundPresSis = settingsViewModelMonitor.getLowerBound(DB_PRESSIONE_SISTOLICA);

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

            int valPresDia = settingsViewModelMonitor.getValueFromParameter(DB_PRESSIONE_DIASTOLICA);

            if(valPresDia >= 3) {
                //get begin date - end date
                String beginPresDia = settingsViewModelMonitor.getBeginDate(DB_PRESSIONE_DIASTOLICA);
                String endPresDia = settingsViewModelMonitor.getEndDate(DB_PRESSIONE_DIASTOLICA);

                //get AVG value
                double avgPresDia = infoViewModelMonitor.getAveragePressioneDiastolica(beginPresDia, endPresDia);

                //get upper - lower bound
                double upperBoundPresDia = settingsViewModelMonitor.getUpperBound(DB_PRESSIONE_DIASTOLICA);
                double lowerBoundPresDia = settingsViewModelMonitor.getLowerBound(DB_PRESSIONE_DIASTOLICA);

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

            int valGlicemia = settingsViewModelMonitor.getValueFromParameter(DB_GLICEMIA);

            if(valGlicemia >= 3) {
                //get begin date - end date
                String beginGlic = settingsViewModelMonitor.getBeginDate(DB_GLICEMIA);
                String endGlic = settingsViewModelMonitor.getEndDate(DB_GLICEMIA);

                //get AVG value
                double avgGlic = infoViewModelMonitor.getAverageGlicemia(beginGlic, endGlic);

                //get upper - lower bound
                double upperBoundGlic = settingsViewModelMonitor.getUpperBound(DB_GLICEMIA);
                double lowerBoundGlic = settingsViewModelMonitor.getLowerBound(DB_GLICEMIA);

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

            int valPeso = settingsViewModelMonitor.getValueFromParameter(DB_PESO);

            if(valPeso >= 3) {
                //get begin date - end date
                String beginPeso = settingsViewModelMonitor.getBeginDate(DB_PESO);
                String endPeso = settingsViewModelMonitor.getEndDate(DB_PESO);

                //get AVG value
                double avgPeso = infoViewModelMonitor.getAveragePeso(beginPeso, endPeso);

                //get upper - lower bound
                double upperBoundPeso = settingsViewModelMonitor.getUpperBound(DB_PESO);
                double lowerBoundPeso = settingsViewModelMonitor.getLowerBound(DB_PESO);

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



            //Inizio Notifiche
            Log.d(TAG, "NOTIFICHE!!!");

            //Intent che mi porta al Broadcast Receiver
            Intent broadcastIntent = new Intent(getApplicationContext(), AlertReceiver.class);
            broadcastIntent.putExtra("notification_type", SETTINGS_NOTIFICATION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 7, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Calendar c = Calendar.getInstance();

//            c.add(Calendar.MINUTE, 1);

            //Setto l'orario per le 9 di mattina
            c.set(Calendar.HOUR_OF_DAY, 9);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            String oraRemind = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
            Log.d(TAG, "La notifica arriverà alle ore  = " + oraRemind);

            //Se l'orario è già passato allora setto la notifica per domani
            if (c.before(Calendar.getInstance())) {
                Log.i(TAG, "sono le 9:00 di domani");
                c.add(Calendar.DATE, 1);
            }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            //fine notifiche


            Log.d(TAG, "MonitorJobService job finito");
            jobFinished(jobParameters, false);
        }).start();

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Log.d(TAG, "MonitorJobService: cancellato prima del completamento");
        jobCancelled = true;
        return true;
        //true: voglio rischedulare il job
    }
}
