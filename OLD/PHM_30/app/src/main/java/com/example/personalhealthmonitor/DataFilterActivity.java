package com.example.personalhealthmonitor;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Query;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.InfoViewModel;
import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.example.personalhealthmonitor.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.example.personalhealthmonitor.MainActivity.infoViewModel;
import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;

public class DataFilterActivity extends AppCompatActivity {

    private static final String TAG = "DataFilterActivity";

    private TextView reportPriorityTV;
    private TextView personalTV, settimanaTV, meseTV, annoTV;
    private TextView personalDateBegin, personalDateEnd;
    private LinearLayout linearLayoutPersonal;

    private boolean personalON;

    //valori che servono a reperire valori dal DB
    private String priorita;
    private String inizioData, fineData;

    private List<String> parametersList;
    private LiveData<List<Info>> listReport;

    //date picker dei parametri
    private int YEAR;
    private int MONTH;
    private int DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_filter);

        //ID
        reportPriorityTV = findViewById(R.id.filter_importanza);
        personalTV = findViewById(R.id.filter_personal);
        settimanaTV = findViewById(R.id.filter_settimana);
        meseTV = findViewById(R.id.filter_mese);
        annoTV = findViewById(R.id.filter_anno);
        linearLayoutPersonal = findViewById(R.id.filter_personal_linear_layout);

        personalDateBegin = findViewById(R.id.filter_data_begin);
        personalDateEnd = findViewById(R.id.filter_data_end);


        //Creo la UI

        //priorità
        priorita = "1";
        reportPriorityTV.setText(priorita);

        //data inizio e fine - nel formato "dd/mm/yyyy"
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        inizioData = sdf.format(cal.getTime());
        fineData = sdf.format(cal.getTime());

        personalON = true;
        linearLayoutPersonal.setVisibility(View.VISIBLE);
        personalDateBegin.setText(inizioData);
        personalDateEnd.setText(fineData);

        //inizializzo i valori necessari ai datepicker
        YEAR = cal.get(Calendar.YEAR);
        MONTH = cal.get(Calendar.MONTH);
        DATE = cal.get(Calendar.DAY_OF_MONTH);


        //DB
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);





        Log.i(TAG, "RIPPERONEEEEEEEEEEEEE");

        //deve essere svolto in un thread in background
        //settingsViewModel.getParametersFromValue(Integer.parseInt(priorita));

        //parametersList =
        Log.i(TAG, "RIPPERONEEEEEEEEEEEEE");










        //===================//
        // ON CLICK LISTENER //
        //===================//

        personalDateBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                    String data = Utils.getDataSettings(anno, mese, giorno);

                    inizioData = data;
                    personalDateBegin.setText(data);

                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        personalDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                    String data = Utils.getDataSettings(anno, mese, giorno);

                    fineData = data;
                    personalDateEnd.setText(data);

                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });







        //PULSANTI AZZURRIII DA CANCELLARE PROBABILMENTE

        reportPriorityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRadioOption();

                parametersList = settingsViewModel.getParametersFromValue(Integer.parseInt(priorita));
            }
        });


        personalTV.setOnClickListener(view -> {

            if(personalON){
                linearLayoutPersonal.setVisibility(View.GONE);
                personalTV.setTextColor(Color.parseColor("#34353A"));
                personalON = false;
            }else {
                linearLayoutPersonal.setVisibility(View.VISIBLE);
                personalTV.setTextColor(Color.parseColor("#FFFFFF"));
                personalON = true;
            }
            settimanaTV.setTextColor(Color.parseColor("#34353A"));
            meseTV.setTextColor(Color.parseColor("#34353A"));
            annoTV.setTextColor(Color.parseColor("#34353A"));

            //TODO setta la data
            // infoViewModel.getReport


        });

        settimanaTV.setOnClickListener(view -> {

            if(personalON) {
                linearLayoutPersonal.setVisibility(View.GONE);
                personalON = false;
            }
            personalTV.setTextColor(Color.parseColor("#34353A"));
            settimanaTV.setTextColor(Color.parseColor("#FFFFFF"));
            meseTV.setTextColor(Color.parseColor("#34353A"));
            annoTV.setTextColor(Color.parseColor("#34353A"));


        });

        meseTV.setOnClickListener(view -> {

            if(personalON) {
                linearLayoutPersonal.setVisibility(View.GONE);
                personalON = false;
            }
            personalTV.setTextColor(Color.parseColor("#34353A"));
            settimanaTV.setTextColor(Color.parseColor("#34353A"));
            meseTV.setTextColor(Color.parseColor("#FFFFFF"));
            annoTV.setTextColor(Color.parseColor("#34353A"));

        });

        annoTV.setOnClickListener(view -> {

            if(personalON) {
                linearLayoutPersonal.setVisibility(View.GONE);
                personalON = false;
            }
            personalTV.setTextColor(Color.parseColor("#34353A"));
            settimanaTV.setTextColor(Color.parseColor("#34353A"));
            meseTV.setTextColor(Color.parseColor("#34353A"));
            annoTV.setTextColor(Color.parseColor("#FFFFFF"));

        });

    }//end on create

    private void showRadioOption() {
        String[] priority = {"1", "2", "3", "4", "5"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DataFilterActivity.this);
        builder.setTitle("Scegli la priorità");
        builder.setSingleChoiceItems(priority, (Integer.parseInt(priorita)-1) , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                priorita = priority[i];
                reportPriorityTV.setText(priorita);
                Toast.makeText(DataFilterActivity.this, "Priorità selezionata: " + priorita, Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    public void changeListReport(View view){
        //todo chiama il list adapter e mostra la lista di report
    }


}













