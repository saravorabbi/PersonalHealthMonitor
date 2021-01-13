package com.example.personalhealthmonitor;

import android.os.Bundle;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    EditText tempSetET, pressisSetET, pressdiaSetET, glicSetET, pesoSetET;
    EditText tempLowBoundET, pressisLowBoundET, presdiaLowBoundET, glicLowBoundET, pesoLowBoundET;
    EditText tempUpperBoundET, pressisUpperBoundET, presdiaUpperBoundET, glicUpperBoundET, pesoUpperBoundET;
    //TODO text view delle date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //id temp
        tempSetET = findViewById(R.id.temperatura_setting);
        tempLowBoundET = findViewById(R.id.temp_lower_bound);
        tempUpperBoundET = findViewById(R.id.temp_upper_bound);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        
    }
}