package com.example.personalhealthmonitor.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalhealthmonitor.R;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;

public class RemindLater extends AppCompatActivity {

    private TextView oraTv;

    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_later);

        oraTv = findViewById(R.id.ora_remind_later);
        Button okBtn = findViewById(R.id.btn_remind_ok);
        Button cancelBtn = findViewById(R.id.btn_remind_cancel);

        c = null;

        //dismiss la notifica quando clicco Add
        boolean notifyON = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);

        if(notifyON){
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.cancel(ID_NOTIFICATION_DAILY);
            notificationManager.cancel(ID_NOTIFICATION_REMINDER);
        }

        //Time Picker
        oraTv.setOnClickListener(view -> {

            //time picker
            Calendar calendar = Calendar.getInstance();
            int HOUR = calendar.get(Calendar.HOUR);
            int MINUTE = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), (timePicker, ore, minuti) -> {

                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, ore);
                c.set(Calendar.MINUTE, minuti);
                c.set(Calendar.SECOND, 0);

                String oraRemind = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
                oraTv.setText(oraRemind);    //setto l'ora nel layout

            }, HOUR, MINUTE, true);
            timePickerDialog.show();
        });


        //Ok button per creare le notifiche
        okBtn.setOnClickListener(view -> {

            if(c != null){

                //Intent che mi porta al Broadcast Receiver
                Intent broadcastIntent = new Intent(view.getContext(), AlertReceiver.class);
                broadcastIntent.putExtra(NOTIFICATION_TYPE, REMIND_NOTIFICATION);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(view.getContext(), 4, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                //Se l'orario è già passato allora setto la notifica per domani
                if (c.before(Calendar.getInstance())) {
                    c.add(Calendar.DATE, 1);
                }

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

            }else{
                Toast.makeText(getApplicationContext(),"Reminder non inserito",Toast.LENGTH_SHORT ).show();
            }

            finish();

        });

        //Cancel button
        cancelBtn.setOnClickListener(view -> finish());

    }

}