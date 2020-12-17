package com.example.personalhealthmonitor.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.personalhealthmonitor.R;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.MainActivity.DAILY_TIME;
import static com.example.personalhealthmonitor.MainActivity.NOTIFICATION_ON;
import static com.example.personalhealthmonitor.MainActivity.SHARED_PREF;
import static com.example.personalhealthmonitor.SettingsActivity.REMIND_NOTIFICATION;
import static com.example.personalhealthmonitor.notifications.AlertReceiver.ID_NOTIFICATION_DAILY;
import static com.example.personalhealthmonitor.notifications.AlertReceiver.ID_NOTIFICATION_REMINDER;

public class RemindLater extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private TextView oraTv;
    private Button okBtn, cancelBtn;

    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_later);

        Log.i(TAG, "Siamo nella Remind Later Activity");

        oraTv = findViewById(R.id.ora_remind_later);
        okBtn = findViewById(R.id.btn_remind_ok);
        cancelBtn = findViewById(R.id.btn_remind_cancel);

        c = null;


        //dismiss la notifica quando clicco Add
        boolean notifyON = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
        Log.i(TAG, "NOTIFICATION_ON = " + notifyON );
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

            TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener(){

                @Override
                public void onTimeSet(TimePicker timePicker, int ore, int minuti) {

                    c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, ore);
                    c.set(Calendar.MINUTE, minuti);
                    c.set(Calendar.SECOND, 0);

                    String oraRemind = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
                    oraTv.setText(oraRemind);    //setto l'ora nel layout

                    Log.i(TAG, "TPD ora reminder = " + oraRemind);


                }
            }, HOUR, MINUTE, true);
            timePickerDialog.show();
        });


        //creo notifiche
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(c != null){

                    Log.i(TAG, "okBtn: creo notifica");

                        //Intent che mi porta al Broadcast Receiver
                        Intent broadcastIntent = new Intent(view.getContext(), AlertReceiver.class);
                        broadcastIntent.putExtra("notification_type", REMIND_NOTIFICATION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(view.getContext(), 4, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        //Se l'orario è già passato allora setto la notifica per domani
                        if (c.before(Calendar.getInstance())) {
                            Log.i(TAG, "okBtn: notifica settata a domani");
                            c.add(Calendar.DATE, 1);
                        }

                        Log.i(TAG, "okBtn: chiamo Alarm manager");


                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

                        finish();

                }else{

                    Log.i(TAG, "okBtn: calendario null");

                    Toast.makeText(getApplicationContext(),"Reminder non inserito",Toast.LENGTH_SHORT ).show();

                    finish();

                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    } //end


}