package com.example.personalhealthmonitor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalhealthmonitor.database.Info;
import com.example.personalhealthmonitor.database.Settings;
import com.example.personalhealthmonitor.database.SettingsViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

//import static com.example.personalhealthmonitor.MainActivity.settingsViewModel;

public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "Setting activity";

    //EditText tempSetET, pressisSetET, pressdiaSetET, glicSetET, pesoSetET; => DEVONO ESSERE SEEKBAR
    EditText tempLowBoundET, pressisLowBoundET, presdiaLowBoundET, glicLowBoundET, pesoLowBoundET;
    EditText tempUpperBoundET, pressisUpperBoundET, presdiaUpperBoundET, glicUpperBoundET, pesoUpperBoundET;
    //TODO text view delle date

    SettingsViewModel settingsViewModel;


    private SeekBar tempSeekBar;
    private TextView tempTxtView;

    private LinearLayout linearLayoutTEMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // id - collego le view
        tempSeekBar = findViewById(R.id.temperatura_setting);
        tempTxtView = findViewById(R.id.temp_value);
        tempLowBoundET = findViewById(R.id.temp_lower_bound);
        tempUpperBoundET = findViewById(R.id.temp_upper_bound);

        //linear layout
        linearLayoutTEMP = findViewById(R.id.linearTemperatura);



        //DB
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        LiveData<Settings> settingsTemp = settingsViewModel.getSettingsFromParametro("Temperatura");

        //OBSERVE - inserisco tutti i dati

        // Inserisco i valori nel mio layout
        settingsTemp.observe(this, new Observer<Settings>() {
            @Override
            public void onChanged(Settings setting) {
                Log.i(TAG, "DENTRO LA ONCHANGE, PRENDO I DATI" );

                int val = setting.getValue();

                tempSeekBar.setProgress( val );
                tempTxtView.setText( String.valueOf(val) );
                //TODO date
                tempLowBoundET.setText( String.valueOf(setting.getLowerBound()) );
                tempUpperBoundET.setText( String.valueOf(setting.getUpperBound()) );

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

        // ========================== //
        // SEEKBAR ON CHANGE LISTENER
        // ========================== //

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
                Log.i(TAG, "ho finito di toccare la SEEK BAR TEMP " );

                int num = Integer.parseInt((String) tempTxtView.getText());
                Log.i(TAG, "ho finito di toccare la SEEK BAR TEMP "  + String.valueOf(num) );

                if (num > 2){
                    linearLayoutTEMP.setVisibility(View.VISIBLE);
                }
                else{
                    linearLayoutTEMP.setVisibility(View.GONE);
                }
            }
        });




    //fine on create
    }



    //DATEPICKER
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        
    }






    // ======================== //
    // FUNZIONI ON CLICK BUTTON
    // ======================== //
    public void updateSettings(View view){
        Log.i(TAG, "SONO NELL'UPDATE");

        //boolean flag = true;

        //int tempValue_update = Integer.parseInt( tempTxtView.getText().toString() );

        //prova
        int tempValue_update = Integer.parseInt( tempTxtView.getText().toString() );
        Log.i(TAG, "1");
        double tempLowerBound_update = Double.parseDouble( tempLowBoundET.getText().toString() );
        Log.i(TAG, "2");
        double tempUpperBound_update = Double.parseDouble( tempUpperBoundET.getText().toString() );
        Log.i(TAG, "3");
        String begin = "12/05/2020";
        String end = "12/13/2020";

        Log.i(TAG, "val = " + tempValue_update + " low bound = " + tempLowerBound_update + " upper bound = " + tempUpperBound_update);

        Settings settings = new Settings(1,"Temperatura", tempValue_update, tempLowerBound_update, tempUpperBound_update, begin, end);
        settingsViewModel.updateSettings(settings);
        finish();


//        if( tempValue_update > 2) {
//
//            double tempLowerBound_update = Integer.parseInt( tempLowBoundET.getText().toString() );
//            double tempUpperBound_update = Integer.parseInt( tempUpperBoundET.getText().toString() );
//
//            if (tempLowerBound_update < tempUpperBound_update) {  //inserisco nel DB
//                //CHECK ANCHE DELLE DATE
//                Log.i(TAG, "BOUND Tutto OK!!");
//
//                //INSERT NEL DB
//                //new Settings da mandare nel database
//                Settings settings = new Settings("Temperatura", tempValue_update, tempLowerBound_update, tempUpperBound_update, "12/02/2020", "12/07/2020");
//
//                // Update asincrono
//                settingsViewModel.updateSettings(settings);
//
//            } else {
//                Log.i(TAG, "bound NON VA BENE");
//                Snackbar.make(view, "Controlla i bound della temperatura", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//                // SETTA UN FLAG A FALSE -> non faccio partire le notifiche
//                flag = false;
//
//            }
//        }


//
//        //se arrivo fino a qui, ho passato tutti i controlli e setto la notifica
//        if (flag) {
//            //SETTO LE NOTIFICHEEE
//        }
//
//
//
//
//        Log.i(TAG, "FINISCO UPDATEEE");
//        finish();
    }


    public void cancelSettings(View view){
        Log.i(TAG, "FINISCO L'activity");
        finish();
    }


}