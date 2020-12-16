package com.example.personalhealthmonitor.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModelProviders;

import com.example.personalhealthmonitor.database.InfoDao;
import com.example.personalhealthmonitor.database.InfoRoomDatabase;
import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.SettingsViewModel;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.MainActivity.EXCEEDED_GLIC;
import static com.example.personalhealthmonitor.MainActivity.EXCEEDED_PESO;
import static com.example.personalhealthmonitor.MainActivity.EXCEEDED_PRESS_DIA;
import static com.example.personalhealthmonitor.MainActivity.EXCEEDED_PRESS_SIS;
import static com.example.personalhealthmonitor.MainActivity.EXCEEDED_TEMP;
import static com.example.personalhealthmonitor.MainActivity.NOTIFICATION_ON;
import static com.example.personalhealthmonitor.MainActivity.SHARED_PREF;
import static com.example.personalhealthmonitor.SettingsActivity.SETTINGS_NOTIFICATION;

public class MonitorJobService extends JobService {


    private static final String TAG = "MonitorJobService: ";
    private boolean jobCancelled = false;

    public static InfoViewModel infoViewModelMonitor;
    public static SettingsViewModel settingsViewModelMonitor;


    private static final String TEMPERATURA = "Temperatura";
    private static final String PRESSIONE_SISTOLICA = "Pressione Sistolica";
    private static final String PRESSIONE_DIASTOLICA = "Pressione Diastolica";
    private static final String GLICEMIA = "Glicemia";
    private static final String PESO = "Peso";

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                //LAVORO NEL BACKGOUND THREAD
                Log.d(TAG, "MonitorJobService SONO NEL NUOVO thread");

                if(jobCancelled){
                    return;
                    //lasciamo il metodo e smettiamo di eseguire il background work
                }



                //TEMPERATURA

                //get begin date - end date
                String beginTemp = settingsViewModelMonitor.getBeginDate(TEMPERATURA);
                String endTemp = settingsViewModelMonitor.getEndDate(TEMPERATURA);

                //get AVG value
                double avgTemp = infoViewModelMonitor.getAverageValueBetweenDates(TEMPERATURA, beginTemp, endTemp);

                //get upper - lower bound
                double upperBoundTemp = settingsViewModelMonitor.getUpperBound(TEMPERATURA);
                double lowerBoundTemp = settingsViewModelMonitor.getLowerBound(TEMPERATURA);

                //check AVG value
                if( (avgTemp < lowerBoundTemp || avgTemp > upperBoundTemp) && (avgTemp != 0) ){
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_TEMP, true).apply();
                }else{
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_TEMP, false).apply();
                }
                boolean bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_TEMP, true);
                Log.d(TAG, "begindate: " + beginTemp + " endDate " + endTemp + " average " + avgTemp);
                Log.d(TAG, "Upper: " + upperBoundTemp + " lower: " + lowerBoundTemp + " soglia: " + bool);


                //PRESSIONE SISTOLICA

                //get begin date - end date
                String beginPresSis = settingsViewModelMonitor.getBeginDate(PRESSIONE_SISTOLICA);
                String endPresSis = settingsViewModelMonitor.getEndDate(PRESSIONE_SISTOLICA);

                //get AVG value
                double avgPresSis = infoViewModelMonitor.getAverageValueBetweenDates(PRESSIONE_SISTOLICA, beginPresSis, endPresSis);

                //get upper - lower bound
                double upperBoundPresSis = settingsViewModelMonitor.getUpperBound(PRESSIONE_SISTOLICA);
                double lowerBoundPresSis = settingsViewModelMonitor.getLowerBound(PRESSIONE_SISTOLICA);

                //check AVG value
                if( (avgPresSis < lowerBoundPresSis || avgTemp > upperBoundPresSis) && (avgPresSis != 0) ){
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_SIS, true).apply();
                }else{
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_SIS, false).apply();
                }
                bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_PRESS_SIS, true);
                Log.d(TAG, "begindate: " + beginPresSis + " endDate " + endPresSis + " average " + avgPresSis);
                Log.d(TAG, "Upper: " + upperBoundPresSis+ " lower: " + lowerBoundPresSis + " soglia: " + bool);


                //PRESSIONE DIASTOLICA

                //get begin date - end date
                String beginPresDia = settingsViewModelMonitor.getBeginDate(PRESSIONE_DIASTOLICA);
                String endPresDia = settingsViewModelMonitor.getEndDate(PRESSIONE_DIASTOLICA);

                //get AVG value
                double avgPresDia = infoViewModelMonitor.getAverageValueBetweenDates(PRESSIONE_DIASTOLICA, beginPresDia, endPresDia);

                //get upper - lower bound
                double upperBoundPresDia = settingsViewModelMonitor.getUpperBound(PRESSIONE_DIASTOLICA);
                double lowerBoundPresDia = settingsViewModelMonitor.getLowerBound(PRESSIONE_DIASTOLICA);

                //check AVG value
                if( (avgPresDia < lowerBoundPresDia || avgPresDia > upperBoundPresDia) && (avgTemp != 0) ){
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_DIA, true).apply();
                }else{
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PRESS_DIA, false).apply();
                }
                bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_PRESS_DIA, true);
                Log.d(TAG, "begindate: " + beginPresDia + " endDate " + endPresDia + " average " + avgPresDia);
                Log.d(TAG, "Upper: " + upperBoundPresDia+ " lower: " + lowerBoundPresDia + " soglia: " + bool);


                //GLICEMIA

                //get begin date - end date
                String beginGlic = settingsViewModelMonitor.getBeginDate(GLICEMIA);
                String endGlic = settingsViewModelMonitor.getEndDate(GLICEMIA);

                //get AVG value
                double avgGlic = infoViewModelMonitor.getAverageValueBetweenDates(GLICEMIA, beginGlic, endGlic);

                //get upper - lower bound
                double upperBoundGlic = settingsViewModelMonitor.getUpperBound(GLICEMIA);
                double lowerBoundGlic = settingsViewModelMonitor.getLowerBound(GLICEMIA);

                //check AVG value
                if( (avgGlic < lowerBoundGlic || avgGlic > upperBoundGlic) && (avgTemp != 0) ){
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_GLIC, true).apply();
                }else{
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_GLIC, false).apply();
                }
                bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_GLIC, true);
                Log.d(TAG, "begindate: " + beginGlic + " endDate " + endGlic + " average " + avgGlic);
                Log.d(TAG, "Upper: " + upperBoundGlic+ " lower: " + lowerBoundGlic + " soglia: " + bool);

                //PESO

                //get begin date - end date
                String beginPeso = settingsViewModelMonitor.getBeginDate(PESO);
                String endPeso = settingsViewModelMonitor.getEndDate(PESO);

                //get AVG value
                double avgPeso = infoViewModelMonitor.getAverageValueBetweenDates(PESO, beginPeso, endPeso);

                //get upper - lower bound
                double upperBoundPeso = settingsViewModelMonitor.getUpperBound(PESO);
                double lowerBoundPeso = settingsViewModelMonitor.getLowerBound(PESO);

                //check AVG value
                if( (avgPeso < lowerBoundPeso || avgPeso > upperBoundPeso) && (avgTemp != 0) ){
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PESO, true).apply();
                }else{
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(EXCEEDED_PESO, false).apply();
                }
                bool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(EXCEEDED_PESO, true);
                Log.d(TAG, "begindate: " + beginPeso + " endDate " + endPeso + " average " + avgPeso);
                Log.d(TAG, "Upper: " + upperBoundPeso+ " lower: " + lowerBoundPeso + " soglia: " + bool);



                //Inizio Notifiche
                Log.d(TAG, "NOTIFICHE!!!");

                //Intent che mi porta al Broadcast Receiver
                Intent broadcastIntent = new Intent(getApplicationContext(), AlertReceiver.class);
                broadcastIntent.putExtra("notification_type", SETTINGS_NOTIFICATION);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 7, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Calendar c = Calendar.getInstance();

                c.add(Calendar.MINUTE, 1);
//                c.set(Calendar.HOUR_OF_DAY, 9);
//                c.set(Calendar.MINUTE, 0);
//                c.set(Calendar.SECOND, 0);

                //TODO setta notifica per le 9:00 di mattina
                String oraRemind = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
                Log.d(TAG, "La notifica arriverà alle ore  = " + oraRemind);

//                //Se l'orario è già passato allora setto la notifica per domani
//                if (c.before(Calendar.getInstance())) {
//                    Log.i(TAG, "sono le 9:00 di domani");
//                    c.add(Calendar.DATE, 1);
//                }

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                //fine notifiche


                Log.d(TAG, "MonitorJobService job finito");
                jobFinished(jobParameters, false);
            }
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
