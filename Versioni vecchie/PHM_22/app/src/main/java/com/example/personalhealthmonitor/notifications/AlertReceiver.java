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

import com.example.personalhealthmonitor.NewInfoActivity;
import com.example.personalhealthmonitor.R;

import java.util.Objects;


import static com.example.personalhealthmonitor.MainActivity.NEW_REPORT;
import static com.example.personalhealthmonitor.MainActivity.SHARED_PREF;
import static com.example.personalhealthmonitor.SettingsActivity.REMIND_NOTIFICATION;
import static com.example.personalhealthmonitor.SettingsActivity.SETTINGS_NOTIFICATION;
import static com.example.personalhealthmonitor.SettingsActivity.TIMED_NOTIFICATION;
import static com.example.personalhealthmonitor.notifications.App.CHANNEL_1_ID;

public class AlertReceiver extends BroadcastReceiver {

    private static final String TAG = "AlertReceiver: ";

    private Bundle bundle;
    private String notifyType;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "ALERT MANAGER si è svegliato!");

        bundle = intent.getExtras();

        if(bundle != null) {
            notifyType = bundle.getString("notification_type");

            //daily notification
            if( Objects.equals(notifyType, TIMED_NOTIFICATION) ){
                Log.i(TAG, "Sono nella Timed Notification");

                SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

                boolean aggiunto = prefs.getBoolean(NEW_REPORT, true);
                Log.i(TAG, "NEW_REPORT = " + aggiunto);

                if(aggiunto){
                    //SI NOTIFICA
                    Log.i(TAG, "SI NOTIFICA - devo aggiungere il report");

                    //creo intent che apre l'app nella NEW ACTIVITY
                    Intent newActivityIntent = new Intent(context, NewInfoActivity.class);
                    PendingIntent pendingIntentNew = PendingIntent.getActivity(context,1, newActivityIntent, 0);

//                    //creo intent che crea il Remi
//                    Intent remindLaterIntent = new Intent(context, RemindLater.class);
//                    remindLaterIntent.putExtra("notification_type", REMIND_NOTIFICATION);
//                    PendingIntent pendingIntentRemind = PendingIntent.getActivity(context, 1, remindLaterIntent, 0);

                    //creo la notifica che invio al cellulare
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)
                            .setContentTitle("Aggiungi un nuovo Report")
                            .setContentText("Non hai ancora inserito le info sulla tua salute!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setColor(Color.BLUE)
                            .setContentIntent(pendingIntentNew)    //quando faccio tap sulla notifica, apre la NEW ACTIVITY
                            .setAutoCancel(true)
                            .addAction(R.mipmap.ic_launcher, "Add ", pendingIntentNew);    //quando faccio tap sulla notifica, si dismissa
                            //.addAction(R.mipmap.ic_launcher, "Remind me later", pendingIntentRemind);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                    notificationManager.notify(200, builder.build());

                }

                else{
                    //NO NOTIFICA
                    Log.i(TAG, "NO NOTIFICA - ho già il report");

                    //risetto NEW REPORT a true per la notifica del giorno dopo
                    prefs.edit().putBoolean(NEW_REPORT, true).apply();
                    boolean newRepo = prefs.getBoolean(NEW_REPORT, true);
                    Log.i(TAG, "ADDED_REPO = " + newRepo );

                }
            }
            //reminded notification
            else if( Objects.equals(notifyType, REMIND_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Remind Me Later Notification");

            }
            //monitor notification
            else if( Objects.equals(notifyType, SETTINGS_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Monitor Settings Notification");

            }



        }else{
            Log.i(TAG, "Beh, c'è un errore, non hai messo il bundle da qualche parte");
        }


        Log.i(TAG, "Fine del Broadcast Receiver");
    }//end onReceive

}
