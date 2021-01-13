package com.example.notificheprove2;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_1_ID = "channel1";

    public static final String CHANNEL_NAME = "Channel 1";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }


    //metodo per creare i canali delle notifiche
    private void createNotificationChannels() {

        //funziona solo da oreo in su
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //creazione channel 1 - una volta creati non si possono più modificare
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    CHANNEL_NAME,    //name channel visibile all'utente
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Timed Notifications");

            //creazione manager
            NotificationManager manager = getSystemService(NotificationManager.class);

            //aggiunta dei channel nel manager
            manager.createNotificationChannel(channel1);

        }
    }

}
