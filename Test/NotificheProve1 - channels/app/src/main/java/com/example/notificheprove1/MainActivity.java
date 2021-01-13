package com.example.notificheprove1;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.notificheprove1.App.CHANNEL_1_ID;
import static com.example.notificheprove1.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager; //to show our notification
    private EditText editTextTitle;
    private EditText editTextMessagge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessagge = findViewById(R.id.edit_text_messaggio);
    }


    //onclick del button per il primo canale
    public void sendOnChannel1(View v){
        String title = editTextTitle.getText().toString();
        String messaggio = editTextMessagge.getText().toString();

        //intent aperto quando tocco la notifica -> apro MessageActivity
        Intent activityIntent = new Intent(this, MessageActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        // broacast receiver -> chiama del codice che viene eseguito
        Intent broadcartIntent = new Intent(this, NotificationBroadcastReceiver.class);
        broadcartIntent.putExtra("toastMessage", messaggio);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcartIntent, PendingIntent.FLAG_UPDATE_CURRENT);




        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_ac_unit_24)
                .setContentTitle(title)
                .setContentText(messaggio)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)    //quando faccio tap sulla notifica, si dismissa
/*                .setOnlyAlertOnce(true) //COMMENTALA DOPO OK?!?!?     */
                .addAction(R.mipmap.ic_launcher, "Toast ", actionIntent)
                .build();

        // faccio partire la notifica
        notificationManager.notify(1, notification);

    }



    //on click notifica priorità LOW
    public void sendOnChannel2(View v){
        String title = editTextTitle.getText().toString();
        String message = editTextMessagge.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_bathtub_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }


    public void sendOnChannel3(View view){

        Toast.makeText(this, "Notifica settata", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtButtonClick = System.currentTimeMillis();
        long dieciSecInMillisec = 1000 * 10;

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + dieciSecInMillisec, pendingIntent);

    }


}