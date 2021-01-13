package com.example.personalhealthmonitor.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.personalhealthmonitor.NewInfoActivity;
import com.example.personalhealthmonitor.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;
import static com.example.personalhealthmonitor.notifications.App.CHANNEL_1_ID;

public class AlertReceiver extends BroadcastReceiver {

    private static final String TAG = "AlertReceiver: ";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "ALERT MANAGER si è svegliato!");

        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            String notifyType = bundle.getString(NOTIFICATION_TYPE);

            //DAILY NOTIFICATION
            if( Objects.equals(notifyType, TIMED_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Timed Notification");

                //controllo se ho già aggiunto un report oggi
                //True: non aggiunto - si notifica, False: aggiunto - no notifica
                SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
                boolean aggiunto = prefs.getBoolean(NEW_REPORT, true);

                if(aggiunto){
                    //SI NOTIFICA
                    createDailyNotification(context);
                }

                else{
                    //NO NOTIFICA
                    //risetto NEW REPORT a true per la notifica del giorno dopo
                    prefs.edit().putBoolean(NEW_REPORT, true).apply();
                }
            }

            //REMINDER NOTIFICATION
            else if( Objects.equals(notifyType, REMIND_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Remind Me Later Notification");

                //creo notifica giornaliera
                createReminderNotification(context);
            }

            //SETTINGS NOTIFICATION
            else if( Objects.equals(notifyType, PARAMETERS_OUT_BOUND_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Settings Notification");

                createOutOfBoundNotification(context);
            }

            //MONITOR NOTIFICATION
            else if( Objects.equals(notifyType, MONITOR_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Monitor Notification - FACCIO PARTIRE IL SERVICE");

                //chiamo il foreground service per controllare i parametri
                Intent serviceIntent = new Intent(context, ForegroundService.class);
                ContextCompat.startForegroundService(context, serviceIntent);
            }
        }

    }//end onReceive



    private void createDailyNotification(Context context){

        //Intent che apre l'app nella NEW INFO ACTIVITY
        Intent newInfoIntent = new Intent(context, NewInfoActivity.class);
        PendingIntent pendingIntentNewInfo = PendingIntent.getActivity(context,2, newInfoIntent, 0);

        //Intent che crea la Reminder Notification
        Intent remindLaterIntent = new Intent(context, RemindLater.class);
        PendingIntent pendingIntentRemind = PendingIntent.getActivity(context, 3, remindLaterIntent, 0);

        //creo la notifica
        NotificationCompat.Builder dailyBuilder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)
                .setContentTitle("Aggiungi un nuovo Report")
                .setContentText("Non hai ancora inserito le info sulla tua salute!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntentNewInfo)    //quando faccio click sulla notifica, apre la NEW ACTIVITY
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Add ", pendingIntentNewInfo)    //quando faccio tap sulla notifica, dismiss
                .addAction(R.mipmap.ic_launcher, "Remind me later", pendingIntentRemind);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(ID_NOTIFICATION_DAILY, dailyBuilder.build());

    }


    private void createReminderNotification(Context context){

        //Intent che apre l'app nella NEW ACTIVITY
        Intent newInfoIntent = new Intent(context, NewInfoActivity.class);
        PendingIntent pendingIntentNewInfo = PendingIntent.getActivity(context,5, newInfoIntent, 0);

        //Intent che crea la Reminder Notification
        Intent remindLaterIntent = new Intent(context, RemindLater.class);
        PendingIntent pendingIntentRemind = PendingIntent.getActivity(context, 6, remindLaterIntent, 0);

        //creo la notifica che invio al cellulare
        NotificationCompat.Builder dailyBuilder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)
                .setContentTitle("Aggiungi un nuovo Report - Reminder")
                .setContentText("Non hai ancora inserito il report sulla tua salute!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntentNewInfo)    //quando faccio tap sulla notifica, apre la NEW ACTIVITY
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Add ", pendingIntentNewInfo)    //quando faccio tap sulla notifica, dismiss
                .addAction(R.mipmap.ic_launcher, "Remind me later", pendingIntentRemind);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(ID_NOTIFICATION_REMINDER, dailyBuilder.build());
    }


    private void createOutOfBoundNotification(Context context){

        boolean flag = false;
        String text = "";

        List<String> stringList = new ArrayList<>();

        //check shared prefs -> per creare testo notifica
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        if( prefs.getBoolean(EXCEEDED_TEMP, true) ){
            stringList.add(TEMPERATURA);
            flag = true;
        }
        if( prefs.getBoolean(EXCEEDED_PRESS_SIS, true) ){
            stringList.add(PRESSIONE_SISTOLICA);
            flag = true;
        }
        if( prefs.getBoolean(EXCEEDED_PRESS_DIA, true) ){
            stringList.add(PRESSIONE_DIASTOLICA);
            flag = true;
        }
        if( prefs.getBoolean(EXCEEDED_GLIC, true) ){
            stringList.add(GLICEMIA);
            flag = true;
        }
        if( prefs.getBoolean(EXCEEDED_PESO, true) ){
            stringList.add(PESO);
            flag = true;
        }

        //ciclo che crea la stringa con i nomi dei parametri
        for (int i = 0; i < stringList.size(); i++){
            text = text + stringList.get(i);
            if( i != (stringList.size() - 1) ){
                text = text + " - ";
            }
        }


        //manda notifica
        if(flag) {
            Log.i(TAG, "Monitor Notification: invio la notifica");

            NotificationCompat.Builder dailyBuilder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)
                    .setContentTitle("Monitor Notification - Soglia predefinitia superata")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setColor(Color.RED)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify(ID_NOTIFICATION_MONITOR, dailyBuilder.build());

        }else{

            Log.i(TAG, "Monitor Notification: non invio la notifica");

        }
    }


}
