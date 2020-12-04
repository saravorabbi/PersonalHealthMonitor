package com.example.personalhealthmonitor;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        
    }
}