package com.example.personalhealthmonitor.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_NAME = "Health Notification";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels() {

        // Creazione channel 1 - una volta creati non si possono più modificare
        NotificationChannel channel1 = new NotificationChannel(
                CHANNEL_1_ID,
                CHANNEL_NAME,    //name channel - visibile all'utente nelle impostazioni del cellulare
                NotificationManager.IMPORTANCE_HIGH
        );
        channel1.setDescription("Personal Health Monitor Notifications");

        // Creazione manager
        NotificationManager manager = getSystemService(NotificationManager.class);

        // Aggiunta dei channel nel manager
        manager.createNotificationChannel(channel1);

    }

}
