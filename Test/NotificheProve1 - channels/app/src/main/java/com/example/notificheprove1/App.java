package com.example.notificheprove1;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    public static final String CHANNEL_3_ID = "channel3";

    public static final String CHANNEL_NAME = "Channel 1";
    public static final String CHANNEL_NAME_3 = "Channel 3";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    //metodo per creare i canali delle notifiche
    private void createNotificationChannels() {

        //funziona solo da oreo in su
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            //creazione channel 1 - una volta creati non si possono più modificare
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    CHANNEL_NAME,    //name channel visibile all'utente
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            //creazione channel 2
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",    //name channel visibile all'utente
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");


            //creazione channel 3
            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_3_ID,
                    CHANNEL_NAME_3,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel3.setDescription("Notifiche con un orario");

            //creazione manager
            NotificationManager manager = getSystemService(NotificationManager.class);
            //aggiunta dei channel nel manager
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

            manager.createNotificationChannel(channel3);





        }
    }
}
