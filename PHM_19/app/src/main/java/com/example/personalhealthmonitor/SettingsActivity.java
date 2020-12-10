package com.example.personalhealthmonitor;

import android.app.Application;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import com.example.personalhealthmonitor.Utils;

import java.util.Calendar;

import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;



public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "Setting activity";

    private SeekBar tempSeekBar, presSisSeekBar, presDiaSeekBar, glicSeekBar, pesoSeekBar;
    private TextView tempTxtView, presSisTxtView, presDiaTxtView, glicTxtView, pesoTxtView;
    private EditText tempLowBoundET, presSisLowBoundET, presDiaLowBoundET, glicLowBoundET, pesoLowBoundET;
    private EditText tempUpperBoundET, presSisUpperBoundET, presDiaUpperBoundET, glicUpperBoundET, pesoUpperBoundET;
    private TextView tempBeginDate, presSisBeginDate, presDiaBeginDate, glicBeginDate, pesoBeginDate;
    private TextView tempEndDate, presSisEndDate, presDiaEndDate, glicEndDate, pesoEndDate;


    private LinearLayout linearLayoutTEMP, linearLayoutPRESSIS, linearLayoutPRESDIA, linearLayoutGLIC, linearLayoutPESO;

    //private SettingsViewModel settingsViewModel;

    private ToggleButton toggleButton;
    private TextView timeTW;
    private LinearLayout linearLayoutNotifiche;

    //date picker
    private int YEAR;
    private int MONTH;
    private int DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // collego le view - id parametri

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

        //notifiche
        toggleButton = findViewById(R.id.toggleButton_notifiche);
        timeTW = findViewById(R.id.time_notifiche);
        linearLayoutNotifiche = findViewById(R.id.linear_time_notifiche);


        //datepicker
        YEAR = Calendar.getInstance().get(Calendar.YEAR);
        MONTH = Calendar.getInstance().get(Calendar.MONTH);
        DATE = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        //DB
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        LiveData<Settings> settingsTemp = settingsViewModel.getSettingsFromParametro("Temperatura");
        LiveData<Settings> settingsPressSis = settingsViewModel.getSettingsFromParametro("Pressione Sistolica");
        LiveData<Settings> settingsPressDia = settingsViewModel.getSettingsFromParametro("Pressione Diastolica");
        LiveData<Settings> settingsGlicemia = settingsViewModel.getSettingsFromParametro("Glicemia");
        LiveData<Settings> settingsPeso = settingsViewModel.getSettingsFromParametro("Peso");


        //OBSERVE - inserisco tutti i dati nel layout

        // Temperatura
        settingsTemp.observe(this, new Observer<Settings>() {
            @Override
            public void onChanged(Settings setting) {
                Log.i(TAG, "DENTRO LA ONCHANGE temperatura, PRENDO I DATI" );

                int val = setting.getValue();

                tempSeekBar.setProgress( val );
                tempTxtView.setText( String.valueOf(val) );
                tempLowBoundET.setText( String.valueOf(setting.getLowerBound()) );
                tempUpperBoundET.setText( String.valueOf(setting.getUpperBound()) );
                tempBeginDate.setText( setting.getBeginDate() );
                tempEndDate.setText( setting.getEndDate() );

                Log.i(TAG, "FINE GET ROBA DAL DB" );

                //setto la visibilità del monitoraggio
                if( val > 2 ){
                    linearLayoutTEMP.setVisibility(View.VISIBLE);
                    Log.i(TAG, " IF TRUE" );
                } else{
                    linearLayoutTEMP.setVisibility(View.GONE);
                    Log.i(TAG, "IF FALSE " );
                }

                Log.i(TAG, "FINE DELLA ON CHANGE" );
            }
        });

        //Pressione Sistolica
        settingsPressSis.observe(this, new Observer<Settings>() {
            @Override
            public void onChanged(Settings settings) {
                Log.i(TAG, "DENTRO LA ONCHANGE press sistolica, PRENDO I DATI" );

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

                Log.i(TAG, "FINE DELLA ON CHANGE" );
            }
        });

        //Pressione Diastolica
        settingsPressDia.observe(this, new Observer<Settings>() {
            @Override
            public void onChanged(Settings settings) {
                Log.i(TAG, "DENTRO LA ONCHANGE press diastolica, PRENDO I DATI" );

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

                Log.i(TAG, "FINE DELLA ON CHANGE" );
            }
        });

        //Glicemia
        settingsGlicemia.observe(this, new Observer<Settings>() {
            @Override
            public void onChanged(Settings settings) {
                Log.i(TAG, "DENTRO LA ONCHANGE glicemia, PRENDO I DATI" );

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

                Log.i(TAG, "FINE DELLA ON CHANGE" );
            }
        });

        //Peso
        settingsPeso.observe(this, new Observer<Settings>() {
            @Override
            public void onChanged(Settings settings) {
                Log.i(TAG, "DENTRO LA ONCHANGE peso, PRENDO I DATI" );

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

                Log.i(TAG, "FINE DELLA ON CHANGE" );
            }
        });



        // ========================== //
        // SEEKBAR ON CHANGE LISTENER
        // ========================== //

        // mentre utilizzo la seekbar modifico il valore tella text view per mostrare il valore selezionato
        // quando finisco di usare la seek bar, controllo il valore selezionato: se >2 mostro le impostazioni aggiuntive

        tempSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tempTxtView.setText(String.valueOf(i));
                //Log.i(TAG, "STO CAMBIANDO I VALORI DELLA SEEKBAR");
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


        // ========================= //
        // LISTENER BEGIN - END date
        // ========================= //

        // Temperatura
        // Listener Begin Date
        tempBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        tempBeginDate.setText(data);

                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Listener End Date
        tempEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        tempEndDate.setText(data);
                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Pressione Sistolica
        // Listener Begin Date
        presSisBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        presSisBeginDate.setText(data);

                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Listener End Date
        presSisEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        presSisEndDate.setText(data);
                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Pressione Diastolica
        // Listener Begin Date
        presDiaBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        presDiaBeginDate.setText(data);

                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Listener End Date
        presDiaEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        presDiaEndDate.setText(data);
                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Glicemia
        // Listener Begin Date
        glicBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        glicBeginDate.setText(data);

                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Listener End Date
        glicEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        glicEndDate.setText(data);
                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Peso
        // Listener Begin Date
        pesoBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        pesoBeginDate.setText(data);

                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        // Listener End Date
        pesoEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        String data = Utils.getDataSettings(anno, mese, giorno);
                        pesoEndDate.setText(data);
                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });


        //TODO toggle listener - quando è ON attivo la notifica nell'orario
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( toggleButton.isChecked() ){
                    linearLayoutNotifiche.setVisibility(View.VISIBLE);

                }else{
                    linearLayoutNotifiche.setVisibility(View.GONE);

                }


            }
        });

    } //fine on create



    //DATEPICKER
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {



    }





    // ======================== //
    // FUNZIONI ON CLICK BUTTON //
    // ======================== //
    public void updateSettings(View view){

        Log.i(TAG, "SONO NELL'UPDATE");

        int updateVal = 0;
        double updateLowBound = 0;
        double updateUpBound = 0;
        String updateBeginDate = "";
        String updateEndDate = "";
        String parametro = "";

        for(int i = 1; i<=5; i++){

            // Valori da inserire nel DB - prendo i valori dal form e li insersco in variabili temporanee
            switch(i) {
                case 1:
                    parametro = "Temperatura";
                    updateVal = Integer.parseInt( tempTxtView.getText().toString() );
                    updateLowBound = Double.parseDouble( tempLowBoundET.getText().toString() );
                    updateUpBound = Double.parseDouble( tempUpperBoundET.getText().toString() );
                    updateBeginDate = tempBeginDate.getText().toString();
                    updateEndDate = tempEndDate.getText().toString();
                    break;
                case 2:
                    parametro = "Pressione Sistolica";
                    updateVal = Integer.parseInt( presSisTxtView.getText().toString() );
                    updateLowBound = Double.parseDouble( presSisLowBoundET.getText().toString() );
                    updateUpBound = Double.parseDouble( presSisUpperBoundET.getText().toString() );
                    updateBeginDate = presSisBeginDate.getText().toString();
                    updateEndDate = presSisEndDate.getText().toString();
                    break;
                case 3:
                    parametro = "Pressione Diastolica";
                    updateVal = Integer.parseInt( presDiaTxtView.getText().toString() );
                    updateLowBound = Double.parseDouble( presDiaLowBoundET.getText().toString() );
                    updateUpBound = Double.parseDouble( presDiaUpperBoundET.getText().toString() );
                    updateBeginDate = presDiaBeginDate.getText().toString();
                    updateEndDate = presDiaEndDate.getText().toString();
                    break;
                case 4:
                    parametro = "Glicemia";
                    updateVal = Integer.parseInt( glicTxtView.getText().toString() );
                    updateLowBound = Double.parseDouble( glicLowBoundET.getText().toString() );
                    updateUpBound = Double.parseDouble( glicUpperBoundET.getText().toString() );
                    updateBeginDate = glicBeginDate.getText().toString();
                    updateEndDate = glicEndDate.getText().toString();
                    break;
                case 5:
                    parametro = "Peso";
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
                if (updateLowBound < updateUpBound) {  //inserisco nel DB

                    //check date
                    if( Utils.checkDateSettings(updateBeginDate, updateEndDate) ){

                        Log.i(TAG, "DATE Tutto OK!!");

                        //INSERT NEL DB
                        //new Settings da mandare nel database
                        Settings settings = new Settings(i, parametro, updateVal, updateLowBound, updateUpBound, updateBeginDate, updateEndDate );

                        // Update asincrono
                        settingsViewModel.updateSettings(settings);


                    } else{
                        //le date non vanno bene
                        Log.i(TAG, "Le date del parametro: " + parametro + "non vanno bene");
                        Snackbar.make(view, "Controlla le date di: " + parametro, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        return;
                    }
                } else {
                    //i bound non vanno bene
                    Log.i(TAG, "I bound del parametro: " + parametro + "non vanno bene");
                    Snackbar.make(view, "Controlla i bound di: " + parametro, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }
            } else{
                //TODO update value nel DB - value<=2 non devo creare notifiche e nel DB restano i valori di default
//                SETTA UN FLAG A FALSE -> non faccio partire le notifiche
//                flag = false;
                Log.i(TAG, "Parametro <= 2");
                Settings settings = new Settings(i, parametro, updateVal, updateLowBound, updateUpBound, updateBeginDate, updateEndDate );

                // Update asincrono
                settingsViewModel.updateSettings(settings);
            }
        }

        finish();
    }


    public void cancelSettings(View view){
        Log.i(TAG, "FINISCO L'activity");
        finish();
    }


}