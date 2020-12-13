package com.example.personalhealthmonitor.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

import static com.example.personalhealthmonitor.MainActivity.ADDED_REPO;
import static com.example.personalhealthmonitor.MainActivity.SHARED_PREF;
import static com.example.personalhealthmonitor.SettingsActivity.REMIND_NOTIFICATION;
import static com.example.personalhealthmonitor.SettingsActivity.SETTINGS_NOTIFICATION;
import static com.example.personalhealthmonitor.SettingsActivity.TIMED_NOTIFICATION;

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

                boolean aggiunto = prefs.getBoolean(ADDED_REPO, true);
                Log.i(TAG, "ADDED_REPO = " + String.valueOf(aggiunto));

                if(aggiunto){

                    //SI NOTIFICA
                    //devo ancora aggiungere il report

                }

                else{

                    //NO NOTIFICA
                    //non ho ancora aggiunto il report
                    //set "ADDED_REPO = true"
                    //set "i++"

                }






            }
            //reminded notification
            else if( Objects.equals(notifyType, REMIND_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Remind Me Later Notification");

            }
            else if( Objects.equals(notifyType, SETTINGS_NOTIFICATION) ){

                Log.i(TAG, "Sono nella Monitor Settings Notification");

            }



        }else{
            Log.i(TAG, "Beh, c'è un errore, non hai messo il bundle da qualche parte");
        }

    }//end onReceive

}
