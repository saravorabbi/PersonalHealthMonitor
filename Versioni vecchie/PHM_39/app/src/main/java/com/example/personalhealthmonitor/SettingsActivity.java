package com.example.personalhealthmonitor;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.example.personalhealthmonitor.notifications.AlertReceiver;
import com.example.personalhealthmonitor.utils.Utils;
import com.google.android.material.snackbar.Snackbar;


import java.text.DateFormat;
import java.util.Calendar;

import static com.example.personalhealthmonitor.utils.UtilsValue.*;
import static com.example.personalhealthmonitor.utils.Utils.*;

import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;



public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "Setting activity";

    //view dei parametri
    private SeekBar tempSeekBar, presSisSeekBar, presDiaSeekBar, glicSeekBar, pesoSeekBar;
    private TextView tempTxtView, presSisTxtView, presDiaTxtView, glicTxtView, pesoTxtView;
    private EditText tempLowBoundET, presSisLowBoundET, presDiaLowBoundET, glicLowBoundET, pesoLowBoundET;
    private EditText tempUpperBoundET, presSisUpperBoundET, presDiaUpperBoundET, glicUpperBoundET, pesoUpperBoundET;
    private TextView tempBeginDate, presSisBeginDate, presDiaBeginDate, glicBeginDate, pesoBeginDate;
    private TextView tempEndDate, presSisEndDate, presDiaEndDate, glicEndDate, pesoEndDate;

    private LinearLayout linearLayoutTEMP, linearLayoutPRESSIS, linearLayoutPRESDIA, linearLayoutGLIC, linearLayoutPESO;

    //date picker dei parametri
    private int YEAR;
    private int MONTH;
    private int DATE;


    //view delle notifiche
    private ToggleButton toggleButton;
    private LinearLayout linearLayoutNotifiche;
    private TextView notificationTimeTV;

    //orario notifica
    private Calendar calendarNotify;    //var in cui salvo l'ora della notifica per la notifica

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //temperatura
        tempSeekBar = findViewById(R.id.temperatura_setting);
        tempTxtView = findViewById(R.id.temp_value);
        tempLowBoundET = findViewById(R.id.temp_lower_bound);
        tempUpperBoundET = findViewById(R.id.temp_upper_bound);
        tempBeginDate = findViewById(R.id.temp_begin_date);
        tempEndDate = findViewById(R.id.temp_end_date);

        //pressione sistolica
        presSisSeekBar = findViewById(R.id.pressione_sistolica_setting);
        presSisTxtView = findViewById(R.id.press_sis_value);
        presSisLowBoundET = findViewById(R.id.press_sis_lower_bound);
        presSisUpperBoundET = findViewById(R.id.press_sis_upper_bound);
        presSisBeginDate = findViewById(R.id.press_sis_begin_date);
        presSisEndDate = findViewById(R.id.press_sis_end_date);

        //pressione diastolica
        presDiaSeekBar = findViewById(R.id.pressione_diastolica_setting);
        presDiaTxtView = findViewById(R.id.press_dia_value);
        presDiaLowBoundET = findViewById(R.id.press_dia_lower_bound);
        presDiaUpperBoundET = findViewById(R.id.press_dia_upper_bound);
        presDiaBeginDate = findViewById(R.id.press_dia_begin_date);
        presDiaEndDate = findViewById(R.id.press_dia_end_date);

        //glicemia
        glicSeekBar = findViewById(R.id.glicemia_setting);
        glicTxtView = findViewById(R.id.glic_value);
        glicLowBoundET = findViewById(R.id.glic_lower_bound);
        glicUpperBoundET = findViewById(R.id.glic_upper_bound);
        glicBeginDate = findViewById(R.id.glic_begin_date);
        glicEndDate = findViewById(R.id.glic_end_date);

        //peso
        pesoSeekBar = findViewById(R.id.peso_setting);
        pesoTxtView = findViewById(R.id.peso_value);
        pesoLowBoundET = findViewById(R.id.peso_lower_bound);
        pesoUpperBoundET = findViewById(R.id.peso_upper_bound);
        pesoBeginDate = findViewById(R.id.peso_begin_date);
        pesoEndDate = findViewById(R.id.peso_end_date);

        //linear layout
        linearLayoutTEMP = findViewById(R.id.linearTemperatura);
        linearLayoutPRESSIS = findViewById(R.id.linearPressioneSistolica);
        linearLayoutPRESDIA = findViewById(R.id.linearPressioneDiastolica);
        linearLayoutGLIC = findViewById(R.id.linearGlicemia);
        linearLayoutPESO = findViewById(R.id.linearPeso);

        //ok - cancel
        Button okBtn = findViewById(R.id.saveSettings);
        Button cancelBtn = findViewById(R.id.cancelSettings);

        //notifiche
        toggleButton = findViewById(R.id.toggleButton_notifiche);
        notificationTimeTV = findViewById(R.id.time_notifiche);
        linearLayoutNotifiche = findViewById(R.id.linear_time_notifiche);

        //inizializzo i valori necessari ai datepicker
        Calendar cal = Calendar.getInstance();
        YEAR = cal.get(Calendar.YEAR);
        MONTH = cal.get(Calendar.MONTH);
        DATE = cal.get(Calendar.DAY_OF_MONTH);


        //PRIMA ESECUZIONE - INIZIO

        //notifiche
        //setto il toggle all'apertura della Settings Activity
        boolean toggleBool = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getBoolean(NOTIFICATION_ON, true);
        toggleButton.setChecked(toggleBool);

        //Notifica ON
        if(toggleBool){
            //setto la visibilità del linear layout che contiene il date picker
            linearLayoutNotifiche.setVisibility(View.VISIBLE);

            String dailyTime = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(DAILY_TIME, DAILY_TIME_DEFAULT);
            //Log.i(TAG, "DAILY_TIME = " + dailyTime );

            //Setto il calendario col tempo dentro DAILY_TIME
            String ore = dailyTime.substring(0, 2);
            String minuti = dailyTime.substring(3, 5);

            int oreint = Integer.parseInt(ore);
            int minutiint = Integer.parseInt(minuti);

            //creo calendario
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, oreint);
            c.set(Calendar.MINUTE, minutiint);
            c.set(Calendar.SECOND, 0);

            //setto il calendario in una val globale
            calendarNotify = c;

            //setto l'ora DAILY_TIME nel layout
            notificationTimeTV.setText(dailyTime);

        }
        //Notifica OFF
        else linearLayoutNotifiche.setVisibility(View.GONE);


        //parametri - DB
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        LiveData<Settings> settingsTemp = settingsViewModel.getSettingsFromParametro(DB_TEMPERATURA);
        LiveData<Settings> settingsPressSis = settingsViewModel.getSettingsFromParametro(DB_PRESSIONE_SISTOLICA);
        LiveData<Settings> settingsPressDia = settingsViewModel.getSettingsFromParametro(DB_PRESSIONE_DIASTOLICA);
        LiveData<Settings> settingsGlicemia = settingsViewModel.getSettingsFromParametro(DB_GLICEMIA);
        LiveData<Settings> settingsPeso = settingsViewModel.getSettingsFromParametro(DB_PESO);


        // ~~~~~~~ //
        // OBSERVE - inserisco tutti i dati nel layout
        // ~~~~~~~ //

        // Temperatura
        settingsTemp.observe(this, setting -> {

            int val = setting.getValue();

            tempSeekBar.setProgress( val );
            tempTxtView.setText( String.valueOf(val) );
            tempLowBoundET.setText( String.valueOf(setting.getLowerBound()) );
            tempUpperBoundET.setText( String.valueOf(setting.getUpperBound()) );
            tempBeginDate.setText( setting.getBeginDate() );
            tempEndDate.setText( setting.getEndDate() );

            //setto la visibilità del monitoraggio
            if( val > 2 ) linearLayoutTEMP.setVisibility(View.VISIBLE);
            else linearLayoutTEMP.setVisibility(View.GONE);

        });

        //Pressione Sistolica
        settingsPressSis.observe(this, settings -> {

            int val = settings.getValue();

            presSisSeekBar.setProgress( val );
            presSisTxtView.setText( String.valueOf(val) );
            presSisLowBoundET.setText( String.valueOf(settings.getLowerBound()) );
            presSisUpperBoundET.setText( String.valueOf(settings.getUpperBound()) );
            presSisBeginDate.setText( settings.getBeginDate() );
            presSisEndDate.setText( settings.getEndDate() );

            //setto la visibilità del monitoraggio
            if( val > 2 ) linearLayoutPRESSIS.setVisibility(View.VISIBLE);
            else linearLayoutPRESSIS.setVisibility(View.GONE);

        });

        //Pressione Diastolica
        settingsPressDia.observe(this, settings -> {

            int val = settings.getValue();

            presDiaSeekBar.setProgress( val );
            presDiaTxtView.setText( String.valueOf(val) );
            presDiaLowBoundET.setText( String.valueOf(settings.getLowerBound()) );
            presDiaUpperBoundET.setText( String.valueOf(settings.getUpperBound()) );
            presDiaBeginDate.setText( settings.getBeginDate() );
            presDiaEndDate.setText( settings.getEndDate() );

            //setto la visibilità del monitoraggio
            if( val > 2 ) linearLayoutPRESDIA.setVisibility(View.VISIBLE);
            else linearLayoutPRESDIA.setVisibility(View.GONE);

        });

        //Glicemia
        settingsGlicemia.observe(this, settings -> {

            int val = settings.getValue();

            glicSeekBar.setProgress( val );
            glicTxtView.setText( String.valueOf(val) );
            glicLowBoundET.setText( String.valueOf(settings.getLowerBound()) );
            glicUpperBoundET.setText( String.valueOf(settings.getUpperBound()) );
            glicBeginDate.setText( settings.getBeginDate() );
            glicEndDate.setText( settings.getEndDate() );

            //setto la visibilità del monitoraggio
            if( val > 2 ) linearLayoutGLIC.setVisibility(View.VISIBLE);
            else linearLayoutGLIC.setVisibility(View.GONE);

        });

        //Peso
        settingsPeso.observe(this, settings -> {

            int val = settings.getValue();

            pesoSeekBar.setProgress( val );
            pesoTxtView.setText( String.valueOf(val) );
            pesoLowBoundET.setText( String.valueOf(settings.getLowerBound()) );
            pesoUpperBoundET.setText( String.valueOf(settings.getUpperBound()) );
            pesoBeginDate.setText( settings.getBeginDate() );
            pesoEndDate.setText( settings.getEndDate() );

            //setto la visibilità del monitoraggio
            if( val > 2 ) linearLayoutPESO.setVisibility(View.VISIBLE);
            else linearLayoutPESO.setVisibility(View.GONE);

        });

        //PRIMA ESECUZIONE - FINE



        // ================ //
        // LISTENER SEEKBAR
        // ================ //

        // mentre utilizzo la seekbar modifico il valore tella text view per mostrare il valore selezionato
        // quando finisco di usare la seek bar, controllo il valore selezionato: se >2 mostro le impostazioni aggiuntive

        tempSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tempTxtView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int num = Integer.parseInt((String) tempTxtView.getText());

                if (num > 2) linearLayoutTEMP.setVisibility(View.VISIBLE);
                else linearLayoutTEMP.setVisibility(View.GONE);
            }
        });

        presSisSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                presSisTxtView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int num = Integer.parseInt((String) presSisTxtView.getText());

                if (num > 2) linearLayoutPRESSIS.setVisibility(View.VISIBLE);
                else linearLayoutPRESSIS.setVisibility(View.GONE);
            }
        });

        presDiaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                presDiaTxtView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int num = Integer.parseInt((String) presDiaTxtView.getText());

                if (num > 2) linearLayoutPRESDIA.setVisibility(View.VISIBLE);
                else linearLayoutPRESDIA.setVisibility(View.GONE);
            }
        });

        glicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                glicTxtView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int num = Integer.parseInt((String) glicTxtView.getText());

                if (num > 2) linearLayoutGLIC.setVisibility(View.VISIBLE);
                else linearLayoutGLIC.setVisibility(View.GONE);
            }
        });

        pesoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                pesoTxtView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int num = Integer.parseInt((String) pesoTxtView.getText());

                if (num > 2) linearLayoutPESO.setVisibility(View.VISIBLE);
                else linearLayoutPESO.setVisibility(View.GONE);
            }
        });


        // ============= //
        // LISTENER DATE
        // ============= //

        // TEMPERATURA
        // Listener Begin Date
        tempBeginDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                tempBeginDate.setText(data);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // Listener End Date
        tempEndDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                tempEndDate.setText(data);
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // PRESSIONE SISTOLICA
        // Listener Begin Date
        presSisBeginDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                presSisBeginDate.setText(data);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // Listener End Date
        presSisEndDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                presSisEndDate.setText(data);
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // PRESSIONE DIASTOLICA
        // Listener Begin Date
        presDiaBeginDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                presDiaBeginDate.setText(data);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // Listener End Date
        presDiaEndDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                presDiaEndDate.setText(data);
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // GLICEMIA
        // Listener Begin Date
        glicBeginDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                glicBeginDate.setText(data);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // Listener End Date
        glicEndDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                glicEndDate.setText(data);
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // PESO
        // Listener Begin Date
        pesoBeginDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                pesoBeginDate.setText(data);

            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        // Listener End Date
        pesoEndDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, anno, mese, giorno) -> {
                String data = Utils.getDataSettings(anno, mese, giorno);
                pesoEndDate.setText(data);
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });



        //--------------------//
        // LISTENER NOTIFICHE //
        //--------------------//

        // ON: mostro il layout con un'ora
        // OFF: nascondo il layout dell'ora
        toggleButton.setOnClickListener(view -> {

            //Attivo notifiche
            if( toggleButton.isChecked() ){

                linearLayoutNotifiche.setVisibility(View.VISIBLE);

                //creo il calendario - ore "09:00"
                Calendar calendarTmp = Calendar.getInstance();
                calendarTmp.set(Calendar.HOUR_OF_DAY, 9);
                calendarTmp.set(Calendar.MINUTE, 0);
                calendarTmp.set(Calendar.SECOND, 0);

                //setto l'orario in una val globale
                calendarNotify = calendarTmp;

                //mostro l'ora nel layout
                String oraNotifica = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendarTmp.getTime());
                notificationTimeTV.setText(oraNotifica);

            }
            //Disattivo notifiche
            else {
                linearLayoutNotifiche.setVisibility(View.GONE);
            }
        });


        // Quando clicco la text view con l'orario della notifica mostro il TimePicker
        notificationTimeTV.setOnClickListener(view -> {

            //time picker
            Calendar calendar = Calendar.getInstance();
            int HOUR = calendar.get(Calendar.HOUR);
            int MINUTE = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), (timePicker, ore, minuti) -> {

                //creo calendario
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY, ore);
                calendar1.set(Calendar.MINUTE, minuti);
                calendar1.set(Calendar.SECOND, 0);

                //mostro l'ora nel layout
                String oraNotifica = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar1.getTime());
                notificationTimeTV.setText(oraNotifica);

                //setto l'orario in una var globale
                calendarNotify = calendar1;

            }, HOUR, MINUTE, true);

            timePickerDialog.show();

        });


        //premo bottone Aggiorna notifiche
        okBtn.setOnClickListener(view -> {

            Log.i(TAG, "SONO NELL'UPDATE");

            int updateVal = 0;
            double updateLowBound = 0;
            double updateUpBound = 0;
            String updateBeginDate = "";
            String updateEndDate = "";
            String parametro = "";

            boolean iniziaMonitaggio = false;

            for(int i = 1; i<=5; i++){

                // Valori da inserire nel DB - prendo i valori dal form e li insersco in variabili temporanee
                switch(i) {
                    case 1:
                        parametro = DB_TEMPERATURA;
                        updateVal = Integer.parseInt( tempTxtView.getText().toString() );
                        updateLowBound = Double.parseDouble( tempLowBoundET.getText().toString() );
                        updateUpBound = Double.parseDouble( tempUpperBoundET.getText().toString() );
                        updateBeginDate = tempBeginDate.getText().toString();
                        updateEndDate = tempEndDate.getText().toString();
                        break;
                    case 2:
                        parametro = DB_PRESSIONE_SISTOLICA;
                        updateVal = Integer.parseInt( presSisTxtView.getText().toString() );
                        updateLowBound = Double.parseDouble( presSisLowBoundET.getText().toString() );
                        updateUpBound = Double.parseDouble( presSisUpperBoundET.getText().toString() );
                        updateBeginDate = presSisBeginDate.getText().toString();
                        updateEndDate = presSisEndDate.getText().toString();
                        break;
                    case 3:
                        parametro = DB_PRESSIONE_DIASTOLICA;
                        updateVal = Integer.parseInt( presDiaTxtView.getText().toString() );
                        updateLowBound = Double.parseDouble( presDiaLowBoundET.getText().toString() );
                        updateUpBound = Double.parseDouble( presDiaUpperBoundET.getText().toString() );
                        updateBeginDate = presDiaBeginDate.getText().toString();
                        updateEndDate = presDiaEndDate.getText().toString();
                        break;
                    case 4:
                        parametro = DB_GLICEMIA;
                        updateVal = Integer.parseInt( glicTxtView.getText().toString() );
                        updateLowBound = Double.parseDouble( glicLowBoundET.getText().toString() );
                        updateUpBound = Double.parseDouble( glicUpperBoundET.getText().toString() );
                        updateBeginDate = glicBeginDate.getText().toString();
                        updateEndDate = glicEndDate.getText().toString();
                        break;
                    case 5:
                        parametro = DB_PESO;
                        updateVal = Integer.parseInt( pesoTxtView.getText().toString() );
                        updateLowBound = Double.parseDouble( pesoLowBoundET.getText().toString() );
                        updateUpBound = Double.parseDouble( pesoUpperBoundET.getText().toString() );
                        updateBeginDate = pesoBeginDate.getText().toString();
                        updateEndDate = pesoEndDate.getText().toString();
                        break;
                }

                //CONTROLLI
                //check value
                if( updateVal > 2) {

                    //check bound
                    if (updateLowBound < updateUpBound) {

                        //check date
                        if( Utils.checkDateSettings(updateBeginDate, updateEndDate) ){

                            //INSERT NEL DB
                            //new Settings da mandare nel database
                            Settings settings = new Settings(i, parametro, updateVal, updateLowBound, updateUpBound, updateBeginDate, updateEndDate );

                            // Update asincrono
                            settingsViewModel.updateSettings(settings);

                            //update delle shared_prefs a TRUE
                            if(i == 1) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_TEMP, true).apply();
                            else if(i == 2) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_TEMP, true).apply();
                            else if(i == 3) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_PRESS_SIS, true).apply();
                            else if(i == 4) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_PRESS_DIA, true).apply();
                            else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_GLIC, true).apply();

                            //c'è almeno un valore da monitorare (siamo dentro il > 2 )
                            iniziaMonitaggio = true;

                        } else{
                            //le date non vanno bene
                            Log.i(TAG, "Le date del parametro: " + getParameterName(parametro) + "non vanno bene");
                            Snackbar.make(view, "Controlla le date di: " + getParameterName(parametro), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            return;
                        }
                    } else {
                        //i bound non vanno bene
                        Log.i(TAG, "I bound del parametro: " + getParameterName(parametro) + "non vanno bene");
                        Snackbar.make(view, "Controlla i bound di: " + getParameterName(parametro), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        return;
                    }
                } else{

                    Log.i(TAG, parametro + " <= 2");

                    //update delle shared_prefs a FALSE
                    if(i == 1) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_TEMP, false).apply();
                    else if(i == 2) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_TEMP, false).apply();
                    else if(i == 3) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_PRESS_SIS, false).apply();
                    else if(i == 4) getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_PRESS_DIA, false).apply();
                    else getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(MONITORA_GLIC, false).apply();

                    Settings settings = new Settings(i, parametro, updateVal, updateLowBound, updateUpBound, updateBeginDate, updateEndDate );

                    // Update asincrono
                    settingsViewModel.updateSettings(settings);
                }
            } //end for


            cancelMonitorNotification();

            //Notifiche Monitoraggio
            if( iniziaMonitaggio ){
                startMonitorNotification();
            }else{
                cancelMonitorNotification();
            }

            //Notifiche Giornaliere
            if ( toggleButton.isChecked() ){    //si notifica

                //setto shared preference
                String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendarNotify.getTime());
                getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putString(DAILY_TIME, time).apply();
                getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NOTIFICATION_ON, true).apply();

                //Log.i(TAG, "Toggle Button - ora notifica: " + time);

                cancelTimedNotification();
                startTimedNotification();

            }else{      //no notifica

                //setto shared preference
                getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putString(DAILY_TIME, DAILY_TIME_DEFAULT).apply();
                getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit().putBoolean(NOTIFICATION_ON, false).apply();

                cancelTimedNotification();

            }
            Log.i(TAG, "Chiudo L'activity- FINISH");
            finish();

        });

        //premo bottone cancel
        cancelBtn.setOnClickListener(view -> {
            Log.i(TAG, "Chiudo L'activity");
            finish();
        });


    } //fine on create



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ON DESTROY DEI SETTINGS");
    }



    //===========//
    // NOTIFICHE //
    //===========//

    //chiamata quando setto il toggle delle notifiche OFF
    //chiamata quando inserisco una nuova notifica nel SAVE SETTINGS
    public void cancelTimedNotification(){

        //Cancella Timed Notification
        Intent broadcastIntent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager1.cancel(pendingIntent);

        //Cancella Remind Later Notification
        Intent remindLaterIntent = new Intent(this, AlertReceiver.class);
        PendingIntent remindLaterPendingIntent = PendingIntent.getBroadcast(this, 4, remindLaterIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager2.cancel(remindLaterPendingIntent);
    }

    public void startTimedNotification(){

        //intent che mi porta al Broadcast Receiver
        Intent broadcastIntent = new Intent(this, AlertReceiver.class);
        broadcastIntent.putExtra(NOTIFICATION_TYPE, TIMED_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //se l'orario è già passato allora setto la notifica per domani
        if (calendarNotify.before(Calendar.getInstance())) {
            calendarNotify.add(Calendar.DATE, 1);
        }

        String oraRemind = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendarNotify.getTime());
        Log.d(TAG, "La notifica GIORNALIERA arriverà alle ore  = " + oraRemind + " CALENDARIO " + calendarNotify);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarNotify.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarNotify.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }



    //se uno dei 5 parametri sono >=3 allora uso un intent per far partire il ForegroundService alle 2:00
    public void startMonitorNotification(){
        Log.i(TAG, "MONITOR NOTICICATION START - startMonitorNotification");
        Intent broadcastIntent = new Intent(this, AlertReceiver.class);
        broadcastIntent.putExtra(NOTIFICATION_TYPE, MONITOR_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 8, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //QUESTA NOTIFICA ARRIVA ALLE 2:00 DI NOTTE E FA PARTIRE IL LAVORO IN BACKGROUND
        Calendar c = Calendar.getInstance();

        //Setto l'orario per le 2 di mattina
        c.set(Calendar.HOUR_OF_DAY, 2);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        //c.add(Calendar.MINUTE, 1);

        String oraRemind = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Log.d(TAG, "La notifica MONITOR arriverà alle ore  = " + oraRemind);

        //Se l'orario è già passato allora setto la notifica per domani
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    //cancello gli intent che fanno partire le notifiche di monitoraggio
    private void cancelMonitorNotification() {

        //cancello intent che fa partire il foreground service (8)
        Intent broadcastIntent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 8, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);


        //cancello intent che fa partire la notifica del monitoraggio - creata nel foregrounf service (7)
        Intent broadcastIntent2 = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 7, broadcastIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager2.cancel(pendingIntent2);
    }


}