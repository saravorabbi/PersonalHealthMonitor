package com.example.timeprove;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity: ";

    private TextView notificationTimeTV;
    private String notifyTime;
    private Button button;
    private Calendar calendarNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationTimeTV = findViewById(R.id.time_picker_tv);
        button = findViewById(R.id.button);



        String tempo = "23:00";
        String ore = tempo.substring(0, 2);
        String minuti = tempo.substring(3, 5);

        Log.i(TAG, "Ore " + tempo);
        Log.i(TAG, "Ora: " + ore + " minuti " + minuti);

        int oreint = Integer.parseInt(ore);
        int minutiint = Integer.parseInt(minuti);

        Log.i(TAG, "Ora: " + oreint + " minuti " + minutiint);

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, oreint);
        c.set(Calendar.MINUTE, minutiint);
        c.set(Calendar.SECOND, 0);

        if ( Calendar.getInstance().before(c) ) Log.i(TAG, "orario c è prima dell'ora attuale");
        else Log.i(TAG, "orario c è dopo l'ora attuale");




        notificationTimeTV.setOnClickListener(new View.OnClickListener() {
            //Update della TextView con l'ora della notifica
            //Set l'allarme che farà partire il BroadcastManager

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

                        //TODO Time notifyTime = notificationTimeTV.getFromTextView(ora) //generalmente sono le 09:00

                        Log.i(TAG, "TimePicker: settato l'ora, parte la notifica");
                        updateTimeText(calendar1);
                        //startTimedNotification(calendar1);

                        calendarNotify = calendar1;

                    }
                }, HOUR, MINUTE, true);
                timePickerDialog.show();
            }
        });

    }

    public void updateTimeText(Calendar c){
        notifyTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        notificationTimeTV.setText(notifyTime);

        Log.i(TAG, notifyTime);

    }

    public void funzione(View view){

        if (calendarNotify.before(Calendar.getInstance())) {
            Log.i(TAG, "Data selezionata va a domani");
        }

        double mill = calendarNotify.getTimeInMillis();

        Log.i(TAG, "Millisecondi" + mill);

    }


}