package com.example.notificheprove1;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.notificheprove1.App.CHANNEL_3_ID;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //se triggero il broadcast receiver
        //posso eseguire codice con l'app chiusa
        String message = intent.getStringExtra("toastMessage");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_3_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_3_24)
                .setContentTitle("Terzo canale")
                .setContentText("NotificonaaaAA")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setAutoCancel(true);    //quando faccio tap sulla notifica, si dismissa


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());


    }
}
