package com.example.notificheprove2;

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

import java.util.Objects;

import static com.example.notificheprove2.App.CHANNEL_1_ID;
import static com.example.notificheprove2.MainActivity.ADDED_REPO;
import static com.example.notificheprove2.MainActivity.REMIND_NOTIFICATION;
import static com.example.notificheprove2.MainActivity.SHARED_PREF;
import static com.example.notificheprove2.MainActivity.TIMED_NOTIFICATION;

public class AlertReceiver extends BroadcastReceiver {
    public static final String TAG = "AlertReceiver: ";
    Bundle bundle;
    String notifyType;

    @Override
    public void onReceive(Context context, Intent intent) {
        //quando scatta l'allarme il broadcast receiver si accende
        Log.i(TAG, "ALERT MANAGER si è svegliato!");

        bundle = intent.getExtras();

        if(bundle != null){
            notifyType = bundle.getString("notification_type");

            //creo la daily notification
            if(Objects.equals(notifyType, TIMED_NOTIFICATION)){
                Log.i(TAG, "Sono nella Timed Notification");

                SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

                boolean aggiunto = prefs.getBoolean(ADDED_REPO, true);

                Log.i(TAG, "ADDED_REPO = " + String.valueOf(aggiunto));


                if( !aggiunto ) {
                    //non ho ancora aggiunto il report
                    Log.i(TAG, "NON HO AGGIUNTO IL REPORT, MANDO LA NOTIFICA");
                    //creo intent che aprono l'app nella NEW ACTIVITY
                    Intent newActivityIntent = new Intent(context, NewActivity.class);
                    PendingIntent pendingIntentNew = PendingIntent.getActivity(context,
                            1, newActivityIntent, 0);

                    //creo intent che crea il Remi
                    Intent remindLaterIntent = new Intent(context, RemindLater.class);
                    remindLaterIntent.putExtra("notification_type", REMIND_NOTIFICATION);
                    PendingIntent pendingIntentRemind = PendingIntent.getActivity(context,
                            1, remindLaterIntent, 0);

                    //creo la notifica che invio al cellulare
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_baseline_filter_1_24)
                            .setContentTitle("Aggiungi un nuovo Report")
                            .setContentText("Non hai ancora inserito le info sulla tua salute!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setColor(Color.BLUE)
                            .setContentIntent(pendingIntentNew)    //quando faccio tap sulla notifica, apre la NEW ACTIVITY
                            .setAutoCancel(true)
                            .addAction(R.mipmap.ic_launcher, "Add ", pendingIntentNew)    //quando faccio tap sulla notifica, si dismissa
                            .addAction(R.mipmap.ic_launcher, "Remind me later", pendingIntentRemind);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                    notificationManager.notify(200, builder.build());
                }else{
                    Log.i(TAG, "Ho aggiunto il report, la notifica non scatta");
                }

            }else if ( Objects.equals(notifyType, REMIND_NOTIFICATION) ){
                Log.i(TAG, "Sono nella Remind Me Later Notification");
            }else{
                Log.i(TAG, notifyType);
            }



        }else{
            Log.i(TAG, "Beh, c'è un errore, non hai messo il bundle da qualche parte");
        }




    }
}
