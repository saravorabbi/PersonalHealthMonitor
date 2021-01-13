package com.example.notificheprove2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main Activity: ";

    //intent code
    public static final String TIMED_NOTIFICATION = "timed_notification";
    public static final String REMIND_NOTIFICATION = "reminded_notification";
    public static final String SETTINGS_NOTIFICATION = "settings_notification";

    //shared prefs
    public static final String SHARED_PREF = "sharedPref";
    public static final String SP_CREATED = "dbCreated";
    public static final String IS_TIME_ON = "DailyNotifications";
    public static final String ADDED_REPO = "addedReport";


    private boolean isTimeCheck;

    //view
    private ToggleButton toggleButton;
    private LinearLayout linearLayoutNotifiche;
    private TextView timeTW;
    private Switch aSwitch;
    private EditText addNumer;
    private Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // collego view con id
        toggleButton = findViewById(R.id.toggleButton_notifiche);
        linearLayoutNotifiche = findViewById(R.id.linear_time_notifiche);
        timeTW = findViewById(R.id.time_notifiche);
        aSwitch = findViewById(R.id.switch_add_data);
        addNumer = findViewById(R.id.edit_text_int);
        buttonOK = findViewById(R.id.button_ok);

        //shared preferences
        // setto off il toggle button quando apro l'app la prima volta
        boolean notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(SP_CREATED, true);
        Log.i(TAG, "Creato: " + String.valueOf(notCreated));

        if(notCreated) {

            getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(SP_CREATED, false).apply();
            notCreated = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(SP_CREATED, true);
            Log.i(TAG, "notCreated = " + String.valueOf(notCreated));


            getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(IS_TIME_ON, false).apply();
            boolean isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(IS_TIME_ON, true);
            Log.i(TAG, "IS_TIME_ON = " + String.valueOf(isTimeCheck));

            getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(ADDED_REPO, false).apply();
            boolean isRepoAdded = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(IS_TIME_ON, true);
            Log.i(TAG, "IS_TIME_ON = " + String.valueOf(isRepoAdded));
        }

        // setto il toggle lo switch ON/OFF
        boolean Check = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(IS_TIME_ON, true);
        toggleButton.setChecked(Check);
        Log.i(TAG, "IS_TIME_ON (notifica on TRUE, notifica off FALSE)=  " + String.valueOf(Check));
        if(Check){
            linearLayoutNotifiche.setVisibility(View.VISIBLE);
        } else linearLayoutNotifiche.setVisibility(View.GONE);

        boolean addedOn = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(ADDED_REPO, true);
        aSwitch.setChecked(addedOn);
        Log.i(TAG, "ADDED_REPO (repo aggiunto TRUE, repo non aggiunto off FALSE)=  " + String.valueOf(addedOn));



        toggleButton.setOnClickListener(new View.OnClickListener() {
            // Quando  isCheched => è attiva la notifica per l'orario
            // Quando !isChecked => deve disattivare la notifica
            @Override
            public void onClick(View view) {
                if( toggleButton.isChecked() ){
                    linearLayoutNotifiche.setVisibility(View.VISIBLE);

                    //shared preferences settato a ON
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(IS_TIME_ON, true).apply();
                    isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(IS_TIME_ON, true);
                    Log.i(TAG, "IS_TIME_ON = " + String.valueOf(isTimeCheck));
                    Log.i(TAG, "Attivo la notifica, mostro il Time Picker");
                }else{
                    linearLayoutNotifiche.setVisibility(View.GONE);

                    //shared preferences settato a OFF
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(IS_TIME_ON, false).apply();
                    isTimeCheck = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(IS_TIME_ON, true);
                    Log.i(TAG, "IS_TIME_ON = " + String.valueOf(isTimeCheck));

                    //TODO setto notifiche OFF => cancelNotification
                    Log.i(TAG, "Disattivo la notifica");
                    cancelTimedNotification();

                }
            }
        });

        // Quando clicco la text view con l'orario della notifica mostro il TimePicker
        timeTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //time picker
                Calendar calendar = Calendar.getInstance();
                int HOUR = calendar.get(Calendar.HOUR);
                int MINUTE = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker timePicker, int ore, int minuti) {

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY, ore);
                        calendar1.set(Calendar.MINUTE, minuti);
                        calendar1.set(Calendar.SECOND, 0);

                        Log.i(TAG, "Orario settato NEL TIMEPICKER, ora parte la notifica");
                        updateTimeText(calendar1);
                        startTimedNotification(calendar1);

                    }
                }, HOUR, MINUTE, true);

                timePickerDialog.show();

            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bol = aSwitch.isChecked();
                if(bol){
                    getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(ADDED_REPO, true).apply();
                } else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(ADDED_REPO, false).apply();
            }
        });

    }   //end on create


    public void cancelTimedNotification(){
        Log.i(TAG, "NOTIFICA CANCELLATA");

        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Notifica cancellata", Toast.LENGTH_SHORT).show();
    }

    public void startTimedNotification(Calendar cal){
        Log.i(TAG, "startTimedNotification");
//        //tocco notifica -> apro NEW ACTIVITY
//        Intent activityIntent = new Intent(this, NewActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, activityIntent, 0);

        //
        Intent broadcastIntent = new Intent(this, AlertReceiver.class);
        broadcastIntent.putExtra("notification_type", TIMED_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                1, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //check if time is already passed, notification is for tomorrow
        if (cal.before(Calendar.getInstance())) {
            Log.i(TAG, "startTimedNotification - show domani");
            cal.add(Calendar.DATE, 1);
        }
        Log.i(TAG, "startTimedNotification - Alarm manager");
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }


    public void updateTimeText(Calendar c){
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeTW.setText(timeText);
    }
}