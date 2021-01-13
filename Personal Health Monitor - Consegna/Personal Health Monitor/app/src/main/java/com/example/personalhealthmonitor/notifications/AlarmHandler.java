package com.example.personalhealthmonitor.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.utils.UtilsValue.NOTIFICATION_TYPE;
import static com.example.personalhealthmonitor.utils.UtilsValue.PARAMETERS_OUT_BOUND_NOTIFICATION;

public class AlarmHandler {

    private static final String TAG = "ALARM HANDLER";

    private Context context;

    public AlarmHandler(Context context) {
        this.context = context;
    }

    public void setMonitorAlarm(){

        //Intent che porta al Broadcast Receiver
        Intent broadcastIntent = new Intent(context, AlertReceiver.class);
        broadcastIntent.putExtra(NOTIFICATION_TYPE, PARAMETERS_OUT_BOUND_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 7, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar c = Calendar.getInstance();

            //Setto l'orario per le 9 di mattina
            c.set(Calendar.HOUR_OF_DAY, 9);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

        //c.add(Calendar.MINUTE, 1);

        String oraRemind = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Log.d(TAG, "La notifica arriverà alle ore  = " + oraRemind);

        //se l'orario è già passato allora setto la notifica per domani
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        //Se l'orario è già passato allora setto la notifica per domani
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

}
