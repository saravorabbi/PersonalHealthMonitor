package com.example.testmenu2.impostazioni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testmenu2.Database.Settings;
import com.example.testmenu2.Database.SettingsViewModel;
import com.example.testmenu2.MainActivity;
import com.example.testmenu2.R;
import com.example.testmenu2.Utilities.Converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

public class Impostazioni extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{

    private SimpleDateFormat SDF;
    private TextView TXVbattito_seekbar;
    private SeekBar battito_seekbar;
    private Button BTNsalva, BTNbattito_data1, BTNbattito_data2;
    private EditText ETbattito;
    private LinearLayout LLbattito;
    private SettingsViewModel settingsViewModel;
    private LiveData<List<Settings>> mSettings;
    private Settings battito, pressione, temperatura, glicemia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);

        SDF = new SimpleDateFormat("dd/MM/yyyy");

        BTNsalva = findViewById(R.id.BTNsave);
        battito_seekbar = findViewById(R.id.battito_seekbar);
        TXVbattito_seekbar = findViewById(R.id.TXVbattito_seekbar);
        BTNbattito_data1 = findViewById(R.id.BTNbattito_data1);
        BTNbattito_data2 = findViewById(R.id.BTNbattito_data2);
        ETbattito = findViewById(R.id.ETbattito_limite);
        LLbattito = findViewById(R.id.LLbattito);

        LLbattito.setVisibility(View.GONE);

        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        mSettings = settingsViewModel.getAllSettings();
        mSettings.observe(this, new Observer<List<Settings>>() {
            @Override
            public void onChanged(List<Settings> settings) {
                if (settings.size() == 0) {
                    newSettings();
                    mSettings = settingsViewModel.getAllSettings();
                }

                for (int i = 0; i<settings.size(); i++){
                    Settings tmpSetting = settings.get(i);
                    String value = tmpSetting.getValore();
                    switch (value){
                        case "Battito":
                            battito = tmpSetting;
                            battito_seekbar.setProgress(battito.getImportanza());
                            TXVbattito_seekbar.setText(String.valueOf(battito.getImportanza()));
                            if(battito.getImportanza()>2){
                                ETbattito.setText(String.valueOf(battito.getLimite()));
                                BTNbattito_data1.setText(Converters.DateToString(battito.getInizio()));
                                BTNbattito_data2.setText(Converters.DateToString(battito.getFine()));
                            }
                            break;
                    }
                }

            }
        });

        BTNsalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battito.setImportanza(battito_seekbar.getProgress());
                if(battito.getImportanza() > 2) {
                    String s = ETbattito.getText().toString();
                    battito.setLimite(Double.parseDouble(s));
                    battito.setInizio(Converters.StringToDate(String.valueOf(BTNbattito_data1.getText())));
                    battito.setFine(Converters.StringToDate(String.valueOf(BTNbattito_data2.getText())));
                    if (battito.getInizio() == null || battito.getFine() == null && (Converters.DateToLong(battito.getInizio()) > Converters.DateToLong(battito.getFine()))) {
                        Toast.makeText(getApplicationContext(), "Impostazioni errate", Toast.LENGTH_LONG).show();
                    } else {
                        settingsViewModel.updateSettings(battito);
                        finish();
                    }
                }
                else{
                    battito.setInizio(null);
                    battito.setFine(null);
                    battito.setLimite(0);
                    settingsViewModel.updateSettings(battito);
                    finish();
                }

                Toast.makeText(getApplicationContext(), "Impostazioni salvate", Toast.LENGTH_LONG).show();

            }
        });

        battito_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String s = String.valueOf(seekBar.getProgress());
                TXVbattito_seekbar.setText(s);
                battito.setImportanza(seekBar.getProgress());
                showRange("Battito", seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        BTNbattito_data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(BTNbattito_data1);
            }
        });

        BTNbattito_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(BTNbattito_data2);
            }
        });

    }

    private void showDatePickerDialog(Button button){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Sdate = dayOfMonth+"/"+(month+1)+"/"+year;
                button.setText(Sdate);
            }
        },
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }



    private void showRange(String string, int val) {
        switch(string){
            case "Battito":
                if(val>2) {
                    LLbattito.setVisibility(View.VISIBLE);
                    if(battito.getLimite() == 0){
                        BTNbattito_data1.setText(Converters.DateToString(Calendar.getInstance().getTime()));
                        BTNbattito_data2.setText(Converters.DateToString(Calendar.getInstance().getTime()));
                    }
                }
                else LLbattito.setVisibility(View.GONE);
                break;

        }
    }


    private void newSettings(){
        Settings tmpSettings = new Settings(0, "Battito", 2, null, null, 0);
        settingsViewModel.setSettings(tmpSettings);
        tmpSettings = new Settings(0, "Pressione", 2, null, null, 0);
        settingsViewModel.setSettings(tmpSettings);
        tmpSettings = new Settings(0, "Temperatura", 2, null, null, 0);
        settingsViewModel.setSettings(tmpSettings);
        tmpSettings = new Settings(0, "Glicemia", 2, null, null, 0);
        settingsViewModel.setSettings(tmpSettings);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}